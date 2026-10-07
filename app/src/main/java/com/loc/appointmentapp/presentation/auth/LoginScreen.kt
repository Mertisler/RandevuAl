package com.loc.appointmentapp.presentation.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LoginScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onNavigateToAdmin: () -> Unit,
    onNavigateToClient: (String) -> Unit // () -> Unit yerine (String) -> Unit yapıldı
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val authState by viewModel.authState.collectAsState()

    // İş Akışı: ViewModel'dan gelen durum (State) anlık olarak dinlenir.
    // Başarılı giriş sonrası role göre ilgili ekrana yönlendirme (navigation) tetiklenir.
    // İş Akışı: ViewModel'dan State dinlenir. Müşteri girişi ise, State içindeki musteriId alınarak yönlendirmeye (Navigation) iletilir.
    LaunchedEffect(authState) {
        when (val state = authState) { // state'i değişkene atadık ki içindeki değere ulaşabilelim
            is AuthState.NavigateToAdmin -> onNavigateToAdmin()
            is AuthState.NavigateToClient -> onNavigateToClient(state.musteriId) // ID fırlatılıyor
            else -> {}
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Giriş Yap", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("E-posta") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Şifre") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // İş Akışı: Butona tıklandığında UI, e-posta ve şifre verisini ViewModel'a ileterek iş kuralını (UseCase) başlatır.
        Button(
            onClick = { viewModel.login(email, password) },
            modifier = Modifier.fillMaxWidth(),
            enabled = authState !is AuthState.Loading
        ) {
            if (authState is AuthState.Loading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
            } else {
                Text("Giriş")
            }
        }

        if (authState is AuthState.Error) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = (authState as AuthState.Error).message, color = MaterialTheme.colorScheme.error)
        }
    }
}