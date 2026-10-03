package com.loc.appointmentapp.domain.usecase.admin

import com.loc.appointmentapp.domain.model.Appointment
import com.loc.appointmentapp.domain.repository.CalendarRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllAppointmentsUseCase @Inject constructor(
    private val calendarRepository: CalendarRepository
) {
    // İş Akışı: Admin paneli açıldığında veritabanındaki tüm randevular canlı akış (Flow) olarak bağlanır.
    // Yeni randevu düştüğünde arayüz anında güncellenir.
    operator fun invoke(): Flow<List<Appointment>> {
        return calendarRepository.getAllAppointments()
    }
}