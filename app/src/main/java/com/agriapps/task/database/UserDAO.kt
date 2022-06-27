package com.agriapps.task.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDAO {

    @Insert
    suspend fun insertSubscriber(userDetails: UserDetails): Long

    @Update
    suspend fun updateSubscriber(userDetails: UserDetails): Int

    @Delete
    suspend fun deleteSubscriber(userDetails: UserDetails): Int

    @Query("DELETE FROM user_data_table")
    suspend fun deleteAll(): Int

    @Query("SELECT * FROM user_data_table")
    fun getAllSubscribers(): LiveData<List<UserDetails>>
}