package tech.ixor.apis

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import io.ktor.client.request.*
import io.ktor.http.*
import tech.ixor.web.HttpRequests

class GoogleGeminiAPI (private val url: String, private val key: String) {
    /**
     * Send a request to the GeminiPro API to get the response
     * @param context The previous context of the conversation
     * @param userRole The role of the user in the conversation, defaults to "user"
     * @param modelRole The role of the model in the conversation, defaults to "model"
     * @return The response from the GeminiPro API
     */
    fun generateContent(
        path: String,
        systemInstruction: Map<String, Any>?,
        contents: ArrayList<Map<String, Any>>,
    ): String {
        val api = "$url$path:generateContent?key=$key"

        // Parse JSON API request body
        val request: MutableMap<String, Any> = mutableMapOf()
        if (systemInstruction != null) {
//            request["system_instruction"] = systemInstruction
        }
        request["contents"] = contents

        // Send request to API
        val response = HttpRequests.PostRequests.bodyJsonObject(api) {
            contentType(ContentType.Application.Json)
            setBody(Klaxon().toJsonString(request))
        }

        // Get all responses from API
        val results = ArrayList<String>()
        response.array<JsonObject>("candidates")?.forEach { candidate ->
            candidate.obj("content")?.array<JsonObject>("parts")?.forEach { part ->
                results.add(part.string("text") ?: "")
            }
        }

        // Return the response from the API
        return results[0]
    }
}
