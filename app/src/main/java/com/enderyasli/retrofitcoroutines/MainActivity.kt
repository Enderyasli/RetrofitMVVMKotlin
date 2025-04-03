package com.enderyasli.retrofitcoroutines

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.enderyasli.retrofitcoroutines.adapter.PostRecyclerAdapter
import com.enderyasli.retrofitcoroutines.databinding.ActivityMainBinding
import com.enderyasli.retrofitcoroutines.utils.Resource
import com.enderyasli.retrofitcoroutines.viewModel.MainViewModel
import com.enderyasli.retrofitcoroutines.viewModel.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var postRecyclerAdapter: PostRecyclerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(Repository())
        ).get(MainViewModel::class.java)

        postRecyclerAdapter = PostRecyclerAdapter()
        binding.rvPost.apply {

            adapter = postRecyclerAdapter
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if (!recyclerView.canScrollVertically(1) && !viewModel.isRefreshing) {
                        viewModel.loadMorePosts()
                    }
                }
            })


        }

        //Swipe Refresh
        binding.swipeRefreshLayout.setOnRefreshListener {
            postRecyclerAdapter.differ.submitList(emptyList())
            viewModel.refreshPost()
        }


//        viewModel.getPost(1)
        viewModel.responsePost.observe(this, Observer { response ->

            when (response) {
                is Resource.Success -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    hideErrorText()
                    hideProgressBar()
                    response.data?.let { postList ->
                        postRecyclerAdapter.differ.submitList(postRecyclerAdapter.differ.currentList + postList)
                    }
                }

                is Resource.Error -> {
                    showProgressBar()
                    hideProgressBar()
                    binding.swipeRefreshLayout.isRefreshing = false
                    response.message?.let { errorMessage ->
                        Log.d("MainActivity: ", "${errorMessage}")
                    }
                }

                is Resource.Loading -> {
                    if (!viewModel.isRefreshing)
                        showProgressBar()
                }


            }

        })


    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideErrorText() {
        binding.tvError.visibility = View.GONE
    }

    private fun showErrorText() {
        binding.tvError.apply {
            visibility = View.VISIBLE
            binding.tvError.text = "Hata Oluştu"
        }
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }
}




