package tech.ixor.web

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.runBlocking
import okhttp3.Protocol
import tech.ixor.web.HttpRequests.GetRequests
import tech.ixor.web.HttpRequests.PostRequests
import tech.ixor.web.HttpRequests.httpClient

/**
 * HTTP utilities, used for quickly sending GET and POST requests
 * @property httpClient The HTTP client used for sending requests
 * @property GetRequests The object containing all GET request methods
 * @property PostRequests The object containing all POST request methods
 */
object HttpRequests {
    /**
     * The Ktor HTTP client used for sending requests
     */
    private val httpClient = HttpClient(OkHttp) {
        engine {
            config {
                followRedirects(true)
                protocols(listOf(Protocol.HTTP_1_1, Protocol.HTTP_2))
                connectTimeout(0, java.util.concurrent.TimeUnit.SECONDS)
                readTimeout(0, java.util.concurrent.TimeUnit.SECONDS)
                writeTimeout(0, java.util.concurrent.TimeUnit.SECONDS)
            }
        }
    }

    /**
     * The object containing all GET request methods
     */
    object GetRequests {
        /**
         * Send a GET request to the specified URL
         * @param url The URL to send the request to
         * @param requestBuilder The request builder used to modify the request
         * @return The response from the server
         */
        fun request(url: String, requestBuilder: HttpRequestBuilder.() -> Unit = {}): HttpResponse {
            val httpResponse: HttpResponse
            runBlocking {
                httpResponse = httpClient.get(url, requestBuilder)
            }
            return httpResponse
        }
    }

    /**
     * The object containing all POST request methods
     */
    object PostRequests {
        /**
         * Send a POST request to the specified URL
         * @param url The URL to send the request to
         * @param requestBuilder The request builder used to modify the request
         * @return The response from the server
         */
        fun request(url: String, requestBuilder: HttpRequestBuilder.() -> Unit = {}): HttpResponse {
            val httpResponse: HttpResponse
            runBlocking {
                httpResponse = httpClient.post(url, requestBuilder)
            }
            return httpResponse
        }
    }
}
