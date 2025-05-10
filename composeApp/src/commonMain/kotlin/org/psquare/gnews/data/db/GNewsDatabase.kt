package org.psquare.gnews.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import org.psquare.gnews.data.db.dao.ArticleDao
import org.psquare.gnews.data.entities.DbArticleEntity

@Database(entities = [DbArticleEntity::class], version = 1)
abstract class GNewsDatabase : RoomDatabase() {
    abstract val articleDao: ArticleDao
}

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT", "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect object GNewsDatabaseConstructor : RoomDatabaseConstructor<GNewsDatabase> {
    override fun initialize(): GNewsDatabase
}

internal const val DB_FILE_NAME = "gnews.db"