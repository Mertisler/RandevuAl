package com.loc.appointmentapp.presentation.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun DashboardScreen(
    viewModel: AdminViewModel = hiltViewModel()
) {
    // İş Akışı: ViewModel'daki StateFlow dinlenir. Liste güncellendikçe ekran otomatik olarak yeniden çizilir (Recomposition).
    val appointments by viewModel.appointments.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "İşletme Randevu Takvimi", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(appointments) { randevu ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Tarih: ${randevu.tarih} - Saat: ${randevu.saat}")
                        Text(text = "Durum: ${randevu.durum}")

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = { viewModel.cancelAppointment(randevu.id) },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                        ) {
                            Text("İptal Et")
                        }
                    }
                }
            }
        }
    }
}