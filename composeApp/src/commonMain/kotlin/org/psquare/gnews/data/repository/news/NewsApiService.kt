package org.psquare.gnews.data.repository.news

import org.psquare.gnews.data.entities.ArticleResponse
import org.psquare.util.NetworkResult

interface NewsApiService {

    suspend fun getNewsArticles(category: String): NetworkResult<ArticleResponse>
}