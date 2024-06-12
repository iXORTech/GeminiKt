package tech.ixor.apis

import com.beust.klaxon.Klaxon
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import tech.ixor.entities.responses.GoogleGeminiResponse
import tech.ixor.web.HttpRequests

class GoogleGeminiAPI (private val url: String, private val key: String) {
    /**
     * Send a request to the GeminiPro API to get the response
     * @param path The API URL path to send the request to
     * @param systemInstruction The system instruction to send to the API
     * @param contents The context of the conversation
     * @return The response from the GeminiPro API
     */
    suspend fun generateContent(
        path: String,
        systemInstruction: Map<String, Any>?,
        contents: ArrayList<Map<String, Any>>,
    ): GoogleGeminiResponse? {
        val api = "$url$path:generateContent?key=$key"

        // Parse JSON API request body
        val request: MutableMap<String, Any> = mutableMapOf()
        if (systemInstruction != null) {
//            request["system_instruction"] = systemInstruction
        }
        request["contents"] = contents

        // Send request to API
        val response = HttpRequests.PostRequests.request(api) {
            contentType(ContentType.Application.Json)
            setBody(Klaxon().toJsonString(request))
        }

        // Return the response from the API
        return Klaxon().parse<GoogleGeminiResponse>(response.bodyAsText())
    }
}
