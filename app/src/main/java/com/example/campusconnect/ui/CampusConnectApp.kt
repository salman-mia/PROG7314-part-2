package com.example.campusconnect.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Root composable that controls authentication and screen navigation.
 */
@Composable
fun CampusConnectApp() {
    var isSignedIn by rememberSaveable { mutableStateOf(false) }
    var selectedFeature by rememberSaveable {
        mutableStateOf<String?>(null)
    }

    when {
        !isSignedIn -> {
            LoginScreen(
                onSignIn = {
                    Log.i(
                        "CampusState",
                        "Application state changed: signed out to signed in"
                    )
                    isSignedIn = true
                }
            )
        }

        selectedFeature == "Campus Events" -> {
            EventsScreen(
                onBack = {
                    Log.i(
                        "CampusNavigation",
                        "Returning from Campus Events to dashboard"
                    )
                    selectedFeature = null
                }
            )
        }

        selectedFeature == "Societies" -> {
            SocietiesScreen(
                onBack = {
                    Log.i(
                        "CampusNavigation",
                        "Returning from Societies to dashboard"
                    )
                    selectedFeature = null
                }
            )
        }

        selectedFeature == "Lost and Found" -> {
            LostFoundScreen(
                onBack = {
                    Log.i(
                        "CampusNavigation",
                        "Returning from Lost and Found to dashboard"
                    )
                    selectedFeature = null
                }
            )
        }

        selectedFeature == "Announcements" -> {
            AnnouncementsScreen(
                onBack = {
                    Log.i(
                        "CampusNavigation",
                        "Returning from Announcements to dashboard"
                    )
                    selectedFeature = null
                }
            )
        }

        selectedFeature == "Academic Resources" -> {
            AcademicResourcesScreen(
                onBack = {
                    Log.i(
                        "CampusNavigation",
                        "Returning from Academic Resources to dashboard"
                    )
                    selectedFeature = null
                }
            )
        }

        selectedFeature == "Campus Services" -> {
            CampusServicesScreen(
                onBack = {
                    Log.i(
                        "CampusNavigation",
                        "Returning from Campus Services to dashboard"
                    )
                    selectedFeature = null
                }
            )
        }

        selectedFeature == "Leaderboard" -> {
            LeaderboardScreen(
                onBack = {
                    Log.i(
                        "CampusNavigation",
                        "Returning from Leaderboard to dashboard"
                    )
                    selectedFeature = null
                }
            )
        }

        selectedFeature == "Settings" -> {
            SettingsScreen(
                onBack = {
                    Log.i(
                        "CampusNavigation",
                        "Returning from Settings to dashboard"
                    )
                    selectedFeature = null
                }
            )
        }

        selectedFeature != null -> {
            FeatureScreen(
                featureName = selectedFeature ?: "",
                onBack = {
                    Log.i(
                        "CampusNavigation",
                        "Returning from $selectedFeature to dashboard"
                    )
                    selectedFeature = null
                }
            )
        }

        else -> {
            HomeScreen(
                onFeatureSelected = { feature ->
                    Log.i(
                        "CampusNavigation",
                        "Navigating from dashboard to $feature"
                    )
                    selectedFeature = feature
                },
                onSignOut = {
                    Log.i(
                        "CampusState",
                        "Application state changed: signed in to signed out"
                    )
                    selectedFeature = null
                    isSignedIn = false
                }
            )
        }
    }
}

/**
 * Temporary page for features that have not yet received their
 * individual Android screen.
 */
@Composable
private fun FeatureScreen(
    featureName: String,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = featureName,
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "This feature is ready to be connected to the backend.",
            modifier = Modifier.padding(
                top = 12.dp,
                bottom = 24.dp
            )
        )

        Button(onClick = onBack) {
            Text("Back to Dashboard")
        }
    }
}