package com.sufaka.thenewsappkotlinappcent.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sufaka.thenewsappkotlinappcent.models.Article

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) // If the article already exists, replace it
    suspend fun upsert(article: Article): Long
    @Query("SELECT * FROM articles") // Get all articles
    fun getAllArticles(): LiveData<List<Article>>

    @Delete // Delete an article
    suspend fun deleteArticle(article: Article)

}