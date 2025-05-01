package org.psquare.gnews.data.repository.news

import org.psquare.gnews.domain.entities.ArticleEntity
import org.psquare.gnews.domain.repository.NewsRepository

class FakeNewsRepositoryImpl : NewsRepository {

    override suspend fun getArticles(category: String): List<ArticleEntity> {
        return buildList {
            for (i in 1..10) {
                add(ArticleEntity(
                    title = "Article $i title",
                    description = "Article $i description",
                    content = "Article $i content",
                    url = "Article $i url",
                    image = "https://picsum.photos/200",
                    elapsedTime = "$i h",
                    sourceName = "Source $i"
                ))
            }
        }
    }
}