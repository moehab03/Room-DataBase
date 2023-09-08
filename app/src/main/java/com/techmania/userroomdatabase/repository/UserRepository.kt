package com.techmania.userroomdatabase.repository

import com.techmania.userroomdatabase.model.User
import com.techmania.userroomdatabase.room.UserDAO

class UserRepository(private val dao: UserDAO) {
    val users = dao.getAllUsers()

    suspend fun insert(user: User) : Long {
        return dao.insert(user)
    }

    suspend fun delete(user: User){
        return dao.delete(user)
    }

    suspend fun update(user: User){
        return dao.update(user)
    }
    suspend fun deleteAll(){
        return dao.deleteAllData()
    }
}