package com.loc.appointmentapp.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loc.appointmentapp.domain.model.Role
import com.loc.appointmentapp.domain.usecase.auth.LoginAndCheckRoleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginAndCheckRoleUseCase
) : ViewModel() {

    // UI'ın dinleyeceği durum değişkenleri
    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    // İş Akışı: UI'dan email ve şifre gelir. UseCase tetiklenir.
    // Sonuç başarılıysa kullanıcının rolüne göre yönlendirme (Navigate) state'i güncellenir.
    fun login(email: String, sifre: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading

            loginUseCase(email, sifre).fold(
                onSuccess = { user ->
                    when (user.role) {
                        Role.ADMIN -> _authState.value = AuthState.NavigateToAdmin
                        Role.CLIENT -> _authState.value = AuthState.NavigateToClient
                    }
                },
                onFailure = { hata ->
                    _authState.value = AuthState.Error(hata.message ?: "Giriş başarısız")
                }
            )
        }
    }
}

// Ekranın alabileceği durumlar
sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object NavigateToAdmin : AuthState()
    object NavigateToClient : AuthState()
    data class Error(val message: String) : AuthState()
}