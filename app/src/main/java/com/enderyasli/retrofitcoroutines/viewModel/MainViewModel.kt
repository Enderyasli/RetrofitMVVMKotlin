package com.enderyasli.retrofitcoroutines.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enderyasli.retrofitcoroutines.Repository
import com.enderyasli.retrofitcoroutines.data.User
import com.enderyasli.retrofitcoroutines.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository) : ViewModel() {


    //Factory

    val myResponse: MutableLiveData<Resource<User>> = MutableLiveData()
    val myResponsewithId: MutableLiveData<Resource<User>> = MutableLiveData()
    val postUser: MutableLiveData<Resource<User>> = MutableLiveData()

//    val isLoading: MutableLiveData<Boolean> = MutableLiveData()

    fun getUser() {
        viewModelScope.launch(Dispatchers.IO) {
//            isLoading.postValue(true)
            myResponse.postValue(Resource.Loading())
            val response = repository.getUser()
            myResponse.postValue(handleResponse(response))
//            isLoading.postValue(false)
        }
    }

    fun getUserWithId(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            myResponsewithId.postValue(Resource.Loading())
            val response = repository.getUserWithId(id)
            myResponsewithId.postValue(handleResponse(response))
        }
    }


    fun getUserWithJob() {

        viewModelScope.launch(Dispatchers.IO) {

            val job1 = launch {
                myResponse.postValue(Resource.Loading())
                val response = repository.getUser()
                myResponse.postValue(handleResponse(response))
            }
            val job2 = launch {
                myResponsewithId.postValue(Resource.Loading())
                val response = repository.getUserWithId(3)
                myResponsewithId.postValue(handleResponse(response))
            }

            job1.join()
            job2.join()
        }

    }

    fun getUserWithSync() {

        viewModelScope.launch(Dispatchers.IO) {

            val deferred1 = async {
                myResponse.postValue(Resource.Loading())
                repository.getUser()
            }
            val deferred2 = async {
                myResponsewithId.postValue(Resource.Loading())
                repository.getUserWithId(3)
            }

            val response1 = deferred1.await()
            val response2 = deferred2.await()

            myResponse.postValue(handleResponse(response1))
            myResponsewithId.postValue(handleResponse(response2))
        }

    }


    private fun handleResponse(response: Response<User>): Resource<User> {

        if (response.isSuccessful) {
            response.body()?.let { myResponse ->
                return Resource.Success(myResponse)
            }
        }
        return Resource.Error("Error: ${response.code()} - ${response.body()}")
    }

    private fun handleListResponse(response: Response<List<User>>): Resource<List<User>> {

        if (response.isSuccessful) {
            response.body()?.let { myResponse ->
                return Resource.Success(myResponse)
            }
        }
        return Resource.Error("Error: ${response.code()} - ${response.body()}")
    }

    private fun handlePostResponse(response: Response<User>): Resource<User> {

        return if (response.isSuccessful) {
            response.body()?.let { result ->
                Log.d("Retrofit Post:", "Response code: ${response.code()}")
                Resource.Success(result)
            } ?: Resource.Error("Response body is null")
        } else {
            Resource.Error(response.message())
        }


    }


    //***************************************************************************************

    val responseUserComments: MutableLiveData<Resource<List<User>>> = MutableLiveData()

    fun getUserComments(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            responseUserComments.postValue(Resource.Loading())
            val response = repository.getUserCommentsWithId(id)
            responseUserComments.postValue(handleListResponse(response))
        }
    }

    val responseSortedUserComments: MutableLiveData<Resource<List<User>>> = MutableLiveData()


    fun getSortedUserComments(postId: Int, sort: String, order: String) {
        viewModelScope.launch(Dispatchers.IO) {
            responseSortedUserComments.postValue(Resource.Loading())
            val response = repository.getSortedUserComments(postId, sort, order)
            responseSortedUserComments.postValue(handleListResponse(response))
        }
    }


    fun postUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            postUser.postValue(Resource.Loading())
            val response = repository.postUser(user)
            postUser.postValue(handlePostResponse(response))
        }
    }


}