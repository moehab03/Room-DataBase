package com.techmania.userroomdatabase.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.techmania.userroomdatabase.model.User
import com.techmania.userroomdatabase.R
import com.techmania.userroomdatabase.databinding.CardItemBinding

class Adapter(
    private val userList: List<User>,
    private val cL: (User) -> Unit
) : RecyclerView.Adapter<Adapter.userViewHolder>() {

    class userViewHolder(private val binding: CardItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User, cL: (User) -> Unit) {
            binding.nameTextView.text = user.name
            binding.emailTextView.text = user.email
            binding.linear.setOnClickListener {
                cL(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): userViewHolder {
        val view = LayoutInflater.from(parent.context)
        val binding : CardItemBinding = DataBindingUtil.inflate(view,R.layout.card_item,parent,false)
        return userViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: userViewHolder, position: Int) {
        holder.bind(userList[position],cL)
    }
}