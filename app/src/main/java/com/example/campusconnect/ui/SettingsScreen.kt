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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/**
 * Allows a student to manage local application preferences.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit
) {
    var notificationsEnabled by rememberSaveable {
        mutableStateOf(true)
    }

    var eventRemindersEnabled by rememberSaveable {
        mutableStateOf(true)
    }

    var selectedLanguage by rememberSaveable {
        mutableStateOf("English")
    }

    var settingsSaved by rememberSaveable {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Notifications",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement =
                            Arrangement.spacedBy(16.dp)
                    ) {
                        SettingSwitchRow(
                            title = "App notifications",
                            description = "Receive general campus updates",
                            checked = notificationsEnabled,
                            onCheckedChange = {
                                notificationsEnabled = it
                                settingsSaved = false

                                Log.i(
                                    "CampusSettings",
                                    "App notifications changed: $it"
                                )
                            }
                        )

                        SettingSwitchRow(
                            title = "Event reminders",
                            description = "Receive reminders for upcoming events",
                            checked = eventRemindersEnabled,
                            onCheckedChange = {
                                eventRemindersEnabled = it
                                settingsSaved = false

                                Log.i(
                                    "CampusSettings",
                                    "Event reminders changed: $it"
                                )
                            }
                        )
                    }
                }
            }

            item {
                Text(
                    text = "Language",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp)
                    ) {
                        Text(
                            text = "Selected language: $selectedLanguage",
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        LanguageButton(
                            language = "English",
                            selectedLanguage = selectedLanguage,
                            onSelected = {
                                selectedLanguage = it
                                settingsSaved = false
                            }
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        LanguageButton(
                            language = "isiZulu",
                            selectedLanguage = selectedLanguage,
                            onSelected = {
                                selectedLanguage = it
                                settingsSaved = false
                            }
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        LanguageButton(
                            language = "Afrikaans",
                            selectedLanguage = selectedLanguage,
                            onSelected = {
                                selectedLanguage = it
                                settingsSaved = false
                            }
                        )
                    }
                }
            }

            item {
                Button(
                    onClick = {
                        settingsSaved = true

                        Log.i(
                            "CampusSettings",
                            "Settings saved: notifications=" +
                                    "$notificationsEnabled, eventReminders=" +
                                    "$eventRemindersEnabled, language=" +
                                    selectedLanguage
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save Settings")
                }
            }

            if (settingsSaved) {
                item {
                    Text(
                        text = "Settings saved successfully.",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun SettingSwitchRow(
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

@Composable
private fun LanguageButton(
    language: String,
    selectedLanguage: String,
    onSelected: (String) -> Unit
) {
    if (language == selectedLanguage) {
        Button(
            onClick = {
                Log.i(
                    "CampusSettings",
                    "Language selected: $language"
                )
                onSelected(language)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(language)
        }
    } else {
        OutlinedButton(
            onClick = {
                Log.i(
                    "CampusSettings",
                    "Language selected: $language"
                )
                onSelected(language)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(language)
        }
    }
}