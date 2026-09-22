package com.example.campusconnect.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.campusconnect.data.CampusRepository
import kotlinx.coroutines.launch
import androidx.compose.ui.platform.LocalContext
import com.example.campusconnect.auth.TokenManager

/**
 * Displays the CampusConnect sign-in screen.
 *
 * Authentication is performed using the CampusConnect REST API.
 */
@Composable
fun LoginScreen(
    onSignIn: () -> Unit
) {

    var email by rememberSaveable {
        mutableStateOf("")
    }

    var password by rememberSaveable {
        mutableStateOf("")
    }

    var errorMessage by rememberSaveable {
        mutableStateOf<String?>(null)
    }

    var isLoading by rememberSaveable {
        mutableStateOf(false)
    }

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val repository = CampusRepository()
    val tokenManager = TokenManager(context)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "CampusConnect",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Your Student Campus Companion",
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Sign in using your campus account",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                errorMessage = null
            },
            label = {
                Text("Campus email")
            },
            placeholder = {
                Text("student@campus.ac.za")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            singleLine = true,
            enabled = !isLoading,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                errorMessage = null
            },
            label = {
                Text("Password")
            },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            singleLine = true,
            enabled = !isLoading,
            modifier = Modifier.fillMaxWidth()
        )

        if (errorMessage != null) {

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = errorMessage ?: "",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {

                if (email.isBlank() || password.isBlank()) {

                    errorMessage =
                        "Please enter both your campus email and password."

                    Log.w(
                        "CampusLogin",
                        "Sign-in rejected: incomplete details"
                    )

                } else {

                    isLoading = true
                    errorMessage = null

                    coroutineScope.launch {

                        val result = repository.login(
                            email = email,
                            password = password
                        )

                        result
                            .onSuccess { authResponse ->

                                isLoading = false

                                tokenManager.saveToken(authResponse.token)

                                Log.i(
                                    "CampusLogin",
                                    "Login successful for ${authResponse.email}"
                                )

                                Log.i(
                                    "CampusLogin",
                                    "User ID: ${authResponse.userId}"
                                )

                                Log.i(
                                    "CampusLogin",
                                    "JWT saved successfully"
                                )

                                onSignIn()
                            }
                            .onFailure { exception ->

                                isLoading = false

                                Log.e(
                                    "CampusLogin",
                                    "Login failed",
                                    exception
                                )

                                errorMessage =
                                    "Login failed: ${exception.message}"
                            }
                    }
                }
            },
            enabled = !isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {

            if (isLoading) {

                CircularProgressIndicator()

            } else {

                Text("Sign In")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Authentication secured through the CampusConnect REST API.",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}