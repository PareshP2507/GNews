package org.psquare.gnews.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
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
    val title: String,
    val description: String,
    val content: String,
    val url: String,
    val image: String,
    val publishedAt: String,
    val sourceName: String,
    val sourceUrl: String,
    val category: String,
    val isBookmarked: Boolean = false
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
    id = this.id,
    title = this.title,
    description = this.description,
    content = this.content,
    url = this.url,
    image = this.image,
    elapsedTime = elapsedTime,
    sourceName = this.sourceName,
    isBookmarked = this.isBookmarked
)
