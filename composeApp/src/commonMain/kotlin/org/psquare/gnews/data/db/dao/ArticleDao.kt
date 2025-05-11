package org.psquare.gnews.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.psquare.gnews.data.entities.DbArticleEntity

@Dao
interface ArticleDao {
    @Query("SELECT * FROM article_master WHERE category = :category")
    fun getAllAsFlow(category: String): Flow<List<DbArticleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(articles: List<DbArticleEntity>)
}