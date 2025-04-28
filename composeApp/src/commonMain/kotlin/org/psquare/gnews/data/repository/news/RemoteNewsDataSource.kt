package org.psquare.gnews.data.repository.news

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.psquare.gnews.data.entities.ArticleResponse

class RemoteNewsDataSource(
    private val newsApiService: NewsApiService
) : NewsDataSource.Remote {

    override suspend fun getArticles(category: String): ArticleResponse =
        withContext(Dispatchers.IO) {
            newsApiService.getNewsArticles(category)
        }
}