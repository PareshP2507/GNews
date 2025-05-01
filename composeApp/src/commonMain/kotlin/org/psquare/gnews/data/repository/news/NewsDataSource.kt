package org.psquare.gnews.data.repository.news

import org.psquare.gnews.data.entities.ArticleResponse
import org.psquare.util.NetworkResult

interface NewsDataSource {

    interface Remote {
        suspend fun getArticles(category: String): NetworkResult<ArticleResponse>
    }
}