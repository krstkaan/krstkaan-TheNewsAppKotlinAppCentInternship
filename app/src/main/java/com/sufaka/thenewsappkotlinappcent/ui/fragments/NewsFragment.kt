package com.sufaka.thenewsappkotlinappcent.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sufaka.thenewsappkotlinappcent.R
import com.sufaka.thenewsappkotlinappcent.adapters.NewsAdapter
import com.sufaka.thenewsappkotlinappcent.databinding.FragmentNewsBinding
import com.sufaka.thenewsappkotlinappcent.ui.NewsActivity
import com.sufaka.thenewsappkotlinappcent.ui.NewsViewModel
import com.sufaka.thenewsappkotlinappcent.util.Constants
import com.sufaka.thenewsappkotlinappcent.util.Resource


class NewsFragment : Fragment(R.layout.fragment_news) {

    lateinit var newsViewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    lateinit var retryBtn: Button
    lateinit var errorText: TextView
    lateinit var itemNewsError: CardView
    lateinit var binding: FragmentNewsBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewsBinding.bind(view)

        itemNewsError = view.findViewById(R.id.itemHeadlinesError)

        val inflater =
            requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.item_error, null)

        retryBtn = view.findViewById(R.id.retryButton)
        errorText = view.findViewById(R.id.errorText)

        newsViewModel = (activity as NewsActivity).newsViewModel
        setupNewsRecyclerView()

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)

            }
            findNavController().navigate(R.id.action_newsFragment2_to_articleFragment, bundle)

        }

        newsViewModel.breakingNews.observe(viewLifecycleOwner) { response ->
            when (response) {
               is Resource.Success<*> -> {
                   hideProgressBar()
                   hideErrorMesssage()
                   response.data?.let { newsResponse ->
                       newsAdapter.differ.submitList(newsResponse.articles.toList())
                       val totalPages = newsResponse.totalResults / Constants.QUERY_PAGE_SIZE + 2
                       isLastPage = newsViewModel.breakingNewsPage == totalPages
                       if (isLastPage) {
                           binding.recyclerHeadlines.setPadding(0, 0, 0, 0)
                       }
                   }
               }
                is Resource.Error<*> -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Toast.makeText(activity, "An error occured: $message", Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading<*> -> {
                    showProgressBar()
                }
            }
        }
        retryBtn.setOnClickListener{
            newsViewModel.getBreakingNews("us")
        }
    }

    var isError = false
    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
        isLoading = true
    }

    private fun showErrorMessage(message: String) {
        itemNewsError.visibility = View.VISIBLE
        errorText.text = message
        isError = true
    }

    private fun hideErrorMesssage() {
        itemNewsError.visibility = View.INVISIBLE
        isError = false
    }

    val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(
            recyclerView: RecyclerView,
            dx: Int,
            dy: Int
        ) { //dx is the horizontal scroll and dy is the vertical scroll

            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount
            val isNoError = !isError
            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= Constants.QUERY_PAGE_SIZE
            val shouldPaginate =
                isNoError && isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning && isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {//if all of these are true, then we will get the next page
                newsViewModel.getBreakingNews("us")
                isScrolling = false
            }

        }

        override fun onScrollStateChanged(
            recyclerView: RecyclerView,
            newState: Int
        ) {//newState is the state of the scroll
            super.onScrollStateChanged(recyclerView, newState)

            if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                isScrolling = true
            }

        }
    }

    private fun setupNewsRecyclerView() {
        newsAdapter = NewsAdapter()
        binding.recyclerHeadlines.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@NewsFragment.scrollListener)
        }

    }
}


