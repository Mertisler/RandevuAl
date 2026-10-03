package com.loc.appointmentapp.domain.usecase.auth

import com.loc.appointmentapp.domain.model.User
import com.loc.appointmentapp.domain.repository.AuthRepository
import javax.inject.Inject

class LoginAndCheckRoleUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    // İş Akışı: Kullanıcı e-posta ve şifresi repository'ye iletilir. Gelen User objesi UI'a döndürülür.
    // Yönlendirme (Admin/Client) bu dönüşe göre ViewModel'da yapılacaktır.
    suspend operator fun invoke(email: String, sifre: String): Result<User> {
        if (email.isBlank() || sifre.isBlank()) {
            return Result.failure(Exception("E-posta ve şifre boş bırakılamaz."))
        }
        return authRepository.login(email, sifre)
    }
}