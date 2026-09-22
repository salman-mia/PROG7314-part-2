package com.example.campusconnect.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

private data class InformationUiModel(
    val id: Int,
    val title: String,
    val subtitle: String,
    val description: String,
    val actionLabel: String? = null
)

@Composable
fun AnnouncementsScreen(
    onBack: () -> Unit
) {
    val announcements = listOf(
        InformationUiModel(
            id = 1,
            title = "Library hours extended",
            subtitle = "21 September 2026",
            description = "The Main Library will remain open until 22:00 during the examination period."
        ),
        InformationUiModel(
            id = 2,
            title = "Campus network maintenance",
            subtitle = "24 September 2026",
            description = "Short interruptions may occur between 18:00 and 20:00 while maintenance is completed."
        ),
        InformationUiModel(
            id = 3,
            title = "Student funding applications",
            subtitle = "Closing date: 30 September 2026",
            description = "Students are reminded to submit outstanding funding documents before the closing date."
        )
    )

    InformationListScreen(
        screenTitle = "Announcements",
        logTag = "CampusAnnouncements",
        information = announcements,
        onBack = onBack
    )
}

@Composable
fun AcademicResourcesScreen(
    onBack: () -> Unit
) {
    val resources = listOf(
        InformationUiModel(
            id = 1,
            title = "Referencing Guide",
            subtitle = "Academic Writing",
            description = "A guide to citations, reference lists and avoiding plagiarism.",
            actionLabel = "Open Resource"
        ),
        InformationUiModel(
            id = 2,
            title = "Programming Support Notes",
            subtitle = "Application Development",
            description = "Revision notes covering Kotlin and object-oriented programming.",
            actionLabel = "Open Resource"
        ),
        InformationUiModel(
            id = 3,
            title = "Study Skills Toolkit",
            subtitle = "Student Support",
            description = "Time-management, examination and effective note-taking guidance.",
            actionLabel = "Open Resource"
        )
    )

    InformationListScreen(
        screenTitle = "Academic Resources",
        logTag = "CampusResources",
        information = resources,
        onBack = onBack
    )
}

@Composable
fun CampusServicesScreen(
    onBack: () -> Unit
) {
    val services = listOf(
        InformationUiModel(
            id = 1,
            title = "Campus Security",
            subtitle = "Available 24 hours",
            description = "Emergency assistance, safety escorts and lost-property support.",
            actionLabel = "View Details"
        ),
        InformationUiModel(
            id = 2,
            title = "Student Counselling",
            subtitle = "Monday–Friday, 08:00–16:30",
            description = "Confidential wellness and mental-health support for students.",
            actionLabel = "View Details"
        ),
        InformationUiModel(
            id = 3,
            title = "IT Help Desk",
            subtitle = "Technology Support",
            description = "Support for campus Wi-Fi, student accounts and learning systems.",
            actionLabel = "View Details"
        )
    )

    InformationListScreen(
        screenTitle = "Campus Services",
        logTag = "CampusServices",
        information = services,
        onBack = onBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InformationListScreen(
    screenTitle: String,
    logTag: String,
    information: List<InformationUiModel>,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(screenTitle) },
                navigationIcon = {
                    TextButton(
                        onClick = {
                            Log.i(
                                "CampusNavigation",
                                "Returning from $screenTitle to dashboard"
                            )
                            onBack()
                        }
                    ) {
                        Text("Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = information,
                key = { item -> item.id }
            ) { item ->
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp)
                    ) {
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = item.subtitle,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = item.description,
                            style = MaterialTheme.typography.bodyLarge
                        )

                        if (item.actionLabel != null) {
                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = {
                                    Log.i(
                                        logTag,
                                        "${item.actionLabel} selected: ${item.title}"
                                    )
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(item.actionLabel)
                            }
                        }
                    }
                }
            }
        }
    }
}