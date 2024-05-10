package com.sufaka.thenewsappkotlinappcent.repos

import com.sufaka.thenewsappkotlinappcent.api.RetrofitInstance
import com.sufaka.thenewsappkotlinappcent.database.ArticleDatabase
import com.sufaka.thenewsappkotlinappcent.models.Article

class NewsRepository(val datab: ArticleDatabase) {

    // This function is used to get breaking news from the API
suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
    RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)
suspend fun searchForNews(searchQuery: String, pageNumber: Int) =
    RetrofitInstance.api.searchForNews(searchQuery, pageNumber)

    suspend fun upsert(article: Article) = datab.getArticleDao().upsert(article)

    fun getFavoriteNews() = datab.getArticleDao().getAllArticles()

    suspend fun deleteArticle(article: Article) = datab.getArticleDao().deleteArticle(article)
}