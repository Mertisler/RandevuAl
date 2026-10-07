package com.loc.appointmentapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.loc.appointmentapp.presentation.admin.DashboardScreen
import com.loc.appointmentapp.presentation.auth.LoginScreen
import com.loc.appointmentapp.presentation.client.BookScreen

@Composable
fun AppNavigation() {
    // İş Akışı: NavController, Compose içindeki tüm gezinme işlemlerini takip eden ve yöneten objedir.
    val navController = rememberNavController()

    // Başlangıç ekranı olarak Login (Giriş) belirlenir.
    NavHost(navController = navController, startDestination = Screen.Login.route) {

        // 1. Giriş Ekranı Rotası
        composable(route = Screen.Login.route) {
            LoginScreen(
                onNavigateToAdmin = {
                    // İş Akışı: Admine geçilirken, geri tuşuna basıldığında tekrar Login'e dönülmemesi için
                    // popUpTo ile arka plandaki Login ekranı yığından (backstack) silinir.
                    navController.navigate(Screen.AdminDashboard.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToClient = { musteriId ->
                    navController.navigate(Screen.ClientBook.createRoute(musteriId)) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        // 2. Müşteri Ekranı Rotası
        composable(
            route = Screen.ClientBook.route,
            arguments = listOf(navArgument("musteriId") { type = NavType.StringType })
        ) { backStackEntry ->
            // İş Akışı: Rotadan gelen 'musteriId' parametresi yakalanır ve BookScreen'e iletilir.
            val musteriId = backStackEntry.arguments?.getString("musteriId") ?: ""
            BookScreen(musteriId = musteriId)
        }

        // 3. Admin Ekranı Rotası
        composable(route = Screen.AdminDashboard.route) {
            DashboardScreen()
        }
    }
}