package com.loc.appointmentapp.di

import com.loc.appointmentapp.data.repository.AuthRepositoryImpl
import com.loc.appointmentapp.data.repository.CalendarRepoImpl
import com.loc.appointmentapp.domain.repository.AuthRepository
import com.loc.appointmentapp.domain.repository.CalendarRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    // İş Akışı: Bir UseCase "AuthRepository" arayüzünü istediğinde, Hilt ona otomatik olarak "AuthRepositoryImpl" sınıfını verir.
    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindCalendarRepository(
        calendarRepoImpl: CalendarRepoImpl
    ): CalendarRepository
}