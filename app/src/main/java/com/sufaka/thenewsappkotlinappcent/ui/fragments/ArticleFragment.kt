package com.sufaka.thenewsappkotlinappcent.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.webkit.WebViewClient
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.sufaka.thenewsappkotlinappcent.R
import com.sufaka.thenewsappkotlinappcent.databinding.FragmentArticleBinding
import com.sufaka.thenewsappkotlinappcent.ui.NewsActivity
import com.sufaka.thenewsappkotlinappcent.ui.NewsViewModel

class ArticleFragment : Fragment(R.layout.fragment_article) {

    lateinit var newsViewModel: NewsViewModel
    val args: ArticleFragmentArgs by navArgs()
    lateinit var binding: FragmentArticleBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArticleBinding.bind(view)

        newsViewModel = (activity as NewsActivity).newsViewModel
        val article = args.article
        binding.webView.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.safeBrowsingEnabled = true


            article.url?.let {
                loadUrl(it)
            }
        }




        val backBTN = view.findViewById<ImageView>(R.id.backToContentBTN)

        backBTN.setOnClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", article)
            }
            findNavController().navigate(R.id.action_articleFragment_to_contentFragment, bundle)

        }

        val shareBTN = view.findViewById<ImageView>(R.id.shareBTN)
        shareBTN.setOnClickListener{
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, article.url)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        val makeFavBTN = view.findViewById<ImageView>(R.id.makeFavBTN)
        makeFavBTN.setOnClickListener {
            newsViewModel.addToFavorites(article)
            Snackbar.make(view, "Added to favourites", Snackbar.LENGTH_SHORT).show()
        }





    }

}