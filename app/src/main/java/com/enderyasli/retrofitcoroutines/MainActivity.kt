package com.enderyasli.retrofitcoroutines

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.enderyasli.retrofitcoroutines.data.User
import com.enderyasli.retrofitcoroutines.databinding.ActivityMainBinding
import com.enderyasli.retrofitcoroutines.utils.Resource
import com.enderyasli.retrofitcoroutines.viewModel.MainViewModel
import com.enderyasli.retrofitcoroutines.viewModel.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(Repository())
        ).get(MainViewModel::class.java)


//        viewModel.getUser()
//        viewModel.getUserWithId(3)
//        viewModel.getUserWithJob()
//        viewModel.getUserWithSync()
//        viewModel.getUserComments(3)
        viewModel.getSortedUserComments(2, "id", "desc")
        viewModel.postUser((User("body", 10, "Custom title", 10, 0, "name", "email")))





        viewModel.myResponse.observe(this, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let {
                        binding.tv.text = it.toString()
                    }
                    hideProgressBar()
                    hideErrorText()
                }

                is Resource.Error -> {
                    response.message?.let { error ->
                        Log.i("Main", error)
                    }
                    hideProgressBar()
                    showErrorText()
                }

                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })

        viewModel.myResponsewithId.observe(this, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let {
                        binding.tv2.text = it.toString()
                    }
                    hideProgressBar()
                    hideErrorText()
                }

                is Resource.Error -> {
                    response.message?.let { error ->
                        Log.i("Main", error)
                    }
                    hideProgressBar()
                    showErrorText()
                }

                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })


        viewModel.responseUserComments.observe(this, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { userCommentList ->

                        userCommentList.forEach {
                            val userCommentList = userCommentList.joinToString("\n") {
                                it.name
                            }
                            binding.tvComment.text = userCommentList
                        }

                    }
                    hideProgressBar()
                    hideErrorText()
                }

                is Resource.Error -> {
                    response.message?.let { error ->
                        Log.i("Main", error)
                    }
                    hideProgressBar()
                    showErrorText()
                }

                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
        viewModel.responseSortedUserComments.observe(this, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { userCommentList ->

                        userCommentList.forEach {
                            val userCommentList = userCommentList.joinToString("\n") {
                                it.name
                            }
                            binding.tvSortedComment.text = userCommentList
                        }

                    }
                    hideProgressBar()
                    hideErrorText()
                }

                is Resource.Error -> {
                    response.message?.let { error ->
                        Log.i("Main", error)
                    }
                    hideProgressBar()
                    showErrorText()
                }

                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })

        viewModel.postUser.observe(this, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    hideErrorText()
                    response.data?.let {
                        Log.d("Post User: ", "Post Succesfull: ${it}")
                    }

                }

                is Resource.Error -> {
                    response.message?.let { error ->
                        Log.i("Main", error)
                    }
                    hideProgressBar()
                    showErrorText()
                }

                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })

//        viewModel.myResponse.observe(this, Observer { response ->
//            if (response.isSuccessful && response != null) {
//                response.body()?.let {
//                    Log.d("service response: ", response.body().toString())
//                    binding.tv.text = response.body().toString()
//                }
//            } else {
//                binding.tvError.apply {
//                    visibility = View.VISIBLE
//                    text = "Error"
//                }
//            }
//        })

//        viewModel.isLoading.observe(this, Observer { isLoading ->
//            if (isLoading)
//                binding.progressBar.visibility = View.VISIBLE
//            else
//                binding.progressBar.visibility = View.GONE


//        })


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


