package com.enderyasli.retrofitcoroutines.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enderyasli.retrofitcoroutines.Repository
import com.enderyasli.retrofitcoroutines.data.User
import com.enderyasli.retrofitcoroutines.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository) : ViewModel() {


    //Factory

    val myResponse: MutableLiveData<Resource<User>> = MutableLiveData()
//    val isLoading: MutableLiveData<Boolean> = MutableLiveData()

    fun getUser() {

        viewModelScope.launch {
//            isLoading.postValue(true)
            myResponse.postValue(Resource.Loading())
            val response = repository.getUser()
            myResponse.postValue(handleResponse(response))
//            isLoading.postValue(false)
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

}