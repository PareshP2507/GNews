package org.psquare.gnews.data.repository.news

import kotlinx.coroutines.flow.Flow
import org.psquare.gnews.data.db.GNewsDatabase
import org.psquare.gnews.data.entities.DbArticleEntity

class LocalNewsDataSource(
    private val database: GNewsDatabase
) : NewsDataSource.Local {
    override fun getArticles(category: String): Flow<List<DbArticleEntity>> =
        database.articleDao.getAllAsFlow(category)

    override suspend fun insertArticles(articles: List<DbArticleEntity>) =
        database.articleDao.insertAll(articles)

    override suspend fun clearArticles(category: String) = database.articleDao.clearAll(category)

    override suspend fun addBookmark(id: Long) = database.articleDao.addBookmark(id)

    override suspend fun removeBookmark(id: Long) = database.articleDao.removeBookmark(id)
}