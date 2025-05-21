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

    @Query("DELETE FROM article_master WHERE category = :category")
    suspend fun clearAll(category: String)

    @Query("UPDATE article_master SET isBookmarked = 1 WHERE id = :id")
    suspend fun addBookmark(id: Long)

    @Query("UPDATE article_master SET isBookmarked = 0 WHERE id = :id")
    suspend fun removeBookmark(id: Long)

    @Query("SELECT * FROM article_master WHERE isBookmarked = 1")
    fun getBookmarks(): Flow<List<DbArticleEntity>>
}