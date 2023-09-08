package com.techmania.userroomdatabase.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.techmania.userroomdatabase.model.User

@Database(entities = [User::class], version = 1)
abstract class UserDataBase : RoomDatabase() {

    abstract val userDAO: UserDAO

    // Singleton design pattern
    companion object {
        @Volatile
        private var INSTANCE: UserDataBase? = null
        fun getInstance(context: Context): UserDataBase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserDataBase::class.java,
                        "users_db"
                    ).build()
                }
                return instance
            }

        }
    }

}