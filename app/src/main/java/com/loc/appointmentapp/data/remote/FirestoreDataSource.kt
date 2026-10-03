package com.loc.appointmentapp.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.loc.appointmentapp.domain.model.Appointment
import com.loc.appointmentapp.domain.model.AppointmentStatus
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val appointmentsCollection = firestore.collection("appointments")

    // İş Akışı: Belirtilen tarihteki randevular çekilir, mevcut çalışma saatlerinden çıkarılarak "boş" saatler hesaplanır.
    suspend fun getBookedHours(tarih: String): List<String> {
        val snapshot = appointmentsCollection
            .whereEqualTo("tarih", tarih)
            .whereNotEqualTo("durum", AppointmentStatus.IPTAL.name)
            .get()
            .await()
        return snapshot.documents.mapNotNull { it.getString("saat") }
    }

    // İş Akışı: Yeni randevu objesi Firestore'a yazılır.
    suspend fun bookAppointment(appointment: Appointment) {
        val docRef = appointmentsCollection.document()
        val newAppointment = appointment.copy(id = docRef.id)
        docRef.set(newAppointment).await()
    }

    // İş Akışı: Admin paneli için veritabanındaki randevular canlı (real-time) olarak dinlenir. Değişiklik anında Flow ile UI'a aktarılır.
    fun getAllAppointments(): Flow<List<Appointment>> = callbackFlow {
        val listener = appointmentsCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            val appointments = snapshot?.documents?.mapNotNull {
                it.toObject(Appointment::class.java)
            } ?: emptyList()
            trySend(appointments)
        }
        awaitClose { listener.remove() }
    }

    // İş Akışı: Belirtilen randevu ID'si bulunur ve sadece "durum" alanı IPTAL olarak güncellenir.
    suspend fun updateAppointmentStatus(appointmentId: String, status: AppointmentStatus) {
        appointmentsCollection.document(appointmentId)
            .update("durum", status.name)
            .await()
    }
}