package com.example.task8

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

data class Task(val id: String, val task: String)

@Composable
fun TodoScreen(navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser
    val db = FirebaseFirestore.getInstance()

    var taskText by remember { mutableStateOf("") }
    var tasks = remember { mutableStateListOf<Task>() }
    var editingTaskId by remember { mutableStateOf<String?>(null) }

    // Fetch tasks in real-time
    LaunchedEffect(true) {
        db.collection("tasks").whereEqualTo("uid", user?.uid)
            .addSnapshotListener { snapshot, _ ->
                tasks.clear()
                snapshot?.documents?.forEach { doc ->
                    val task = doc.getString("task") ?: return@forEach
                    tasks.add(Task(doc.id, task))
                }
            }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = taskText,
                onValueChange = { taskText = it },
                label = { Text(if (editingTaskId == null) "Add Task" else "Edit Task") },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    if (taskText.isBlank()) return@Button

                    if (editingTaskId == null) {
                        // Add new task
                        db.collection("tasks").add(
                            mapOf("task" to taskText, "uid" to user?.uid)
                        )
                    } else {
                        // Update existing task
                        db.collection("tasks").document(editingTaskId!!).update("task", taskText)
                        editingTaskId = null
                    }
                    taskText = ""
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(if (editingTaskId == null) "Add" else "Update")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(tasks, key = { it.id }) { task ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF04FA8F)
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = task.task, modifier = Modifier.weight(1f))

                        IconButton(onClick = {
                            taskText = task.task
                            editingTaskId = task.id
                        }) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit")
                        }

                        IconButton(onClick = {
                            db.collection("tasks").document(task.id).delete()
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete")
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                auth.signOut()
                navController.navigate("login") {
                    popUpTo("todo") { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) {
            Text("Logout")
        }
    }
}
