package com.techmania.userroomdatabase.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.techmania.userroomdatabase.adapter.Adapter
import com.techmania.userroomdatabase.model.User
import com.techmania.userroomdatabase.repository.UserRepository
import com.techmania.userroomdatabase.room.UserDataBase
import com.techmania.userroomdatabase.viewModel.UserViewModel
import com.techmania.userroomdatabase.viewModel.UserViewModelFactory
import com.techmania.userroomdatabase.R
import com.techmania.userroomdatabase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //Room database
        val dao = UserDataBase.getInstance(application).userDAO
        val repository = UserRepository(dao)
        val factory = UserViewModelFactory(repository)

        userViewModel = ViewModelProvider(this, factory)[UserViewModel::class.java]

        binding.userViewModel = userViewModel

        binding.lifecycleOwner = this

        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        DisplayUserList()
    }

    private fun DisplayUserList() {
        userViewModel.users.observe(this, Observer {
            binding.recyclerView.adapter = Adapter(it, { selectedItem: User ->
                listItemClicked(selectedItem)
            })
        })
    }

    private fun listItemClicked(selectedItem: User) {
        Toast.makeText(this, "Selected : ${selectedItem.name}", Toast.LENGTH_SHORT)
            .show()

        userViewModel.initUpdateAndDelete(selectedItem)
    }
}