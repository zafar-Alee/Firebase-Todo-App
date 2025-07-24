package com.example.task8

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF50705C)),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .height(360.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF04FA8F)
            ),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                CustomTextField2(value = email, label = "Email", onValueChange = { email = it })

                CustomTextField2(
                    value = password,
                    label = "Password",
                    onValueChange = { password = it },
                    isPassword = true
                )

                Button(
                    onClick = {
                        auth.signInWithEmailAndPassword(email, password)
                            .addOnSuccessListener { navController.navigate("todo") }
                            .addOnFailureListener {
                                Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show()
                            }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Login")
                }

                Text(
                    "Don't have an account?",
                    modifier = Modifier
                        .clickable { navController.navigate("register") }
                        .padding(top = 4.dp),
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun CustomTextField2(
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        placeholder = { Text("Enter $label") },
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        singleLine = true,
        shape = MaterialTheme.shapes.medium,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            focusedIndicatorColor = Color(0xFF2FAD56),
            unfocusedIndicatorColor = Color.Gray
        ),
        modifier = Modifier
            .fillMaxWidth()
    )
}
