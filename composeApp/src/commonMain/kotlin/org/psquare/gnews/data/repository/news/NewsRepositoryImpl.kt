package org.psquare.gnews.data.repository.news

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import org.psquare.gnews.data.dateconverter.DateConverter
import org.psquare.gnews.data.entities.toDbEntity
import org.psquare.gnews.data.entities.toDomain
import org.psquare.gnews.data.repository.refresh.CategoryRefreshRepository
import org.psquare.gnews.domain.entities.ArticleEntity
import org.psquare.gnews.domain.repository.NewsRepository
import org.psquare.util.NetworkResult

class NewsRepositoryImpl(
    private val remoteNewsDataSource: NewsDataSource.Remote,
    private val localNewsDataSource: NewsDataSource.Local,
    private val dateConverter: DateConverter,
    private val categoryRefreshRepository: CategoryRefreshRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) : NewsRepository {

    override suspend fun getArticlesAsFlow(category: String): Flow<List<ArticleEntity>> =
        localNewsDataSource.getArticles(category)
            .map { dbArticles ->
                dbArticles.map { dbArticle ->
                    dbArticle.toDomain(dateConverter.intoElapsedTime(dbArticle.publishedAt))
                }
            }.flowOn(coroutineDispatcher)

    override suspend fun refreshArticles(category: String) {
        val response = remoteNewsDataSource.getArticles(category)
        if (response is NetworkResult.Success) {
            localNewsDataSource.clearArticles(category)
            localNewsDataSource.insertArticles(response.data.articles.map {
                it.toDbEntity(category)
            })
            categoryRefreshRepository.saveLastRefresh(category, Clock.System.now())
        }
    }
}