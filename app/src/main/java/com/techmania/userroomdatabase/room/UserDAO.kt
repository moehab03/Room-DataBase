package com.techmania.userroomdatabase.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.techmania.userroomdatabase.model.User

@Dao
interface UserDAO {
    @Insert
    suspend fun insert(user: User) : Long

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("DELETE FROM user ")
    suspend fun deleteAllData()

    @Query("SELECT * FROM user ORDER BY user_id ASC")
    fun getAllUsers() : LiveData<List<User>>
}