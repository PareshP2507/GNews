package org.psquare.gnews.data.repository.news

import org.psquare.gnews.data.dateconverter.DateConverter
import org.psquare.gnews.data.entities.toDomain
import org.psquare.util.NetworkResult
import org.psquare.gnews.domain.entities.ArticleEntity
import org.psquare.gnews.domain.repository.NewsRepository

class NewsRepositoryImpl(
    private val remoteNewsDataSource: NewsDataSource.Remote,
    private val dateConverter: DateConverter
) : NewsRepository {

    override suspend fun getArticles(category: String): List<ArticleEntity> {
        val response = remoteNewsDataSource.getArticles(category)
        return if (response is NetworkResult.Success) {
            response.data.articles.map { article ->
                article.toDomain(
                    dateConverter.intoElapsedTime(
                        article.publishedAt
                    )
                )
            }
        } else {
            emptyList()
        }
    }
}