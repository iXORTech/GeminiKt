package tech.ixor.entities

import com.sksamuel.hoplite.ConfigLoader
import tech.ixor.exceptions.ConfigFileCreatedException
import tech.ixor.exceptions.ConfigFileResourceNotFoundError
import java.io.File

class ConfigEntity {
    data class Config(
        val api: ApiConfig
    )

    data class ApiConfig(
        val gemini: GeminiConfig,
    )

    data class GeminiConfig(
        val url: String,
        val key: String
    )

    companion object {
        fun loadConfig(): Config {
            val pwd = System.getProperty("user.dir")
            val configFile = File("$pwd/config/config.yaml")

            if (!configFile.exists()) {
                val fileContent = ConfigEntity::class.java.getResource("/config/config.yaml")?.readText()
                configFile.parentFile.mkdirs()
                configFile.createNewFile()
                if (fileContent == null) {
                    throw ConfigFileResourceNotFoundError()
                }
                configFile.writeText(fileContent)
                throw ConfigFileCreatedException()
            }

            return ConfigLoader().loadConfigOrThrow(configFile.absolutePath)
        }
    }
}
