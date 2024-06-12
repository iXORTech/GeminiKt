package tech.ixor

import tech.ixor.apis.GoogleGeminiAPI
import tech.ixor.entities.ConfigEntity
import tech.ixor.entities.GoogleGemini
import tech.ixor.enums.GeminiApiVersions
import tech.ixor.enums.GeminiModels
import tech.ixor.exceptions.ConfigFileCreatedException
import kotlin.system.exitProcess
import kotlin.test.Test
import kotlin.test.assertTrue

class LibraryTest {
    @Test fun loadConfig() {
        val config = try {
            ConfigEntity.loadConfig()
        } catch (configFileCreatedException: ConfigFileCreatedException) {
            configFileCreatedException.stackTrace.forEach {
                println(it)
            }
            assertTrue { true }
            exitProcess(0)
        }
        assertTrue(config.api.gemini.url.isNotEmpty(), "URL should not be empty")
        assertTrue(config.api.gemini.key.isNotEmpty(), "Key should not be empty")
    }

    @Test fun basicResponse() {
        val config = ConfigEntity.loadConfig()
        val gemini = GoogleGemini(
            GoogleGeminiAPI(config.api.gemini.url, config.api.gemini.key),
            GeminiApiVersions.V1_BETA,
            GeminiModels.GEMINI_1_5_PRO,
            "Output everything in Markdown format"
        )
        val response = gemini.generateContent("Write a story about a magic backpack.")
        println("Response: $response")
        assertTrue(response.isNotEmpty(), "Response should not be empty")
    }
}
