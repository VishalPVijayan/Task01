package com.agriapps.task.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase



@Database(entities = [UserDetails::class], version = 1)
abstract class UserDatabaseDatabase : RoomDatabase() {
    abstract val userDAO : UserDAO

    companion object {
        @Volatile
        private var INSTANCE : UserDatabaseDatabase? = null
        fun getInstance(context: Context): UserDatabaseDatabase{
            synchronized(this) {
                var instance = INSTANCE
                if(instance==null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabaseDatabase::class.java,
                        "user_data_table"
                    ).build()
                }
                return instance
            }
        }
    }
}