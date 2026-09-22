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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.campusconnect.data.CampusRepository
import com.example.campusconnect.model.CampusEvent
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Displays campus events loaded from the CampusConnect REST API.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventsScreen(
    onBack: () -> Unit
) {

    var searchText by rememberSaveable {
        mutableStateOf("")
    }

    var isLoading by remember {
        mutableStateOf(true)
    }

    var errorMessage by remember {
        mutableStateOf<String?>(null)
    }

    val events = remember {
        mutableStateListOf<CampusEvent>()
    }

    val registeredEventIds = remember {
        mutableStateListOf<Int>()
    }

    val repository = remember {
        CampusRepository()
    }

    val coroutineScope = rememberCoroutineScope()

    // Load events from the ASP.NET API when this screen opens.
    LaunchedEffect(Unit) {

        isLoading = true
        errorMessage = null

        repository.getEventsFromApi()
            .onSuccess { apiEvents ->

                events.clear()
                events.addAll(apiEvents)

                Log.i(
                    "CampusEvents",
                    "Loaded ${apiEvents.size} events from backend"
                )

                isLoading = false
            }
            .onFailure { exception ->

                Log.e(
                    "CampusEvents",
                    "Failed to load events from backend",
                    exception
                )

                errorMessage =
                    "Unable to load campus events: ${exception.message}"

                isLoading = false
            }
    }

    val filteredEvents = events.filter { event ->

        event.title.contains(
            searchText,
            ignoreCase = true
        ) ||
                event.location.contains(
                    searchText,
                    ignoreCase = true
                )
    }

    Scaffold(
        topBar = {

            TopAppBar(
                title = {
                    Text("Campus Events")
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
                        Text("Back")
                    }
                }
            )
        }
    ) { innerPadding ->

        when {

            // -------------------------------------------------
            // LOADING
            // -------------------------------------------------

            isLoading -> {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    horizontalAlignment =
                        Alignment.CenterHorizontally,
                    verticalArrangement =
                        Arrangement.Center
                ) {

                    CircularProgressIndicator()

                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )

                    Text("Loading campus events...")
                }
            }

            // -------------------------------------------------
            // ERROR
            // -------------------------------------------------

            errorMessage != null -> {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(24.dp),
                    horizontalAlignment =
                        Alignment.CenterHorizontally,
                    verticalArrangement =
                        Arrangement.Center
                ) {

                    Text(
                        text = errorMessage ?: "",
                        color =
                            MaterialTheme.colorScheme.error
                    )
                }
            }

            // -------------------------------------------------
            // EVENTS
            // -------------------------------------------------

            else -> {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentPadding =
                        PaddingValues(16.dp),
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
                                Text("Search events")
                            },
                            singleLine = true,
                            modifier =
                                Modifier.fillMaxWidth()
                        )

                        Spacer(
                            modifier =
                                Modifier.height(8.dp)
                        )
                    }

                    if (filteredEvents.isEmpty()) {

                        item {

                            Text(
                                text =
                                    "No events match your search.",
                                style =
                                    MaterialTheme.typography.bodyLarge
                            )
                        }
                    }

                    items(
                        items = filteredEvents,
                        key = { event ->
                            event.id
                        }
                    ) { event ->

                        val isRegistered =
                            registeredEventIds.contains(
                                event.id
                            )

                        EventCard(
                            event = event,
                            isRegistered = isRegistered,
                            onRsvpClick = {

                                coroutineScope.launch {

                                    if (isRegistered) {

                                        // Cancel RSVP in backend.
                                        repository
                                            .cancelEventRsvp(
                                                event.id
                                            )
                                            .onSuccess {

                                                registeredEventIds
                                                    .remove(
                                                        event.id
                                                    )

                                                Log.i(
                                                    "CampusEvents",
                                                    "RSVP cancelled for ${event.title}"
                                                )
                                            }
                                            .onFailure { exception ->

                                                Log.e(
                                                    "CampusEvents",
                                                    "Failed to cancel RSVP",
                                                    exception
                                                )

                                                errorMessage =
                                                    "Unable to cancel RSVP: ${exception.message}"
                                            }

                                    } else {

                                        // Create RSVP in backend.
                                        repository
                                            .createEventRsvp(
                                                event.id
                                            )
                                            .onSuccess {

                                                if (
                                                    !registeredEventIds
                                                        .contains(
                                                            event.id
                                                        )
                                                ) {
                                                    registeredEventIds
                                                        .add(
                                                            event.id
                                                        )
                                                }

                                                Log.i(
                                                    "CampusEvents",
                                                    "RSVP created for ${event.title}"
                                                )
                                            }
                                            .onFailure { exception ->

                                                Log.e(
                                                    "CampusEvents",
                                                    "Failed to create RSVP",
                                                    exception
                                                )

                                                errorMessage =
                                                    "Unable to RSVP: ${exception.message}"
                                            }
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

/**
 * Displays one event returned by the backend.
 */
@Composable
private fun EventCard(
    event: CampusEvent,
    isRegistered: Boolean,
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
                fontWeight =
                    FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text =
                    formatEventDate(event.eventDate),
                color =
                    MaterialTheme.colorScheme.primary,
                fontWeight =
                    FontWeight.SemiBold
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
                style =
                    MaterialTheme.typography.bodyLarge
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text =
                    "RSVPs: ${event.rsvpCount}",
                style =
                    MaterialTheme.typography.bodySmall
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Row(
                modifier =
                    Modifier.fillMaxWidth(),
                horizontalArrangement =
                    Arrangement.End
            ) {

                Button(
                    onClick = onRsvpClick
                ) {

                    Text(
                        if (isRegistered) {
                            "Cancel RSVP"
                        } else {
                            "RSVP"
                        }
                    )
                }
            }
        }
    }
}

/**
 * Converts the API date:
 * 2026-09-25T10:00:00
 *
 * Into:
 * 25 September 2026 · 10:00
 */
private fun formatEventDate(
    eventDate: String
): String {

    return try {

        val parsedDate =
            LocalDateTime.parse(eventDate)

        val formatter =
            DateTimeFormatter.ofPattern(
                "dd MMMM yyyy · HH:mm"
            )

        parsedDate.format(formatter)

    } catch (exception: Exception) {

        // If the backend ever sends an unexpected date
        // format, display the original value instead.
        eventDate
    }
}