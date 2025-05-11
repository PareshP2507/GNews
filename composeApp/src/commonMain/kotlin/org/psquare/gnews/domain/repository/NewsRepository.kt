package org.psquare.gnews.domain.repository

import kotlinx.coroutines.flow.Flow
import org.psquare.gnews.domain.entities.ArticleEntity

interface NewsRepository {

    suspend fun getArticlesAsFlow(category: String): Flow<List<ArticleEntity>>

    fun refreshArticles(category: String)

    fun onCleared()
}