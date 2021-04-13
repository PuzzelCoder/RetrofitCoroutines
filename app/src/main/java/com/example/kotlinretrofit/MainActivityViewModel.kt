package com.example.kotlinretrofit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinretrofit.entities.UserList
import com.example.kotlinretrofit.network.RetroService
import com.example.kotlinretrofit.network.RetrofitInstance
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {

   private var recyclerListData: MutableLiveData<UserList>


    init {
        recyclerListData = MutableLiveData()
    }

    fun getUserlistObservable(): LiveData<UserList> {
        return recyclerListData
    }

     fun getUserList() {
        viewModelScope.launch {
            val retrofitInstance =
                RetrofitInstance.getRetrofitInstance().create(RetroService::class.java)
            var response = retrofitInstance.getUserList()
            if(response.isSuccessful){
                recyclerListData.postValue(response.body())
            }
            else {
                recyclerListData.postValue(null)
            }
        }
    }

     fun searchUser(text:String) {
        viewModelScope.launch {
            val retrofitInstance =
                RetrofitInstance.getRetrofitInstance().create(RetroService::class.java)
            var response = retrofitInstance.searchUsers(text)
            if(response.isSuccessful){
                recyclerListData.postValue(response.body())
            }
            else {
                recyclerListData.postValue(null)
            }
        }
    }


}