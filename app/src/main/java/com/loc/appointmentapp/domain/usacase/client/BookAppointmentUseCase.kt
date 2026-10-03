package com.loc.appointmentapp.domain.usecase.client

import com.loc.appointmentapp.domain.repository.CalendarRepository
import javax.inject.Inject

class GetFreeHoursUseCase @Inject constructor(
    private val calendarRepository: CalendarRepository
) {
    // İş Akışı: Müşteri bir tarih seçtiğinde, o tarihe ait dolu saatler çıkarılıp sadece uygun olanlar döndürülür.
    suspend operator fun invoke(tarih: String): Result<List<String>> {
        return calendarRepository.getFreeHours(tarih)
    }
}