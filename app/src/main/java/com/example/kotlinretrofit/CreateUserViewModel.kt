package com.example.kotlinretrofit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinretrofit.entities.User
import com.example.kotlinretrofit.entities.UserResponse
import com.example.kotlinretrofit.network.RetroService
import com.example.kotlinretrofit.network.RetrofitInstance
import kotlinx.coroutines.launch

class CreateUserViewModel : ViewModel() {

    private var createNewUserliveData: MutableLiveData<UserResponse>
    private var loadUserDataliveData: MutableLiveData<UserResponse>
    private var deleteUserDataliveData: MutableLiveData<UserResponse>

    init {
        createNewUserliveData = MutableLiveData()
        loadUserDataliveData = MutableLiveData()
        deleteUserDataliveData = MutableLiveData()
    }

    fun getCreateNewUserObservable(): LiveData<UserResponse> {
        return createNewUserliveData
    }
    fun loadUserDataObservable(): LiveData<UserResponse> {
        return loadUserDataliveData
    }
    fun deleteUserDataObservable(): LiveData<UserResponse> {
        return deleteUserDataliveData
    }

    fun createUser(user: User) {
        viewModelScope.launch {
            val retrofitInstance =
                RetrofitInstance.getRetrofitInstance().create(RetroService::class.java)
            var response = retrofitInstance.createUser(user)
            if (response.isSuccessful) {
                createNewUserliveData.postValue(response.body())
            } else {
                createNewUserliveData.postValue(null)
            }
        }
    }  fun getUserData(user_id: String) {
        viewModelScope.launch {
            val retrofitInstance =
                RetrofitInstance.getRetrofitInstance().create(RetroService::class.java)
            var response = retrofitInstance.getUser(user_id)
            if (response.isSuccessful) {
                loadUserDataliveData.postValue(response.body())
            } else {
                loadUserDataliveData.postValue(null)
            }
        }
    }

    fun updateeUser(user_id:String,user: User) {
        viewModelScope.launch {
            val retrofitInstance =
                RetrofitInstance.getRetrofitInstance().create(RetroService::class.java)
            var response = retrofitInstance.updateUser(user_id,user)
            if (response.isSuccessful) {
                createNewUserliveData.postValue(response.body())
            } else {
                createNewUserliveData.postValue(null)
            }
        }
    }

    fun deleteUser(user_id:String) {

        viewModelScope.launch {
            val retrofitInstance =
                RetrofitInstance.getRetrofitInstance().create(RetroService::class.java)
            var response = retrofitInstance.deleteUser(user_id)
            if (response.isSuccessful) {
                deleteUserDataliveData.postValue(response.body())
            } else {
                deleteUserDataliveData.postValue(null)
            }
        }

    }
}