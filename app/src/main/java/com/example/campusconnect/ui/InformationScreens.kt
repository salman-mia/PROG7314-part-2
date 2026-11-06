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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.campusconnect.R

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
            title = stringResource(
                R.string.library_hours_title
            ),
            subtitle = stringResource(
                R.string.library_hours_date
            ),
            description = stringResource(
                R.string.library_hours_description
            )
        ),
        InformationUiModel(
            id = 2,
            title = stringResource(
                R.string.network_maintenance_title
            ),
            subtitle = stringResource(
                R.string.network_maintenance_date
            ),
            description = stringResource(
                R.string.network_maintenance_description
            )
        ),
        InformationUiModel(
            id = 3,
            title = stringResource(
                R.string.funding_title
            ),
            subtitle = stringResource(
                R.string.funding_date
            ),
            description = stringResource(
                R.string.funding_description
            )
        )
    )

    InformationListScreen(
        screenTitle = stringResource(
            R.string.announcements
        ),
        logTag = "CampusAnnouncements",
        information = announcements,
        onBack = onBack
    )
}

@Composable
fun AcademicResourcesScreen(
    onBack: () -> Unit
) {
    val actionLabel = stringResource(
        R.string.open_resource
    )

    val resources = listOf(
        InformationUiModel(
            id = 1,
            title = stringResource(
                R.string.referencing_guide_title
            ),
            subtitle = stringResource(
                R.string.academic_writing
            ),
            description = stringResource(
                R.string.referencing_guide_description
            ),
            actionLabel = actionLabel
        ),
        InformationUiModel(
            id = 2,
            title = stringResource(
                R.string.programming_notes_title
            ),
            subtitle = stringResource(
                R.string.application_development
            ),
            description = stringResource(
                R.string.programming_notes_description
            ),
            actionLabel = actionLabel
        ),
        InformationUiModel(
            id = 3,
            title = stringResource(
                R.string.study_skills_title
            ),
            subtitle = stringResource(
                R.string.student_support
            ),
            description = stringResource(
                R.string.study_skills_description
            ),
            actionLabel = actionLabel
        )
    )

    InformationListScreen(
        screenTitle = stringResource(
            R.string.academic_resources
        ),
        logTag = "CampusResources",
        information = resources,
        onBack = onBack
    )
}

@Composable
fun CampusServicesScreen(
    onBack: () -> Unit
) {
    val actionLabel = stringResource(
        R.string.view_details
    )

    val services = listOf(
        InformationUiModel(
            id = 1,
            title = stringResource(
                R.string.campus_security_title
            ),
            subtitle = stringResource(
                R.string.available_24_hours
            ),
            description = stringResource(
                R.string.campus_security_description
            ),
            actionLabel = actionLabel
        ),
        InformationUiModel(
            id = 2,
            title = stringResource(
                R.string.student_counselling_title
            ),
            subtitle = stringResource(
                R.string.counselling_hours
            ),
            description = stringResource(
                R.string.student_counselling_description
            ),
            actionLabel = actionLabel
        ),
        InformationUiModel(
            id = 3,
            title = stringResource(
                R.string.it_help_desk_title
            ),
            subtitle = stringResource(
                R.string.technology_support
            ),
            description = stringResource(
                R.string.it_help_desk_description
            ),
            actionLabel = actionLabel
        )
    )

    InformationListScreen(
        screenTitle = stringResource(
            R.string.campus_services
        ),
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
                title = {
                    Text(screenTitle)
                },
                navigationIcon = {
                    TextButton(
                        onClick = {
                            Log.i(
                                "CampusNavigation",
                                "Returning from information screen"
                            )

                            onBack()
                        }
                    ) {
                        Text(
                            stringResource(R.string.back)
                        )
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
            verticalArrangement =
                Arrangement.spacedBy(12.dp)
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
                            style =
                                MaterialTheme.typography
                                    .titleLarge,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(4.dp)
                        )

                        Text(
                            text = item.subtitle,
                            color =
                                MaterialTheme.colorScheme
                                    .primary,
                            fontWeight =
                                FontWeight.SemiBold
                        )

                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )

                        Text(
                            text = item.description,
                            style =
                                MaterialTheme.typography
                                    .bodyLarge
                        )

                        item.actionLabel?.let { label ->
                            Spacer(
                                modifier =
                                    Modifier.height(16.dp)
                            )

                            Button(
                                onClick = {
                                    Log.i(
                                        logTag,
                                        "$label selected: " +
                                                item.title
                                    )
                                },
                                modifier =
                                    Modifier.fillMaxWidth()
                            ) {
                                Text(label)
                            }
                        }
                    }
                }
            }
        }
    }
}