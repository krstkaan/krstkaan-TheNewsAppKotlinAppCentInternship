package com.sufaka.thenewsappkotlinappcent.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sufaka.thenewsappkotlinappcent.repos.NewsRepository

class NewsViewModelProviderFactory(val app: Application, val newsRepository: NewsRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T { // This function is used to create the view model
        return NewsViewModel(app, newsRepository) as T
    }
}