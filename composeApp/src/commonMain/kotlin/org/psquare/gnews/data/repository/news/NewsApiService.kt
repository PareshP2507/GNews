package org.psquare.gnews.data.repository.news

import org.psquare.gnews.data.entities.ArticleResponse

interface NewsApiService {

    suspend fun getNewsArticles(category: String): ArticleResponse
}