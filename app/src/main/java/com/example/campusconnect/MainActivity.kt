package com.example.campusconnect

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.campusconnect.network.ApiClient
import com.example.campusconnect.ui.CampusConnectApp
import com.example.campusconnect.ui.theme.CampusConnectTheme

/**
 * Main entry point for the CampusConnect Android application.
 *
 * Lifecycle logging is included to demonstrate application state
 * transitions for the Part 2 technical requirements.
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i(
            LOG_TAG,
            "onCreate: CampusConnect activity created"
        )

        // Initialise the API client so it can access the saved JWT token.
        ApiClient.initialize(applicationContext)

        enableEdgeToEdge()

        setContent {
            CampusConnectTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CampusConnectApp()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.i(
            LOG_TAG,
            "onStart: CampusConnect is visible"
        )
    }

    override fun onResume() {
        super.onResume()
        Log.i(
            LOG_TAG,
            "onResume: CampusConnect is active"
        )
    }

    override fun onPause() {
        Log.i(
            LOG_TAG,
            "onPause: CampusConnect is leaving the foreground"
        )
        super.onPause()
    }

    override fun onStop() {
        Log.i(
            LOG_TAG,
            "onStop: CampusConnect is no longer visible"
        )
        super.onStop()
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(
            LOG_TAG,
            "onRestart: CampusConnect is restarting"
        )
    }

    override fun onDestroy() {
        Log.i(
            LOG_TAG,
            "onDestroy: CampusConnect activity destroyed"
        )
        super.onDestroy()
    }

    companion object {
        private const val LOG_TAG = "CampusLifecycle"
    }
}