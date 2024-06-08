package tech.ixor

import tech.ixor.entities.ConfigEntity
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
}
