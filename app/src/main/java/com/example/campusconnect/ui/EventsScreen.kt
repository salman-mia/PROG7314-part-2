package com.example.campusconnect.ui

import android.util.Log
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.campusconnect.R

/**
 * Data displayed by one translated event card.
 */
private data class EventUiModel(
    val id: Int,
    val title: String,
    val date: String,
    val location: String,
    val description: String,
    val isAttending: Boolean = false
)

/**
 * Displays translated campus events and allows students to RSVP.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventsScreen(
    onBack: () -> Unit
) {
    var searchText by rememberSaveable {
        mutableStateOf("")
    }

    /*
     * Read the translated event information before creating the
     * remembered state list.
     */
    val welcomeTitle =
        stringResource(R.string.event_welcome_title)
    val welcomeDate =
        stringResource(R.string.event_welcome_date)
    val welcomeLocation =
        stringResource(R.string.event_welcome_location)
    val welcomeDescription =
        stringResource(R.string.event_welcome_description)

    val careersTitle =
        stringResource(R.string.event_careers_title)
    val careersDate =
        stringResource(R.string.event_careers_date)
    val careersLocation =
        stringResource(R.string.event_careers_location)
    val careersDescription =
        stringResource(R.string.event_careers_description)

    val sportsTitle =
        stringResource(R.string.event_sports_title)
    val sportsDate =
        stringResource(R.string.event_sports_date)
    val sportsLocation =
        stringResource(R.string.event_sports_location)
    val sportsDescription =
        stringResource(R.string.event_sports_description)

    val events = remember(
        welcomeTitle,
        careersTitle,
        sportsTitle
    ) {
        mutableStateListOf(
            EventUiModel(
                id = 1,
                title = welcomeTitle,
                date = welcomeDate,
                location = welcomeLocation,
                description = welcomeDescription
            ),
            EventUiModel(
                id = 2,
                title = careersTitle,
                date = careersDate,
                location = careersLocation,
                description = careersDescription
            ),
            EventUiModel(
                id = 3,
                title = sportsTitle,
                date = sportsDate,
                location = sportsLocation,
                description = sportsDescription
            )
        )
    }

    val filteredEvents = events.filter { event ->
        event.title.contains(
            searchText,
            ignoreCase = true
        ) || event.location.contains(
            searchText,
            ignoreCase = true
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(
                            R.string.campus_events
                        )
                    )
                },
                navigationIcon = {
                    TextButton(
                        onClick = {
                            Log.i(
                                "CampusNavigation",
                                "Returning from events to dashboard"
                            )

                            onBack()
                        }
                    ) {
                        Text(
                            stringResource(
                                R.string.back
                            )
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
            item {
                OutlinedTextField(
                    value = searchText,
                    onValueChange = {
                        searchText = it

                        Log.d(
                            "CampusEvents",
                            "Event search changed: $it"
                        )
                    },
                    label = {
                        Text(
                            stringResource(
                                R.string.search_events
                            )
                        )
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )
            }

            if (filteredEvents.isEmpty()) {
                item {
                    Text(
                        text = stringResource(
                            R.string.no_events_found
                        ),
                        style =
                            MaterialTheme.typography.bodyLarge
                    )
                }
            }

            items(
                items = filteredEvents,
                key = { event -> event.id }
            ) { event ->
                EventCard(
                    event = event,
                    onRsvpClick = {
                        val position =
                            events.indexOfFirst {
                                it.id == event.id
                            }

                        if (position >= 0) {
                            val updatedEvent =
                                event.copy(
                                    isAttending =
                                        !event.isAttending
                                )

                            events[position] =
                                updatedEvent

                            Log.i(
                                "CampusEvents",
                                "RSVP changed for " +
                                        "${event.title}: " +
                                        updatedEvent.isAttending
                            )
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun EventCard(
    event: EventUiModel,
    onRsvpClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Text(
                text = event.title,
                style =
                    MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = event.date,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = event.location,
                style =
                    MaterialTheme.typography.bodyMedium
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Text(
                text = event.description,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = onRsvpClick
                ) {
                    Text(
                        if (event.isAttending) {
                            stringResource(
                                R.string.cancel_rsvp
                            )
                        } else {
                            stringResource(
                                R.string.rsvp
                            )
                        }
                    )
                }
            }
        }
    }
}