package com.loc.appointmentapp.data.repository

import com.loc.appointmentapp.data.remote.FirestoreDataSource
import com.loc.appointmentapp.domain.model.Appointment
import com.loc.appointmentapp.domain.model.AppointmentStatus
import com.loc.appointmentapp.domain.repository.CalendarRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CalendarRepoImpl @Inject constructor(
    private val firestoreDataSource: FirestoreDataSource
) : CalendarRepository {

    // İş Akışı: İşletmenin tüm çalışma saatleri tanımlanır. Veritabanından dolu olan saatler çekilir ve filtrelenerek sadece boş saatler döndürülür.
    override suspend fun getFreeHours(tarih: String): Result<List<String>> {
        return try {
            val allHours = listOf("09:00", "10:00", "11:00", "13:00", "14:00", "15:00", "16:00")
            val bookedHours = firestoreDataSource.getBookedHours(tarih)

            val freeHours = allHours.filterNot { it in bookedHours }
            Result.success(freeHours)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun bookAppointment(appointment: Appointment): Result<Unit> {
        return try {
            firestoreDataSource.bookAppointment(appointment)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getAllAppointments(): Flow<List<Appointment>> {
        return firestoreDataSource.getAllAppointments()
    }

    override suspend fun cancelAppointment(appointmentId: String): Result<Unit> {
        return try {
            firestoreDataSource.updateAppointmentStatus(appointmentId, AppointmentStatus.IPTAL)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}