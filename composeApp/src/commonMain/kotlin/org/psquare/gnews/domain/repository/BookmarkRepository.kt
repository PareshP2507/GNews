package org.psquare.gnews.domain.repository

import kotlinx.coroutines.flow.Flow
import org.psquare.gnews.domain.entities.ArticleEntity

interface BookmarkRepository {

    fun getBookmarkAsFlow(): Flow<List<ArticleEntity>>
}