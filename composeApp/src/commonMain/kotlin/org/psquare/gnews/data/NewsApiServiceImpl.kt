package org.psquare.gnews.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.URLProtocol
import io.ktor.http.parameters
import io.ktor.http.path
import org.psquare.gnews.data.entities.ArticleResponse

class NewsApiServiceImpl(
    private val host: String,
    private val apiKey: String,
    private val topHeadline: String,
    private val httpClient: HttpClient
) : NewsApiService {

    override suspend fun getNewsArticles(category: String): ArticleResponse {
        val response = httpClient.get {
            url {
                protocol = URLProtocol.HTTP
                host = this@NewsApiServiceImpl.host
                path(topHeadline)
                parameters {
                    append("category", category)
                    append("apikey", apiKey)
                }
            }
        }
        return response.body<ArticleResponse>()
    }
}