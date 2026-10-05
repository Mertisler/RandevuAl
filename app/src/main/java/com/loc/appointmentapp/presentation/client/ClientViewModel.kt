package com.loc.appointmentapp.presentation.client

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loc.appointmentapp.domain.model.Appointment
import com.loc.appointmentapp.domain.usecase.client.BookAppointmentUseCase
import com.loc.appointmentapp.domain.usecase.client.GetFreeHoursUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientViewModel @Inject constructor(
    private val getFreeHoursUseCase: GetFreeHoursUseCase,
    private val bookAppointmentUseCase: BookAppointmentUseCase
) : ViewModel() {

    private val _freeHours = MutableStateFlow<List<String>>(emptyList())
    val freeHours: StateFlow<List<String>> = _freeHours.asStateFlow()

    private val _bookingStatus = MutableStateFlow<String>("")
    val bookingStatus: StateFlow<String> = _bookingStatus.asStateFlow()

    // İş Akışı: Müşteri takvimden bir gün seçtiğinde bu metot çağrılır ve sadece boş saatler UI'a aktarılır.
    fun loadFreeHours(tarih: String) {
        viewModelScope.launch {
            getFreeHoursUseCase(tarih).onSuccess { saatler ->
                _freeHours.value = saatler
            }
        }
    }

    // İş Akışı: Müşteri bir boş saate tıklayıp onayladığında yeni randevu modeli oluşturulup UseCase'e gönderilir.
    fun bookAppointment(tarih: String, saat: String, musteriId: String) {
        viewModelScope.launch {
            val yeniRandevu = Appointment(
                musteriId = musteriId,
                tarih = tarih,
                saat = saat
            )
            bookAppointmentUseCase(yeniRandevu).fold(
                onSuccess = {
                    _bookingStatus.value = "Randevunuz başarıyla oluşturuldu, onay bekliyor."
                    loadFreeHours(tarih) // Listeyi yenile
                },
                onFailure = {
                    _bookingStatus.value = "Hata: ${it.message}"
                }
            )
        }
    }
}