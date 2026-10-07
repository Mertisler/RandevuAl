package com.loc.appointmentapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.loc.appointmentapp.presentation.navigation.AppNavigation
import dagger.hilt.android.AndroidEntryPoint

// İş Akışı: @AndroidEntryPoint notasyonu, Hilt'e bu aktivitenin bağımlılık enjeksiyonu
// sürecine dahil olduğunu ve altındaki tüm Compose ekranlarına (ViewModel'lar vb.)
// gerekli sınıfları sağlayabileceğini bildirir.
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Uygulamanın ekran rotaları buradan başlatılır
                    AppNavigation()
                }
            }
        }
    }
}