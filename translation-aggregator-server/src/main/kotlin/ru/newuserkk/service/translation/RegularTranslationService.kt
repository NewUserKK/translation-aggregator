package ru.newuserkk.service.translation

import com.fasterxml.jackson.annotation.JsonProperty
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.bouncycastle.util.io.pem.PemReader
import ru.newuserkk.common.*
import ru.newuserkk.model.translation.RegularTranslationResults
import ru.newuserkk.model.translation.TranslationResult
import java.io.FileReader
import java.security.KeyFactory
import java.security.spec.PKCS8EncodedKeySpec
import java.time.Instant
import java.util.*

private data class TranslateRequestBody(
    @JsonProperty("folder_id") val folderId: String,
    val texts: List<String>,
    val targetLanguageCode: String
)

private data class IamTokenResponse(
    val iamToken: String,
    val expiresAt: Date
)

class RegularTranslationService(private val httpClient: HttpClient) : TranslationService {
    private var iamToken: String? = null

    private var iamTokenRetriesCount = 0
    private val iamTokenUpdater: suspend () -> Unit by lazy {
        {
            println("Updating IAM token...")
            val jwt = generateJwtToken()

            val iamTokenResult = runSafelySuspended {
                httpClient.post<IamTokenResponse>(IAM_TOKEN_URL) {
                    body = "{ \"jwt\": \"$jwt\" }"
                }
            }

            when (iamTokenResult) {
                is Left -> {
                    println("Error fetching IAM token: ${iamTokenResult.value}")
                    if (iamTokenRetriesCount < IAM_TOKEN_MAX_RETRIES) {
                        iamTokenRetriesCount++
                        iamTokenUpdater()
                    }
                }
                is Right -> {
                    iamToken = iamTokenResult.value.iamToken
                    iamTokenRetriesCount = 0
                    println("IAM token updated!")

                    delay(IAM_TOKEN_UPDATE_DELAY_HOURS)
                    iamTokenUpdater()
                }
            }
        }
    }

    init {
        GlobalScope.launch {
            iamTokenUpdater()
        }
    }

    override suspend fun translate(word: String): OperationResult<List<TranslationResult>> = when {
        word.isEmpty() -> rightOf(listOf())
        else -> runSafelySuspended {
            httpClient.post<RegularTranslationResults>(TRANSLATION_URL) {
                header("Authorization", "Bearer $iamToken")
                contentType(ContentType.Application.Json)
                body = TranslateRequestBody(
                    folderId = YANDEX_CLOUD_FOLDER_ID,
                    texts = listOf(word),
                    targetLanguageCode = "ru"
                )
            }.translations
        }
    }

    private fun generateJwtToken(): String {
        val privateKeyPem = PemReader(FileReader(PATH_TO_PRIVATE_KEY)).use { reader ->
            reader.readPemObject();
        }

        val keyFactory = KeyFactory.getInstance("RSA");
        val privateKey = keyFactory.generatePrivate(PKCS8EncodedKeySpec(privateKeyPem.content));

        val now = Instant.now();

        return Jwts.builder()
            .setHeaderParam("kid", SERVICE_ACCOUNT_KEY_ID)
            .setIssuer(SERVICE_ACCOUNT_ID)
            .setAudience("https://iam.api.cloud.yandex.net/iam/v1/tokens")
            .setIssuedAt(Date.from(now))
            .setExpiration(Date.from(now.plusSeconds(360)))
            .signWith(privateKey, SignatureAlgorithm.PS256)
            .compact();
    }

    companion object {
        private const val IAM_TOKEN_MAX_RETRIES = 3
        private const val IAM_TOKEN_URL = "https://iam.api.cloud.yandex.net/iam/v1/tokens"
        private const val IAM_TOKEN_UPDATE_DELAY_HOURS = 1 * 60 * 60 * 1000L

        private const val PATH_TO_PRIVATE_KEY = "keys/private.pem"
        private val SERVICE_ACCOUNT_ID = requireEnv("SERVICE_ACCOUNT_ID")
        private val SERVICE_ACCOUNT_KEY_ID = requireEnv("SERVICE_ACCOUNT_KEY_ID")

        private val YANDEX_CLOUD_FOLDER_ID = requireEnv("YANDEX_CLOUD_FOLDER_ID")
        private const val TRANSLATION_URL = "https://translate.api.cloud.yandex.net/translate/v2/translate"
    }
}
