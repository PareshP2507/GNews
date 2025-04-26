package org.psquare.gnews.data.repository

import org.psquare.gnews.data.entities.ArticleResponse

interface NewsDataSource {

    interface Remote {
        suspend fun getArticles(category: String): ArticleResponse
    }
}