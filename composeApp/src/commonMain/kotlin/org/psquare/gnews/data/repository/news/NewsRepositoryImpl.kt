package org.psquare.gnews.data.repository.news

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.psquare.gnews.data.dateconverter.DateConverter
import org.psquare.gnews.data.entities.toDbEntity
import org.psquare.gnews.data.entities.toDomain
import org.psquare.gnews.domain.entities.ArticleEntity
import org.psquare.gnews.domain.repository.NewsRepository
import org.psquare.util.NetworkResult
import kotlin.coroutines.CoroutineContext

class NewsRepositoryImpl(
    private val remoteNewsDataSource: NewsDataSource.Remote,
    private val localNewsDataSource: NewsDataSource.Local,
    private val dateConverter: DateConverter,
    private val coroutineDispatcher: CoroutineDispatcher
) : NewsRepository, CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = SupervisorJob() + coroutineDispatcher

    override suspend fun getArticlesAsFlow(category: String): Flow<List<ArticleEntity>> =
        localNewsDataSource.getArticles(category)
            .map { dbArticles ->
                dbArticles.map { dbArticle ->
                    dbArticle.toDomain(dateConverter.intoElapsedTime(dbArticle.publishedAt))
                }
            }.flowOn(coroutineDispatcher)

    override fun refreshArticles(category: String) {
        launch {
            val response = remoteNewsDataSource.getArticles(category)
            if (response is NetworkResult.Success) {
                localNewsDataSource.insertArticles(response.data.articles.map {
                    it.toDbEntity(category)
                })
            }
        }
    }

    override fun onCleared() {
        coroutineContext.cancel()
    }
}