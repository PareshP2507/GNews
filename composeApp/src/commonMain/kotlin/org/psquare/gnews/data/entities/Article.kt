package org.psquare.gnews.data.entities

import kotlinx.serialization.Serializable
import org.psquare.gnews.domain.entities.ArticleEntity

@Serializable
data class ArticleResponse(
    val articles: List<RemoteArticle>
)

@Serializable
data class RemoteArticle(
    val title: String,
    val description: String,
    val content: String,
    val url: String,
    val image: String,
    val publishedAt: String,
    val source: Source
)

@Serializable
data class Source(val name: String, val url: String)

fun RemoteArticle.toDomain() = ArticleEntity(
    title = this.title,
    description = this.description,
    content = this.content,
    url = this.url,
    image = this.image,
    publishedAt = this.publishedAt,
    sourceName = this.source.name
)