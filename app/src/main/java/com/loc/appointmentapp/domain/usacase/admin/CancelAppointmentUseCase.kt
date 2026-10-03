package com.loc.appointmentapp.domain.usecase.admin

import com.loc.appointmentapp.domain.repository.CalendarRepository
import javax.inject.Inject

class CancelAppointmentUseCase @Inject constructor(
    private val calendarRepository: CalendarRepository
) {
    // İş Akışı: Admin bir randevuyu iptal etmek istediğinde bu sınıf çağrılır ve ilgili randevunun durumu güncellenir.
    suspend operator fun invoke(appointmentId: String): Result<Unit> {
        if (appointmentId.isBlank()) {
            return Result.failure(Exception("Geçersiz randevu numarası."))
        }
        return calendarRepository.cancelAppointment(appointmentId)
    }
}