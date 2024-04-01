package com.example.learncode.repository

import com.example.learncode.model.User

interface UserRepository {
    suspend fun getUserID(userId: Int): User
}