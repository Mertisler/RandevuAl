package com.loc.appointmentapp.data.repository

import com.loc.appointmentapp.data.remote.FirebaseAuthManager
import com.loc.appointmentapp.domain.model.User
import com.loc.appointmentapp.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authManager: FirebaseAuthManager
) : AuthRepository {

    override suspend fun login(email: String, sifre: String): Result<User> {
        return try {
            val user = authManager.login(email, sifre)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun register(email: String, sifre: String, isim: String): Result<User> {
        return try {
            val user = authManager.register(email, sifre, isim)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCurrentUser(): Result<User?> {
        return try {
            val user = authManager.getCurrentUser()
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun logout(): Result<Unit> {
        return try {
            authManager.logout()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}