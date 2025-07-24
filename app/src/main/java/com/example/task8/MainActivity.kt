package com.example.task8

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.task8.ui.theme.Task8Theme
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        setContent {
            Task8Theme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "register"
                ) {
                    composable("register") { RegisterScreen(navController) }
                    composable("login") { LoginScreen(navController) }
                    composable("todo") { TodoScreen(navController) }
                }
            }
        }
    }
}
