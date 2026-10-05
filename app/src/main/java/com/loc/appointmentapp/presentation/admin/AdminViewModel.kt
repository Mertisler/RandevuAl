package com.loc.appointmentapp.presentation.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loc.appointmentapp.domain.model.Appointment
import com.loc.appointmentapp.domain.usecase.admin.CancelAppointmentUseCase
import com.loc.appointmentapp.domain.usecase.admin.GetAllAppointmentsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor(
    private val getAllAppointmentsUseCase: GetAllAppointmentsUseCase,
    private val cancelAppointmentUseCase: CancelAppointmentUseCase
) : ViewModel() {

    private val _appointments = MutableStateFlow<List<Appointment>>(emptyList())
    val appointments: StateFlow<List<Appointment>> = _appointments.asStateFlow()

    init {
        // ViewModel oluştuğu anda tüm randevuları dinlemeye başla
        observeAppointments()
    }

    // İş Akışı: Firestore'daki randevu tablosu sürekli dinlenir. Yeni bir kayıt eklendiğinde liste anında güncellenir.
    private fun observeAppointments() {
        viewModelScope.launch {
            getAllAppointmentsUseCase()
                .catch { /* Hata loglaması yapılabilir */ }
                .collect { liste ->
                    _appointments.value = liste
                }
        }
    }

    // İş Akışı: Admin arayüzden iptal butonuna bastığında randevu durumu güncellenir.
    fun cancelAppointment(appointmentId: String) {
        viewModelScope.launch {
            cancelAppointmentUseCase(appointmentId)
        }
    }
}