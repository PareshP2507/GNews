package org.psquare.gnews.domain.repository

import org.psquare.gnews.domain.entities.ArticleEntity

interface NewsRepository {

    suspend fun getArticles(category: String): List<ArticleEntity>
}