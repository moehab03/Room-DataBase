package com.techmania.userroomdatabase.viewModel

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techmania.userroomdatabase.model.User
import com.techmania.userroomdatabase.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) :
    ViewModel(), Observable {

    val users = repository.users
    private var isUpdateOrDelete = false
    private lateinit var userToUpdateOrDelete: User

    @Bindable
    val inputName = MutableLiveData<String?>()

    @Bindable
    val inputEmail = MutableLiveData<String?>()

    @Bindable
    val saveOrUpdateButton = MutableLiveData<String>()

    @Bindable
    val clearALlOrDeleteAllButton = MutableLiveData<String>()

    init {
        saveOrUpdateButton.value = "Save"
        clearALlOrDeleteAllButton.value = "Clear All"
    }

    fun saveOrUpdate() {
        if (isUpdateOrDelete) {
            //Update
            userToUpdateOrDelete.name = inputName.value!!
            userToUpdateOrDelete.email = inputEmail.value!!
            update(userToUpdateOrDelete)

        } else {
            //Insert
            insert(User(0, inputName.value!!, inputEmail.value!!))
            inputName.value = null
            inputEmail.value = null
        }
    }

    fun clearAllOrDelete() {
        if (isUpdateOrDelete)
            delete(userToUpdateOrDelete)
        else
            clearAll()
    }

    private fun insert(user: User) = viewModelScope.launch {
        repository.insert(user)
    }

    private fun clearAll() = viewModelScope.launch {
        repository.deleteAll()
    }

    private fun update(user: User) = viewModelScope.launch {
        repository.update(user)
        //Reset the buttons and fields
        inputName.value = null
        inputEmail.value = null
        isUpdateOrDelete = false
        saveOrUpdateButton.value = "Save"
        clearALlOrDeleteAllButton.value = "Clear All"
    }

    private fun delete(user: User) = viewModelScope.launch {
        repository.delete(user)
        //Reset the buttons and fields
        inputName.value = null
        inputEmail.value = null
        isUpdateOrDelete = false
        saveOrUpdateButton.value = "Save"
        clearALlOrDeleteAllButton.value = "Clear All"
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}

    fun initUpdateAndDelete(selectedItem: User) {
        //Reset the buttons and fields
        inputName.value = selectedItem.name
        inputEmail.value = selectedItem.email
        isUpdateOrDelete = true
        userToUpdateOrDelete = selectedItem
        saveOrUpdateButton.value = "Update"
        clearALlOrDeleteAllButton.value = "Delete"
    }

}