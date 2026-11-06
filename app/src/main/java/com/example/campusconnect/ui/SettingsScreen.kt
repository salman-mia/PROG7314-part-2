package com.example.campusconnect.ui

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.campusconnect.LocaleHelper
import com.example.campusconnect.R

/**
 * Allows a student to manage local application preferences.
 *
 * English and isZulu are supported using Android string resources.
 * The selected language is saved and remains active after the
 * application is restarted.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit
) {
    val context = LocalContext.current

    var notificationsEnabled by rememberSaveable {
        mutableStateOf(true)
    }

    var eventRemindersEnabled by rememberSaveable {
        mutableStateOf(true)
    }

    var selectedLanguageCode by rememberSaveable {
        mutableStateOf(
            LocaleHelper.getSavedLanguage(context)
        )
    }

    var settingsSaved by rememberSaveable {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(R.string.settings)
                    )
                },
                navigationIcon = {
                    TextButton(onClick = onBack) {
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = stringResource(
                        R.string.notifications
                    ),
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
                            title = stringResource(
                                R.string.app_notifications
                            ),
                            description = stringResource(
                                R.string.app_notifications_description
                            ),
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
                            title = stringResource(
                                R.string.event_reminders
                            ),
                            description = stringResource(
                                R.string.event_reminders_description
                            ),
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
                    text = stringResource(
                        R.string.language
                    ),
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
                        val selectedLanguageName =
                            if (selectedLanguageCode == "zu") {
                                stringResource(
                                    R.string.isizulu
                                )
                            } else {
                                stringResource(
                                    R.string.english
                                )
                            }

                        Text(
                            text = stringResource(
                                R.string.selected_language,
                                selectedLanguageName
                            ),
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(
                            modifier = Modifier.height(14.dp)
                        )

                        LanguageButton(
                            label = stringResource(
                                R.string.english
                            ),
                            languageCode = "en",
                            selectedLanguageCode =
                                selectedLanguageCode,
                            onSelected = {
                                selectedLanguageCode = it
                                settingsSaved = false
                            }
                        )

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        LanguageButton(
                            label = stringResource(
                                R.string.isizulu
                            ),
                            languageCode = "zu",
                            selectedLanguageCode =
                                selectedLanguageCode,
                            onSelected = {
                                selectedLanguageCode = it
                                settingsSaved = false
                            }
                        )
                    }
                }
            }

            item {
                Button(
                    onClick = {
                        LocaleHelper.saveLanguage(
                            context = context,
                            languageCode =
                                selectedLanguageCode
                        )

                        settingsSaved = true

                        Log.i(
                            "CampusSettings",
                            "Settings saved: notifications=" +
                                    "$notificationsEnabled, " +
                                    "eventReminders=" +
                                    "$eventRemindersEnabled, " +
                                    "language=" +
                                    selectedLanguageCode
                        )

                        /*
                         * Recreates the activity so Android loads the
                         * correct strings.xml resource immediately.
                         */
                        context.findActivity()?.recreate()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        stringResource(
                            R.string.save_settings
                        )
                    )
                }
            }

            if (settingsSaved) {
                item {
                    Text(
                        text = stringResource(
                            R.string.settings_saved
                        ),
                        color =
                            MaterialTheme.colorScheme.primary,
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
        horizontalArrangement =
            Arrangement.SpaceBetween
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
                color =
                    MaterialTheme.colorScheme.onSurfaceVariant
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
    label: String,
    languageCode: String,
    selectedLanguageCode: String,
    onSelected: (String) -> Unit
) {
    val isSelected =
        languageCode == selectedLanguageCode

    if (isSelected) {
        Button(
            onClick = {
                Log.i(
                    "CampusSettings",
                    "Language selected: $languageCode"
                )

                onSelected(languageCode)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(label)
        }
    } else {
        OutlinedButton(
            onClick = {
                Log.i(
                    "CampusSettings",
                    "Language selected: $languageCode"
                )

                onSelected(languageCode)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(label)
        }
    }
}

/**
 * Finds the activity even when Compose provides a wrapped context.
 */
private tailrec fun Context.findActivity(): Activity? {
    return when (this) {
        is Activity -> this
        is ContextWrapper -> baseContext.findActivity()
        else -> null
    }
}