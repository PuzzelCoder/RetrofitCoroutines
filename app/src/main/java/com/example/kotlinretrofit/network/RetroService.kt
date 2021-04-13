package com.example.kotlinretrofit.network

import com.example.kotlinretrofit.Utils.Apis.Companion.ACCESS_TOKEN
import com.example.kotlinretrofit.entities.User
import com.example.kotlinretrofit.entities.UserList
import com.example.kotlinretrofit.entities.UserResponse
import retrofit2.Response
import retrofit2.http.*

interface RetroService {

//    https://gorest.co.in/public-api/users
    @GET("users")
    @Headers("Accept:application/json" ,"Content-Type:application/json")
    suspend fun getUserList():Response<UserList>


    //    https://gorest.co.in/public-api/users?name=a
    @GET("users")
    @Headers("Accept:application/json" ,"Content-Type:application/json")
    suspend fun searchUsers(@Query("name") searchText:String):Response<UserList>


    //    https://gorest.co.in/public-api/users>name=a
    @GET("users/{user_id}")
    @Headers("Accept:application/json" ,"Content-Type:application/json")
    suspend fun getUser(@Path("user_id") user_id:String):Response<UserResponse>


    @POST("users")
    @Headers("Accept:application/json", "Content-Type:application/json" ,
        "Authorization: Bearer 73668350bdf06c66f3388408c1a712b378c3e25da599753b21b664a6261e246c")
    suspend fun createUser(@Body user:User):Response<UserResponse>


    @PATCH("users/{user_id}")
    @Headers("Accept:application/json", "Content-Type:application/json" ,
        "Authorization: Bearer 73668350bdf06c66f3388408c1a712b378c3e25da599753b21b664a6261e246c")
    suspend fun updateUser(@Path("user_id") user_id:String,@Body user:User):Response<UserResponse>

    @DELETE("users/{user_id}")
    @Headers("Accept:application/json", "Content-Type:application/json" ,
        "Authorization: Bearer 73668350bdf06c66f3388408c1a712b378c3e25da599753b21b664a6261e246c")
    suspend fun deleteUser(@Path("user_id") user_id:String):Response<UserResponse>
}
