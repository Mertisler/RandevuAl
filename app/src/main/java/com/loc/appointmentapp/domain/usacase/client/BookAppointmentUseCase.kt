package com.loc.appointmentapp.domain.usecase.client

import com.loc.appointmentapp.domain.model.Appointment
import com.loc.appointmentapp.domain.repository.CalendarRepository
import javax.inject.Inject

class BookAppointmentUseCase @Inject constructor(
    private val calendarRepository: CalendarRepository
) {
    // İş Akışı: Seçilen tarih/saat doğrulanır. Boş ise randevu oluşturma isteği repository'ye iletilir.
    suspend operator fun invoke(appointment: Appointment): Result<Unit> {
        // İsteğe bağlı: Burada saat boş mu diye son bir check (validation) yapılabilir.
        return calendarRepository.bookAppointment(appointment)
    }
}