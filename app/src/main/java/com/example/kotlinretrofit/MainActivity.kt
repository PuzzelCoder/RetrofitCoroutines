package com.example.kotlinretrofit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinretrofit.entities.User
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), UserAdapter.onItemClickListener {
    lateinit var recyclerViewAdapter: UserAdapter
    lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeRecycerView()
        initViewModel()
        getAllUsers()
        searchUser()
        createUser.setOnClickListener {
            startActivity(Intent(this@MainActivity, CreateNewUserActivity::class.java))
        }
    }

    private fun getAllUsers() {
        viewModel.getUserList()
    }

    private fun searchUser() {
        searchuser.setOnClickListener {
            if (searchEditText.text.trim().isEmpty()) {
                searchEditText.setError("Please enter the text here")
            } else {
                searchEditText.setError(null)
                viewModel.searchUser(searchEditText.text.toString().trim())
            }
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.getUserlistObservable().observe(this, Observer {
            if (it == null) {
                Toast.makeText(applicationContext, "No results found...", Toast.LENGTH_SHORT).show()
            } else {
                val userlist = it.data.toMutableList()
                if (userlist.size == 0) {
                    Toast.makeText(applicationContext, "No results found...", Toast.LENGTH_SHORT)
                        .show()
                    getAllUsers()
                }
                Log.d("TAG", "userlist.size=: " + userlist.size)
                recyclerViewAdapter.users = userlist
                recyclerViewAdapter.notifyDataSetChanged()

            }
        })

    }

    fun initializeRecycerView() {
        recyclerview.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
            recyclerViewAdapter = UserAdapter(this@MainActivity)
            adapter = recyclerViewAdapter

        }
    }

    override fun onItemClick(user: User) {
        val intent = Intent(this@MainActivity, CreateNewUserActivity::class.java)
        intent.putExtra("user_id", user.id.toString())
        startActivityForResult(intent, 1000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d("TAG", "onActivityResult: $requestCode")
        if (requestCode == 1000) {
            if (resultCode == RESULT_OK) {

                val username = data?.getStringExtra("user_name")
                Log.d("TAG", "onActivityResult: $username")
                if (username != null) {
                    viewModel.searchUser(username.toString())
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}