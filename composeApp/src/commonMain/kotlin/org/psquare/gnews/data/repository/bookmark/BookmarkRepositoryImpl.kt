package org.psquare.gnews.data.repository.bookmark

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.psquare.gnews.data.dateconverter.DateConverter
import org.psquare.gnews.data.entities.toDomain
import org.psquare.gnews.domain.entities.ArticleEntity
import org.psquare.gnews.domain.repository.BookmarkRepository

class BookmarkRepositoryImpl(
    private val localDataSource: BookmarkDataSource.Local,
    private val dateConverterImpl: DateConverter
) : BookmarkRepository {

    override fun getBookmarkAsFlow(): Flow<List<ArticleEntity>> {
        return localDataSource.getBookmarks().map { dbArticles ->
            dbArticles.map {
                it.toDomain(dateConverterImpl.intoElapsedTime(it.publishedAt))
            }
        }
    }
}