package com.example.campusconnect.ui

import android.content.Context
import android.content.ContextWrapper
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import com.example.campusconnect.BiometricAuthenticator
import com.example.campusconnect.R

private const val BIOMETRIC_PREFERENCES =
    "campus_biometric_preferences"

private const val PREVIOUS_LOGIN_KEY =
    "previous_login_completed"

@Composable
fun LoginScreen(
    onSignIn: () -> Unit
) {
    val context = LocalContext.current
    val activity = context.findFragmentActivity()

    val currentLanguage =
        LocalConfiguration.current.locales[0].language

    val isZulu = currentLanguage == "zu"

    val biometricButtonText =
        if (isZulu) {
            "Ngena ngezigxivizo zeminwe noma ngobuso"
        } else {
            "Sign in with fingerprint or face"
        }

    val biometricTitle =
        if (isZulu) {
            "Ukuqinisekisa nge-biometric"
        } else {
            "Biometric authentication"
        }

    val biometricSubtitle =
        if (isZulu) {
            "Sebenzisa isigxivizo somunwe noma ubuso bakho ukuze ungene"
        } else {
            "Use your fingerprint or face to sign in"
        }

    val cancelText =
        if (isZulu) {
            "Khansela"
        } else {
            "Cancel"
        }

    val firstLoginMessage =
        if (isZulu) {
            "Qala ungene nge-imeyili nephasiwedi ukuze uvule ukungena nge-biometric."
        } else {
            "Sign in with your email and password once before using biometric login."
        }

    val biometricSuccessMessage =
        if (isZulu) {
            "Ukuqinisekisa nge-biometric kuphumelele."
        } else {
            "Biometric authentication successful."
        }

    var email by rememberSaveable {
        mutableStateOf("")
    }

    var password by rememberSaveable {
        mutableStateOf("")
    }

    var showError by rememberSaveable {
        mutableStateOf(false)
    }

    var biometricMessage by rememberSaveable {
        mutableStateOf<String?>(null)
    }

    val preferences = context.getSharedPreferences(
        BIOMETRIC_PREFERENCES,
        Context.MODE_PRIVATE
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = stringResource(
                R.string.campus_companion
            ),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )

        Spacer(
            modifier = Modifier.height(40.dp)
        )

        Text(
            text = stringResource(
                R.string.campus_account_prompt
            ),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                showError = false
                biometricMessage = null
            },
            label = {
                Text(
                    stringResource(
                        R.string.campus_email
                    )
                )
            },
            placeholder = {
                Text(
                    stringResource(
                        R.string.campus_email_example
                    )
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                showError = false
                biometricMessage = null
            },
            label = {
                Text(
                    stringResource(
                        R.string.password
                    )
                )
            },
            visualTransformation =
                PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        if (showError) {
            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = stringResource(
                    R.string.login_incomplete
                ),
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.fillMaxWidth()
            )
        }

        biometricMessage?.let { message ->
            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = message,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Button(
            onClick = {
                if (
                    email.isBlank() ||
                    password.isBlank()
                ) {
                    showError = true
                    biometricMessage = null

                    Log.w(
                        "CampusLogin",
                        "Sign-in rejected: incomplete details"
                    )
                } else {
                    preferences
                        .edit()
                        .putBoolean(
                            PREVIOUS_LOGIN_KEY,
                            true
                        )
                        .apply()

                    Log.i(
                        "CampusLogin",
                        "Standard sign-in accepted"
                    )

                    onSignIn()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                stringResource(R.string.sign_in)
            )
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        OutlinedButton(
            onClick = {
                showError = false
                biometricMessage = null

                val hasPreviouslyLoggedIn =
                    preferences.getBoolean(
                        PREVIOUS_LOGIN_KEY,
                        false
                    )

                if (!hasPreviouslyLoggedIn) {
                    biometricMessage =
                        firstLoginMessage

                    Log.w(
                        "CampusBiometric",
                        "Biometric sign-in rejected: no previous login"
                    )

                    return@OutlinedButton
                }

                if (activity == null) {
                    biometricMessage =
                        if (isZulu) {
                            "Ukuqinisekisa nge-biometric akukwazanga ukuqaliswa."
                        } else {
                            "Biometric authentication could not be started."
                        }

                    return@OutlinedButton
                }

                BiometricAuthenticator.authenticate(
                    activity = activity,
                    title = biometricTitle,
                    subtitle = biometricSubtitle,
                    negativeButtonText = cancelText,
                    onSuccess = {
                        Log.i(
                            "CampusBiometric",
                            "Biometric authentication succeeded"
                        )

                        biometricMessage =
                            biometricSuccessMessage

                        onSignIn()
                    },
                    onError = { errorMessage ->
                        Log.w(
                            "CampusBiometric",
                            "Biometric authentication error: $errorMessage"
                        )

                        biometricMessage =
                            errorMessage
                    }
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.Fingerprint,
                contentDescription = null
            )

            Text(
                text = biometricButtonText,
                modifier = Modifier.padding(
                    start = 8.dp
                )
            )
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = stringResource(
                R.string.prototype_authentication
            ),
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            color =
                MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

private fun Context.findFragmentActivity():
        FragmentActivity? {
    return when (this) {
        is FragmentActivity -> this

        is ContextWrapper -> {
            baseContext.findFragmentActivity()
        }

        else -> null
    }
}