package org.psquare.gnews.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
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

@Serializable
@Entity(tableName = "article_master")
data class DbArticleEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String,
    @SerialName("content")
    val content: String,
    @SerialName("url")
    val url: String,
    @SerialName("image")
    val image: String,
    @SerialName("publishedAt")
    val publishedAt: String,
    @SerialName("source_name")
    val sourceName: String,
    @SerialName("source_url")
    val sourceUrl: String,
    @SerialName("category")
    val category: String
)

fun RemoteArticle.toDbEntity(category: String) = DbArticleEntity(
    title = this.title,
    description = this.description,
    content = this.content,
    url = this.url,
    image = this.image,
    publishedAt = this.publishedAt,
    sourceName = this.source.name,
    sourceUrl = this.source.url,
    category = category
)

fun DbArticleEntity.toDomain(elapsedTime: String) = ArticleEntity(
    title = this.title,
    description = this.description,
    content = this.content,
    url = this.url,
    image = this.image,
    elapsedTime = elapsedTime,
    sourceName = this.sourceName
)

fun RemoteArticle.toDomain(elapsedTime: String) = ArticleEntity(
    title = this.title,
    description = this.description,
    content = this.content,
    url = this.url,
    image = this.image,
    elapsedTime = elapsedTime,
    sourceName = this.source.name
)