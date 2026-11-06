package com.example.campusconnect.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.campusconnect.R

/**
 * Stores the translated label displayed to the user and the fixed
 * navigation key used internally by CampusConnectApp.
 */
private data class DashboardFeature(
    val routeKey: String,
    val displayLabel: String
)

/**
 * Main dashboard displayed after the user signs in.
 *
 * All visible wording supports English and isiZulu. The route keys
 * remain in English so that changing language does not break
 * application navigation.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onFeatureSelected: (String) -> Unit,
    onSignOut: () -> Unit
) {
    val features = listOf(
        DashboardFeature(
            routeKey = "Campus Events",
            displayLabel = stringResource(
                R.string.campus_events
            )
        ),
        DashboardFeature(
            routeKey = "Societies",
            displayLabel = stringResource(
                R.string.societies
            )
        ),
        DashboardFeature(
            routeKey = "Lost and Found",
            displayLabel = stringResource(
                R.string.lost_and_found
            )
        ),
        DashboardFeature(
            routeKey = "Announcements",
            displayLabel = stringResource(
                R.string.announcements
            )
        ),
        DashboardFeature(
            routeKey = "Academic Resources",
            displayLabel = stringResource(
                R.string.academic_resources
            )
        ),
        DashboardFeature(
            routeKey = "Campus Services",
            displayLabel = stringResource(
                R.string.campus_services
            )
        ),
        DashboardFeature(
            routeKey = "Leaderboard",
            displayLabel = stringResource(
                R.string.leaderboard
            )
        ),
        DashboardFeature(
            routeKey = "Settings",
            displayLabel = stringResource(
                R.string.settings
            )
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(
                            R.string.app_name
                        ),
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement =
                Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = stringResource(
                        R.string.welcome_student
                    ),
                    style =
                        MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = stringResource(
                        R.string.dashboard_description
                    ),
                    style = MaterialTheme.typography.bodyLarge,
                    color =
                        MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor =
                            MaterialTheme.colorScheme
                                .primaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp)
                    ) {
                        Text(
                            text = stringResource(
                                R.string.latest_announcement
                            ),
                            style =
                                MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(6.dp)
                        )

                        Text(
                            text = stringResource(
                                R.string.welcome_announcement
                            )
                        )
                    }
                }
            }

            item {
                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = stringResource(
                        R.string.available_features
                    ),
                    style =
                        MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            items(
                items = features,
                key = { feature -> feature.routeKey }
            ) { feature ->
                FeatureCard(
                    title = feature.displayLabel,
                    onClick = {
                        Log.i(
                            "CampusNavigation",
                            "Dashboard feature selected: " +
                                    feature.routeKey
                        )

                        onFeatureSelected(
                            feature.routeKey
                        )
                    }
                )
            }

            item {
                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Button(
                    onClick = {
                        Log.i(
                            "CampusLogin",
                            "User selected sign out"
                        )

                        onSignOut()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        stringResource(R.string.sign_out)
                    )
                }
            }
        }
    }
}

@Composable
private fun FeatureCard(
    title: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = title,
                style =
                    MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}