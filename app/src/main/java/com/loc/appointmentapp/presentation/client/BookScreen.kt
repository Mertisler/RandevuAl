package com.loc.appointmentapp.presentation.client

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun BookScreen(
    viewModel: ClientViewModel = hiltViewModel(),
    musteriId: String
) {
    val freeHours by viewModel.freeHours.collectAsState()
    val bookingStatus by viewModel.bookingStatus.collectAsState()

    var secilenTarih by remember { mutableStateOf("2026-10-05") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Randevu Al", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // İş Akışı: Kullanıcı tarih seçer. Butona basıldığında ViewModel üzerinden veritabanındaki o güne ait boş saatler sorgulanır.
        OutlinedTextField(
            value = secilenTarih,
            onValueChange = { secilenTarih = it },
            label = { Text("Tarih (YYYY-AA-GG)") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { viewModel.loadFreeHours(secilenTarih) },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("Boş Saatleri Getir")
        }

        if (bookingStatus.isNotEmpty()) {
            Text(text = bookingStatus, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(vertical = 8.dp))
        }

        LazyColumn {
            items(freeHours) { saat ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = saat)

                        // İş Akışı: Müşteri listelenen boş saatlerden birine tıklar. ViewModel'a randevu oluşturma isteği gönderilir ve takvim güncellenir.
                        Button(
                            onClick = { viewModel.bookAppointment(secilenTarih, saat, musteriId) }
                        ) {
                            Text("Seç")
                        }
                    }
                }
            }
        }
    }
}