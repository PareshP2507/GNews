package org.psquare.gnews.data.repository.news

import kotlinx.coroutines.flow.Flow
import org.psquare.gnews.data.entities.ArticleResponse
import org.psquare.gnews.data.entities.DbArticleEntity
import org.psquare.util.NetworkResult

interface NewsDataSource {

    interface Remote {
        suspend fun getArticles(category: String): NetworkResult<ArticleResponse>
    }

    interface Local {
        suspend fun getArticles(category: String): Flow<List<DbArticleEntity>>

        suspend fun insertArticles(articles: List<DbArticleEntity>)

        suspend fun clearArticles(category: String)
    }
}