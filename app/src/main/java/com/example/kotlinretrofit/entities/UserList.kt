package com.example.kotlinretrofit.entities

import java.io.Serializable

data class UserList(val data: List<User>)
data class User(val id:String, val name:String, val email:String, val gender:String, val status:String, val created_at:String,
                val updated_at:String)
data class UserResponse(val code:Int?, val meta:String, val data:User?)
