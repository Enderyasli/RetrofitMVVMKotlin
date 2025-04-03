package com.enderyasli.retrofitcoroutines.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enderyasli.retrofitcoroutines.Repository
import com.enderyasli.retrofitcoroutines.data.Post
import com.enderyasli.retrofitcoroutines.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository) : ViewModel() {

    val responsePost: MutableLiveData<Resource<List<Post>>> = MutableLiveData()


    fun getPost() {
        viewModelScope.launch(Dispatchers.IO) {
            responsePost.postValue(Resource.Loading())
            val response = repository.getPosts()
            responsePost.postValue(handleListResponse(response))

        }
    }

    private fun handleListResponse(response: Response<List<Post>>): Resource<List<Post>> {

        if (response.isSuccessful) {
            response.body()?.let { myResponse ->
                return Resource.Success(myResponse)
            }
        }
        return Resource.Error("Error: ${response.code()} - ${response.body()}")
    }


}