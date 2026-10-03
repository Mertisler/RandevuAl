package com.loc.appointmentapp.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.loc.appointmentapp.domain.model.Role
import com.loc.appointmentapp.domain.model.User
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthManager @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    // İş Akışı: Firebase Auth ile giriş yapılır, dönen UID ile Firestore'dan kullanıcının rolü okunur.
    suspend fun login(email: String, sifre: String): User {
        val authResult = auth.signInWithEmailAndPassword(email, sifre).await()
        val uid = authResult.user?.uid ?: throw Exception("Kullanıcı ID bulunamadı")

        val document = firestore.collection("users").document(uid).get().await()
        return document.toObject(User::class.java) ?: throw Exception("Kullanıcı verisi eksik")
    }

    // İş Akışı: Auth üzerinde yeni kullanıcı oluşturulur, ardından Firestore'a varsayılan CLIENT rolüyle kaydedilir.
    suspend fun register(email: String, sifre: String, isim: String): User {
        val authResult = auth.createUserWithEmailAndPassword(email, sifre).await()
        val uid = authResult.user?.uid ?: throw Exception("Kayıt başarısız")

        val newUser = User(id = uid, name = isim, email = email, role = Role.CLIENT)
        firestore.collection("users").document(uid).set(newUser).await()
        return newUser
    }

    suspend fun getCurrentUser(): User? {
        val uid = auth.currentUser?.uid ?: return null
        val document = firestore.collection("users").document(uid).get().await()
        return document.toObject(User::class.java)
    }

    fun logout() {
        auth.signOut()
    }
}