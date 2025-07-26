package com.example.task8

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(onFinished: () -> Unit = {}) {
    LaunchedEffect(Unit) {
        delay(2000)
        onFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black), // ✅ black background
        contentAlignment = Alignment.Center // ✅ center content
    ) {
        Image(
            painter = painterResource(R.drawable.firebase),
            contentDescription = "Splash",
            modifier = Modifier
                .size(200.dp) // ✅ large image size
        )
    }
}



@Composable
fun MyAppNavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen {
                navController.navigate("register") {
                    popUpTo("splash") { inclusive = true }
                }
            }
        }

        composable("register") { RegisterScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("todo") { TodoScreen(navController) }
    }
}
