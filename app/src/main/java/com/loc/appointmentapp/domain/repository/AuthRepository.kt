package com.loc.appointmentapp.domain.repository

import com.loc.appointmentapp.domain.model.User

interface AuthRepository {
    // Kullanıcı giriş yapar ve sunucudan User modeli (rolü ile birlikte) döner.
    suspend fun login(email: String, sifre: String): Result<User>

    // Yeni kullanıcı kaydı oluşturulur.
    suspend fun register(email: String, sifre: String, isim: String): Result<User>

    // Uygulama açıldığında halihazırda oturum açmış kullanıcı var mı kontrol edilir.
    suspend fun getCurrentUser(): Result<User?>

    // Mevcut oturum sonlandırılır.
    suspend fun logout(): Result<Unit>
}