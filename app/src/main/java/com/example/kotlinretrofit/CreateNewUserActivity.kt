package com.example.kotlinretrofit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinretrofit.entities.User
import kotlinx.android.synthetic.main.activity_create_new_user.*
import java.util.*

class CreateNewUserActivity : AppCompatActivity() {

    lateinit var viewModel: CreateUserViewModel
    lateinit var deletedUser:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_new_user)
        initViewModel()
        val user_id: String? = intent.getStringExtra("user_id")

        Log.d("TAG", "onCreate: $user_id")
        if (user_id != null) {
            Log.d("TAG", "onCreate:inside ")
            loadUserData(user_id)
        }


        createUserObservable()
        createUser.setOnClickListener {
            createUser(user_id)
        }
        deleteeUser.setOnClickListener {
            deletedUser=user_id.toString()
            viewModel.deleteUser(user_id.toString())
        }
    }

    private fun loadUserData(user_id: String) {
        viewModel.loadUserDataObservable().observe(this, Observer {

            if (it != null) {
                nameEditText.setText(it.data?.name)
                emailEditText.setText(it.data?.email)
                createUser.text = "Update User"
                deleteeUser.visibility = View.VISIBLE
            }
        })

        viewModel.getUserData(user_id)
    }

    fun createUser(user_id: String?) {
        val name: String = nameEditText.text.toString().trim()
        val email: String = emailEditText.text.toString().trim()

        if (name.isEmpty()) {
            nameEditText.setError("Please Enter the name")
        } else if (email.isEmpty()) {
            emailEditText.error = "Please Enter the Email"
        } else {
            emailEditText.error = null
            nameEditText.error = null

            if (user_id == null) {
                val user =
                    User("", name, email, "Male", "Active", Date().toString(), Date().toString())
                viewModel.createUser(user)
            } else {
                val user = User(
                    user_id,
                    name,
                    email,
                    "Male",
                    "Active",
                    Date().toString(),
                    Date().toString()
                )
                viewModel.updateeUser(user_id, user)
            }

        }
    }

    private fun createUserObservable() {
        viewModel.getCreateNewUserObservable().observe(this, Observer {

            if (it == null) {
                Toast.makeText(applicationContext, "User creation Failed..!!", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(
                    applicationContext,
                    "User created/updated Successfully..!!",
                    Toast.LENGTH_SHORT
                ).show()
                val intent=Intent().putExtra("user_name",it.data?.name)
                setResult(RESULT_OK,intent)
                finish()
            }
        })


        viewModel.deleteUserDataObservable().observe(this, Observer {

            if (it == null) {
                Toast.makeText(applicationContext, "User deletion failed..!!", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(
                    applicationContext,
                    "User cdeleted Successfully..!!",
                    Toast.LENGTH_SHORT
                ).show()
                val intent=Intent().putExtra("user_name",deletedUser)
                setResult(RESULT_OK,intent)
                finish()

            }

        })
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(CreateUserViewModel::class.java)

    }


}