package org.psquare.gnews.data.repository.news

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.URLProtocol
import io.ktor.http.encodedPath
import org.psquare.gnews.data.entities.ArticleResponse
import org.psquare.util.NetworkResult
import org.psquare.util.safeApiAll

class NewsApiServiceImpl(
    private val host: String,
    private val apiKey: String,
    private val topHeadline: String,
    private val httpClient: HttpClient
) : NewsApiService {

    override suspend fun getNewsArticles(category: String): NetworkResult<ArticleResponse> =
        safeApiAll {
            val response = httpClient.get {
                url {
                    protocol = URLProtocol.HTTPS
                    host = this@NewsApiServiceImpl.host
                    encodedPath = topHeadline
                    parameters.append("lang", LANGUAGE_EN)
                    parameters.append("category", category)
                    parameters.append("apikey", apiKey)
                }
            }
            response.body<ArticleResponse>()
        }

    private companion object {
        const val LANGUAGE_EN = "en"
    }
}