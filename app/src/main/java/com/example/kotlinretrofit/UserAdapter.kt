package com.example.kotlinretrofit

import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinretrofit.entities.User


class UserAdapter(val clickListner: onItemClickListener) :
    RecyclerView.Adapter<UserAdapter.MyViewHolder>() {
    var users = mutableListOf<User>()


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val textEmail = view.findViewById<TextView>(R.id.email)
        private val textName = view.findViewById<TextView>(R.id.name)
        private val textStatus = view.findViewById<TextView>(R.id.status)
        fun bind(data: User) {
            textEmail.setText(data.email)
            textName.setText(data.name)
            val content = SpannableString(data.status)
            content.setSpan(UnderlineSpan(), 0, content.length, 0)
            textStatus.setText(content)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.user_list, parent, false)
        return MyViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(users.get(position))
        holder.itemView.setOnClickListener { clickListner.onItemClick(users.get(position)) }
    }

    override fun getItemCount(): Int {
        return users.size
    }

    interface onItemClickListener {
        fun onItemClick(user: User)
    }
}