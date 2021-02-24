package ru.newuserkk.service.translation

import io.ktor.client.*
import io.ktor.client.request.*
import ru.newuserkk.common.OperationResult
import ru.newuserkk.common.rightOf
import ru.newuserkk.common.runSafelySuspended
import ru.newuserkk.model.translation.TranslationResult
import ru.newuserkk.model.translation.UrbanTranslationResults

class UrbanTranslationService(private val httpClient: HttpClient) : TranslationService {
    override suspend fun translate(word: String): OperationResult<List<TranslationResult>> = when {
        word.isEmpty() -> rightOf(listOf())
        else -> runSafelySuspended {
            httpClient.get<UrbanTranslationResults>(BASE_URL) {
                parameter("term", word)
            }.list.map { result ->
                result.copy(
                    definition = removeLinks(result.definition),
                    example = removeLinks(result.example)
                )
            }
        }
    }

    private fun removeLinks(text: String): String = text.replace("[\\[\\]]".toRegex(), "")

    companion object {
        private const val BASE_URL = "https://api.urbandictionary.com/v0/define"
    }
}
