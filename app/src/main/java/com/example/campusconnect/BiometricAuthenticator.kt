package com.example.campusconnect

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

object BiometricAuthenticator {

    private const val AUTHENTICATORS =
        BiometricManager.Authenticators.BIOMETRIC_STRONG

    fun availabilityMessage(
        activity: FragmentActivity
    ): String? {
        val biometricManager = BiometricManager.from(activity)

        return when (
            biometricManager.canAuthenticate(AUTHENTICATORS)
        ) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                null
            }

            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                "This device does not have fingerprint or facial recognition hardware."
            }

            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                "Biometric authentication is currently unavailable. Please try again."
            }

            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                "No fingerprint or face has been enrolled on this device."
            }

            BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED -> {
                "A security update is required before biometric authentication can be used."
            }

            BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED -> {
                "Biometric authentication is not supported on this Android device."
            }

            BiometricManager.BIOMETRIC_STATUS_UNKNOWN -> {
                "The biometric authentication status could not be determined."
            }

            else -> {
                "Biometric authentication is unavailable."
            }
        }
    }

    fun authenticate(
        activity: FragmentActivity,
        title: String,
        subtitle: String,
        negativeButtonText: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val availabilityError = availabilityMessage(activity)

        if (availabilityError != null) {
            onError(availabilityError)
            return
        }

        val executor = ContextCompat.getMainExecutor(activity)

        val biometricPrompt = BiometricPrompt(
            activity,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    onSuccess()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()

                    onError(
                        "Fingerprint or face not recognised. Please try again."
                    )
                }

                override fun onAuthenticationError(
                    errorCode: Int,
                    errorString: CharSequence
                ) {
                    super.onAuthenticationError(
                        errorCode,
                        errorString
                    )

                    onError(errorString.toString())
                }
            }
        )

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(title)
            .setSubtitle(subtitle)
            .setAllowedAuthenticators(AUTHENTICATORS)
            .setNegativeButtonText(negativeButtonText)
            .setConfirmationRequired(true)
            .build()

        biometricPrompt.authenticate(promptInfo)
    }
}