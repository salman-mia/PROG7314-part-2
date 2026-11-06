package com.example.campusconnect.notifications

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.campusconnect.MainActivity
import com.example.campusconnect.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class CampusFirebaseMessagingService :
    FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        /*
         * This token uniquely identifies this installation.
         * The group REST API can store it against the signed-in
         * user when the backend notification endpoint is ready.
         */
        Log.i(
            TAG,
            "New Firebase registration token: $token"
        )

        getSharedPreferences(
            TOKEN_PREFERENCES,
            MODE_PRIVATE
        )
            .edit()
            .putString(TOKEN_KEY, token)
            .apply()
    }

    override fun onMessageReceived(
        remoteMessage: RemoteMessage
    ) {
        super.onMessageReceived(remoteMessage)

        Log.i(
            TAG,
            "Firebase message received from: " +
                    "${remoteMessage.from}"
        )

        val notificationTitle =
            remoteMessage.notification?.title
                ?: remoteMessage.data["title"]
                ?: "CampusConnect Update"

        val notificationBody =
            remoteMessage.notification?.body
                ?: remoteMessage.data["body"]
                ?: "A new campus update is available."

        showNotification(
            title = notificationTitle,
            message = notificationBody
        )
    }

    private fun showNotification(
        title: String,
        message: String
    ) {
        createNotificationChannel()

        val openAppIntent = Intent(
            this,
            MainActivity::class.java
        ).apply {
            flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TOP
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            openAppIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or
                    PendingIntent.FLAG_IMMUTABLE
        )

        val notification =
            NotificationCompat.Builder(
                this,
                CHANNEL_ID
            )
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(message)
                )
                .setPriority(
                    NotificationCompat.PRIORITY_HIGH
                )
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build()

        if (
            Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.TIRAMISU &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.w(
                TAG,
                "Notification permission has not been granted."
            )

            return
        }

        NotificationManagerCompat
            .from(this)
            .notify(
                System.currentTimeMillis()
                    .toInt(),
                notification
            )
    }

    private fun createNotificationChannel() {
        if (
            Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.O
        ) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = CHANNEL_DESCRIPTION
                enableVibration(true)
            }

            val notificationManager =
                getSystemService(
                    NotificationManager::class.java
                )

            notificationManager.createNotificationChannel(
                channel
            )
        }
    }

    companion object {
        private const val TAG =
            "CampusFirebaseService"

        const val CHANNEL_ID =
            "campus_updates_channel"

        private const val CHANNEL_NAME =
            "Campus updates"

        private const val CHANNEL_DESCRIPTION =
            "Real-time campus announcements and alerts"

        const val TOKEN_PREFERENCES =
            "firebase_token_preferences"

        const val TOKEN_KEY =
            "firebase_registration_token"
    }
}