package com.example.loginmvvmapp.data.repository

import com.example.loginmvvmapp.data.local.dao.UserDao
import com.example.loginmvvmapp.data.local.entity.User

class UserRepository(
    private val userDao: UserDao
) {
    suspend fun insert(user: User) {
        userDao.insert(user)
    }

    suspend fun login(username: String, password: String): User? {
        return userDao.login(username, password)
    }
}