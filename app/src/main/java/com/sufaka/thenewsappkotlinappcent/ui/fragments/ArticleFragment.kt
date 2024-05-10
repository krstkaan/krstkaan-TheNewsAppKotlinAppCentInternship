package com.sufaka.thenewsappkotlinappcent.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.sufaka.thenewsappkotlinappcent.R
import com.sufaka.thenewsappkotlinappcent.databinding.ActivityNewsBinding
import com.sufaka.thenewsappkotlinappcent.databinding.FragmentArticleBinding
import com.sufaka.thenewsappkotlinappcent.ui.NewsActivity
import com.sufaka.thenewsappkotlinappcent.ui.NewsViewModel

class ArticleFragment : Fragment(R.layout.fragment_article) {
    lateinit var newsViewModel: NewsViewModel
    val args: ArticleFragmentArgs by navArgs()
    lateinit var  binding: FragmentArticleBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       binding = FragmentArticleBinding.bind(view)

        newsViewModel = (activity as NewsActivity).newsViewModel//activity is the context of the fragment
        val article = args.article
        binding.webView.apply { //apply is used to initialize the webview
            webViewClient = WebViewClient()
            article.url?.let { loadUrl(it)
            }
    }
        binding.fab.setOnClickListener {
            newsViewModel.addToFavorites(article)
            Snackbar.make(view, "The news is favorited", Snackbar.LENGTH_SHORT).show()
        }
    }

}