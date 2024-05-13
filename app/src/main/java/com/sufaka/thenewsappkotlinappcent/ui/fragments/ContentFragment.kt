package com.sufaka.thenewsappkotlinappcent.ui.fragments

import android.content.Intent
import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.sufaka.thenewsappkotlinappcent.R
import com.sufaka.thenewsappkotlinappcent.databinding.FragmentArticleBinding
import com.sufaka.thenewsappkotlinappcent.ui.NewsActivity
import com.sufaka.thenewsappkotlinappcent.ui.NewsViewModel


import java.time.LocalDate
import java.time.format.DateTimeFormatter


class ContentFragment : Fragment(R.layout.fragment_content) {
    val args: ArticleFragmentArgs by navArgs()

    lateinit var newsViewModel: NewsViewModel
    lateinit var binding: FragmentArticleBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textView = view.findViewById<TextView>(R.id.newsTitle)
        val article = args.article
        val it= article.url
        textView.text = article.title

        val image = view.findViewById<ImageView>(R.id.newsImage)
        Glide.with(this).load(article.urlToImage).into(image)

        val newsDescription = view.findViewById<TextView>(R.id.newsDesc)
        newsDescription.text = article.description

        val author = view.findViewById<TextView>(R.id.newsAuthor)
        author.text = article.author



        val publishedAt = view.findViewById<TextView>(R.id.newsDate)
        var strDate = article.publishedAt.toString().substring(0,10)


       strDate = LocalDate.parse(strDate).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        //strDate.replace("-", "/")



        publishedAt.text = strDate.toString()
        val backBTN = view.findViewById<ImageView>(R.id.backBTN)

        backBTN.setOnClickListener {
          findNavController().navigate(R.id.action_contentFragment_to_newsFragment)

        }
        val shareBTN = view.findViewById<ImageView>(R.id.shareBTN)
        shareBTN.setOnClickListener {
            val sendIntent: String = article.url.toString()
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, sendIntent)
                type = "text/plain"
            }
            startActivity(Intent.createChooser(shareIntent, null))
        }

        newsViewModel = (activity as NewsActivity).newsViewModel

        val makeFavBTN = view.findViewById<ImageView>(R.id.makeFavBTN)
        makeFavBTN.setOnClickListener {
            newsViewModel.addToFavorites(article)
            Snackbar.make(view, "Added to favorites", Snackbar.LENGTH_SHORT).show()
        }

        val goSourceBTN = view.findViewById<Button>(R.id.goSourceBTN)
        goSourceBTN.setOnClickListener {

            val bundle = Bundle().apply {
                putSerializable("article", article)
            }


            findNavController().navigate(R.id.action_contentFragment_to_articleFragment, bundle)

        }




    }



    }








