package org.psquare.gnews.domain.entities

data class ArticleEntity(
    val title: String,
    val description: String,
    val content: String,
    val url: String,
    val image: String,
    val elapsedTime: String,
    val sourceName: String
)
