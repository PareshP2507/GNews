package org.psquare.gnews.data.repository.news

import org.psquare.gnews.data.entities.ArticleResponse

interface NewsDataSource {

    interface Remote {
        suspend fun getArticles(category: String): ArticleResponse
    }
}