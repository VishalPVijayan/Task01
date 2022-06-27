package com.agriapps.task.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user_data_table")
data class UserDetails (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_name")
    var id: Int,
    @ColumnInfo(name = "user_id")
    var name: String,
    @ColumnInfo(name = "user_email")
    var email: String,
    @ColumnInfo(name = "user_phone")
    var phone: String,
    @ColumnInfo(name = "user_address")
    var address: String
)