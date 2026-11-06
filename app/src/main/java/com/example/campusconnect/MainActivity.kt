package com.example.campusconnect

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.campusconnect.notifications.CampusFirebaseMessagingService
import com.example.campusconnect.ui.CampusConnectApp
import com.example.campusconnect.ui.theme.CampusConnectTheme
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : FragmentActivity() {

    private val notificationPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { permissionGranted ->
            if (permissionGranted) {
                Log.i(
                    "CampusNotifications",
                    "Notification permission granted"
                )
            } else {
                Log.w(
                    "CampusNotifications",
                    "Notification permission denied"
                )
            }
        }

    override fun attachBaseContext(newBase: Context) {
        val localizedContext =
            LocaleHelper.wrapContext(newBase)

        super.attachBaseContext(localizedContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        requestNotificationPermission()
        registerForFirebaseNotifications()

        setContent {
            CampusConnectTheme {
                CampusConnectApp()
            }
        }
    }

    private fun requestNotificationPermission() {
        if (
            Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.TIRAMISU
        ) {
            val permissionStatus =
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                )

            if (
                permissionStatus !=
                PackageManager.PERMISSION_GRANTED
            ) {
                notificationPermissionLauncher.launch(
                    Manifest.permission.POST_NOTIFICATIONS
                )
            } else {
                Log.i(
                    "CampusNotifications",
                    "Notification permission already granted"
                )
            }
        }
    }

    private fun registerForFirebaseNotifications() {
        FirebaseMessaging
            .getInstance()
            .token
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.e(
                        "CampusNotifications",
                        "Could not retrieve Firebase token",
                        task.exception
                    )

                    return@addOnCompleteListener
                }

                val token = task.result

                getSharedPreferences(
                    CampusFirebaseMessagingService
                        .TOKEN_PREFERENCES,
                    MODE_PRIVATE
                )
                    .edit()
                    .putString(
                        CampusFirebaseMessagingService
                            .TOKEN_KEY,
                        token
                    )
                    .apply()

                Log.i(
                    "CampusNotifications",
                    "Firebase registration token: $token"
                )
            }

        FirebaseMessaging
            .getInstance()
            .subscribeToTopic(TOPIC_CAMPUS_UPDATES)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.i(
                        "CampusNotifications",
                        "Subscribed to campus updates topic"
                    )
                } else {
                    Log.e(
                        "CampusNotifications",
                        "Campus topic subscription failed",
                        task.exception
                    )
                }
            }
    }

    companion object {
        private const val TOPIC_CAMPUS_UPDATES =
            "campus_updates"
    }
}