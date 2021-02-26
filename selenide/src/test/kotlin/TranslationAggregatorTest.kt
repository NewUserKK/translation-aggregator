import com.codeborne.selenide.Configuration.baseUrl
import com.codeborne.selenide.Configuration.browser
import com.codeborne.selenide.Selenide.`$`
import com.codeborne.selenide.Selenide.open
import com.codeborne.selenide.WebDriverRunner.*
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.string.shouldContain
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.remote.RemoteWebDriver

import org.openqa.selenium.remote.DesiredCapabilities
import java.net.URI

class TranslationAggregatorTest : FreeSpec({
//    lateinit var driver: WebDriver

    beforeAny {
        baseUrl = "http://127.0.0.1:8081"
        browser = FIREFOX
//        val capabilities = DesiredCapabilities().apply {
//            setCapability("browserName", "firefox")
//            setCapability("browserVersion", "84.0")
//            setCapability(
//                "selenoid:options", mapOf(
//                    "enableVNC" to true,
//                    "enableVideo" to true
//                )
//            )
//        }
//
//        driver = RemoteWebDriver(
//            URI.create("http://localhost:4444/wd/hub").toURL(),
//            capabilities
//        )
//
//        setWebDriver(driver)
    }

    afterAny {
//        driver.close()
    }

    "/ redirects to main" {
        open("/")
        url() shouldContain "#/main"
    }

    "redirect to sign in" {
        open("/")
        `$`(By.xpath("//*[contains(text(), 'Sign in')]")).click()
        url() shouldContain "#/login"
        `$`(By.xpath("//*[contains(text(), 'Authenticate')]")).exists().shouldBeTrue()
    }
})
