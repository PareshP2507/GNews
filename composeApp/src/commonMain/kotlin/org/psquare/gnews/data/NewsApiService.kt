package org.psquare.gnews.data

import org.psquare.gnews.data.entities.ArticleResponse

interface NewsApiService {

    suspend fun getNewsArticles(category: String): ArticleResponse
}