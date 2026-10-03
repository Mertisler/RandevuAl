package com.loc.appointmentapp.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.loc.appointmentapp.data.remote.FirebaseAuthManager
import com.loc.appointmentapp.data.remote.FirestoreDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // İş Akışı: Uygulama boyunca tek bir FirebaseAuth örneği (Singleton) yaşar ve isteyen sınıflara dağıtılır.
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    // İş Akışı: Manager sınıfları, oluşturulan Firebase örneklerini parametre olarak alıp Hilt tarafından enjekte edilebilir hale gelir.
    @Provides
    @Singleton
    fun provideFirebaseAuthManager(
        auth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): FirebaseAuthManager = FirebaseAuthManager(auth, firestore)

    @Provides
    @Singleton
    fun provideFirestoreDataSource(
        firestore: FirebaseFirestore
    ): FirestoreDataSource = FirestoreDataSource(firestore)
}