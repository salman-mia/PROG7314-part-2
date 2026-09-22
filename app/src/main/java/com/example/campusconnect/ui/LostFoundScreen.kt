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
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

private data class LostFoundUiModel(
    val id: Int,
    val title: String,
    val type: String,
    val location: String,
    val description: String
)

/**
 * Displays lost-and-found listings and allows a student to report an item.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LostFoundScreen(
    onBack: () -> Unit
) {
    var showForm by rememberSaveable { mutableStateOf(false) }
    var title by rememberSaveable { mutableStateOf("") }
    var location by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var itemType by rememberSaveable { mutableStateOf("Lost") }
    var showError by rememberSaveable { mutableStateOf(false) }

    val listings = remember {
        mutableStateListOf(
            LostFoundUiModel(
                id = 1,
                title = "Black backpack",
                type = "Lost",
                location = "Main Library",
                description = "Black backpack with a blue keyring."
            ),
            LostFoundUiModel(
                id = 2,
                title = "Student access card",
                type = "Found",
                location = "Cafeteria",
                description = "Access card handed to campus security."
            ),
            LostFoundUiModel(
                id = 3,
                title = "Silver water bottle",
                type = "Found",
                location = "Sports Centre",
                description = "Silver bottle found near the indoor courts."
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lost and Found") },
                navigationIcon = {
                    TextButton(onClick = onBack) {
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
            item {
                Button(
                    onClick = {
                        showForm = !showForm
                        showError = false

                        Log.d(
                            "CampusLostFound",
                            "Report form visible: $showForm"
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        if (showForm) {
                            "Close Report Form"
                        } else {
                            "Report an Item"
                        }
                    )
                }
            }

            if (showForm) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(18.dp)
                        ) {
                            Text(
                                text = "New item report",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                if (itemType == "Lost") {
                                    Button(
                                        onClick = { itemType = "Lost" },
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text("Lost")
                                    }
                                } else {
                                    OutlinedButton(
                                        onClick = { itemType = "Lost" },
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text("Lost")
                                    }
                                }

                                if (itemType == "Found") {
                                    Button(
                                        onClick = { itemType = "Found" },
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text("Found")
                                    }
                                } else {
                                    OutlinedButton(
                                        onClick = { itemType = "Found" },
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text("Found")
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            OutlinedTextField(
                                value = title,
                                onValueChange = {
                                    title = it
                                    showError = false
                                },
                                label = { Text("Item name") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            OutlinedTextField(
                                value = location,
                                onValueChange = {
                                    location = it
                                    showError = false
                                },
                                label = { Text("Location") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            OutlinedTextField(
                                value = description,
                                onValueChange = {
                                    description = it
                                    showError = false
                                },
                                label = { Text("Description") },
                                modifier = Modifier.fillMaxWidth()
                            )

                            if (showError) {
                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = "Please complete all fields.",
                                    color = MaterialTheme.colorScheme.error
                                )
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            Button(
                                onClick = {
                                    if (
                                        title.isBlank() ||
                                        location.isBlank() ||
                                        description.isBlank()
                                    ) {
                                        showError = true

                                        Log.w(
                                            "CampusLostFound",
                                            "Item report rejected: incomplete fields"
                                        )
                                    } else {
                                        listings.add(
                                            index = 0,
                                            element = LostFoundUiModel(
                                                id = (listings.maxOfOrNull {
                                                    it.id
                                                } ?: 0) + 1,
                                                title = title,
                                                type = itemType,
                                                location = location,
                                                description = description
                                            )
                                        )

                                        Log.i(
                                            "CampusLostFound",
                                            "$itemType item report created: $title"
                                        )

                                        title = ""
                                        location = ""
                                        description = ""
                                        itemType = "Lost"
                                        showError = false
                                        showForm = false
                                    }
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Submit Report")
                            }
                        }
                    }
                }
            }

            item {
                Text(
                    text = "Recent listings",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            items(
                items = listings,
                key = { listing -> listing.id }
            ) { listing ->
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp)
                    ) {
                        Text(
                            text = listing.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = listing.type,
                            color = if (listing.type == "Lost") {
                                MaterialTheme.colorScheme.error
                            } else {
                                MaterialTheme.colorScheme.primary
                            },
                            fontWeight = FontWeight.Bold
                        )

                        Text("Location: ${listing.location}")

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(listing.description)
                    }
                }
            }
        }
    }
}