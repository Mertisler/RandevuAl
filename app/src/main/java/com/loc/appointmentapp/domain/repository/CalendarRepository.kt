package com.loc.appointmentapp.domain.repository

import com.loc.appointmentapp.domain.model.Appointment
import kotlinx.coroutines.flow.Flow

interface CalendarRepository {
    // Belirli bir tarihteki sadece "boş" olan saat dilimlerini getirir.
    suspend fun getFreeHours(tarih: String): Result<List<String>>

    // Müşterinin seçtiği tarih ve saat için yeni bir randevu kaydı oluşturur.
    suspend fun bookAppointment(appointment: Appointment): Result<Unit>

    // Admin ekranı için tüm randevuları (bekleyen/onaylanan/iptal edilen) anlık akış olarak getirir.
    fun getAllAppointments(): Flow<List<Appointment>>

    // Adminin belirtilen ID'ye sahip randevunun durumunu "IPTAL" olarak güncellemesini sağlar.
    suspend fun cancelAppointment(appointmentId: String): Result<Unit>
}