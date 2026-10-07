package com.loc.appointmentapp.presentation.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login_screen")

    // İş Akışı: Müşteri ekranına giderken kullanıcının ID'si parametre olarak rotaya eklenir.
    // Örn: "client_book_screen/12345"
    object ClientBook : Screen("client_book_screen/{musteriId}") {
        fun createRoute(musteriId: String) = "client_book_screen/$musteriId"
    }

    object AdminDashboard : Screen("admin_dashboard_screen")
}