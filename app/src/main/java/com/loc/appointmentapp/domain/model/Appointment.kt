package com.loc.appointmentapp.domain.model

data class Appointment(
    val id: String = "",
    val musteriId: String = "",
    val tarih: String = "", // Örn: "2026-10-01"
    val saat: String = "",  // Örn: "14:30"
    val durum: AppointmentStatus = AppointmentStatus.BEKLIYOR
)

enum class AppointmentStatus {
    BEKLIYOR,
    ONAYLANDI,
    IPTAL
}
