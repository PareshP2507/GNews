package org.psquare.gnews.data.repository.bookmark

import kotlinx.coroutines.flow.Flow
import org.psquare.gnews.data.db.GNewsDatabase
import org.psquare.gnews.data.entities.DbArticleEntity

class LocalBookmarkDataSource(
    private val database: GNewsDatabase
) : BookmarkDataSource.Local {

    override fun getBookmarks(): Flow<List<DbArticleEntity>> = database.articleDao.getBookmarks()
}