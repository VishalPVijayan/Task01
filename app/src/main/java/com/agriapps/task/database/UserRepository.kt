package com.agriapps.task.database


class UserRepository(private val dao: UserDAO) {

    val userdetails = dao.getAllSubscribers()

    suspend fun insert(userdetails: UserDetails): Long {
        return dao.insertSubscriber(userdetails)
    }

    suspend fun update(userdetails: UserDetails): Int {
        return dao.updateSubscriber(userdetails)
    }

    suspend fun delete(userdetails: UserDetails): Int {
        return dao.deleteSubscriber(userdetails)
    }

    suspend fun deleteAll(): Int {
        return dao.deleteAll()
    }
}