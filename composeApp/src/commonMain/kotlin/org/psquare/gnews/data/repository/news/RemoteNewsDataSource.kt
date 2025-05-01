package org.psquare.gnews.data.repository.news

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.psquare.gnews.data.entities.ArticleResponse
import org.psquare.util.NetworkResult

class RemoteNewsDataSource(
    private val newsApiService: NewsApiService,
    private val coroutineDispatcher: CoroutineDispatcher
) : NewsDataSource.Remote {

    override suspend fun getArticles(category: String): NetworkResult<ArticleResponse> =
        withContext(coroutineDispatcher) {
            newsApiService.getNewsArticles(category)
        }
}