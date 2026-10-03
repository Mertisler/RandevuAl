package com.loc.appointmentapp.domain.model

data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val role: Role = Role.CLIENT
)

enum class Role {
    ADMIN,
    CLIENT
}