package tech.ixor.entities

import kotlinx.coroutines.runBlocking
import tech.ixor.apis.GoogleGeminiAPI
import tech.ixor.enums.GeminiApiVersions
import tech.ixor.enums.GeminiModels

/**
 * The Google Gemini Entity
 * @param geminiApiVersion The Gemini API version to use
 * @param geminiModel The Gemini model to use
 * @param systemInstructionMessage The system instruction given to the Gemini model
 */
class GoogleGemini(
    private val api: GoogleGeminiAPI,
    geminiApiVersion: GeminiApiVersions,
    geminiModel: GeminiModels,
    systemInstructionMessage: String? = null
) {
    /**
     * The URL to which requests are sent
     */
    private val url: String = "${geminiApiVersion.versionUrl}/models/${geminiModel.modelCode}"

    /**
     * The system instruction to give to the Gemini model
     * null if no system instruction is given
     */
    private val systemInstruction: Map<String, Any>? = if (systemInstructionMessage != null) {
        mapOf(
            "system_instruction" to mapOf(
                "parts" to mapOf(
                    "text" to systemInstructionMessage
                )
            )
        )
    } else {
        null
    }

    /**
     * The context of the conversation
     */
    private val contents: ArrayList<Map<String, Any>> = ArrayList()

    /**
     * Add a new content to the context
     * @param content The content to add (message from user or model)
     * @param role The role of the content (user or model)
     */
    private fun addContent(content: String, role: String) {
        contents.add(
            mapOf(
                "role" to role,
                "parts" to arrayOf(
                    mapOf(
                        "text" to content
                    )
                )
            )
        )
    }

    fun generateContent(message: String): String {
        addContent(message, "user")
        val content: String
        runBlocking {
            val response = api.generateContent(url, systemInstruction, contents)
            content = response?.candidates?.get(0)?.content?.parts?.get(0)?.text ?: "NO_RESPONSE"
        }
        addContent(content, "model")
        return content
    }
}
