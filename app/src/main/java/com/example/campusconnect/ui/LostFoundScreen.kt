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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.campusconnect.R
import com.example.campusconnect.data.LostFoundDatabaseHelper
import com.example.campusconnect.data.OfflineSyncScheduler
import com.example.campusconnect.model.OfflineLostFoundItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

private const val LOST_TYPE = "LOST"
private const val FOUND_TYPE = "FOUND"

private data class LostFoundUiModel(
    val id: String,
    val title: String,
    val type: String,
    val location: String,
    val description: String,
    val syncStatus: String? = null
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LostFoundScreen(
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val applicationContext = context.applicationContext

    val database = remember {
        LostFoundDatabaseHelper(applicationContext)
    }

    DisposableEffect(database) {
        onDispose {
            database.close()
        }
    }

    val currentLanguage =
        LocalConfiguration.current.locales[0].language

    val isZulu = currentLanguage == "zu"

    val offlineReadyText =
        if (isZulu) {
            "Imodi engaxhunyiwe ku-inthanethi isilungile"
        } else {
            "Offline mode ready"
        }

    val offlineExplanationText =
        if (isZulu) {
            "Imibiko igcinwa kule divayisi bese ivunyelaniswa ngokuzenzakalelayo lapho i-inthanethi ibuya."
        } else {
            "Reports are stored on this device and synchronised automatically when internet returns."
        }

    val storedMessageText =
        if (isZulu) {
            "Umbiko ugcinwe kule divayisi futhi ulindele ukuvumelaniswa."
        } else {
            "Report saved on this device and queued for synchronisation."
        }

    val pendingText =
        if (isZulu) {
            "Kulindele ukuvumelaniswa"
        } else {
            "Pending synchronisation"
        }

    val syncedText =
        if (isZulu) {
            "Kuvunyelanisiwe"
        } else {
            "Synchronised"
        }

    var showForm by rememberSaveable {
        mutableStateOf(false)
    }

    var title by rememberSaveable {
        mutableStateOf("")
    }

    var location by rememberSaveable {
        mutableStateOf("")
    }

    var description by rememberSaveable {
        mutableStateOf("")
    }

    var itemType by rememberSaveable {
        mutableStateOf(LOST_TYPE)
    }

    var showError by rememberSaveable {
        mutableStateOf(false)
    }

    var saveMessage by rememberSaveable {
        mutableStateOf<String?>(null)
    }

    var offlineItems by remember {
        mutableStateOf(
            emptyList<OfflineLostFoundItem>()
        )
    }

    /*
     * Refreshes local records while this screen is open.
     * This allows PENDING labels to change to SYNCED
     * after WorkManager runs when internet returns.
     */
    LaunchedEffect(database) {
        while (true) {
            offlineItems = withContext(
                Dispatchers.IO
            ) {
                database.getAllItems()
            }

            delay(1000)
        }
    }

    val backpackTitle =
        stringResource(R.string.black_backpack)

    val backpackLocation =
        stringResource(R.string.main_library)

    val backpackDescription =
        stringResource(
            R.string.black_backpack_description
        )

    val accessCardTitle =
        stringResource(R.string.student_access_card)

    val accessCardLocation =
        stringResource(R.string.cafeteria)

    val accessCardDescription =
        stringResource(
            R.string.access_card_description
        )

    val bottleTitle =
        stringResource(R.string.silver_water_bottle)

    val bottleLocation =
        stringResource(R.string.sports_centre)

    val bottleDescription =
        stringResource(
            R.string.water_bottle_description
        )

    val sampleListings = remember(
        backpackTitle,
        backpackLocation,
        backpackDescription,
        accessCardTitle,
        accessCardLocation,
        accessCardDescription,
        bottleTitle,
        bottleLocation,
        bottleDescription
    ) {
        listOf(
            LostFoundUiModel(
                id = "sample_1",
                title = backpackTitle,
                type = LOST_TYPE,
                location = backpackLocation,
                description = backpackDescription
            ),
            LostFoundUiModel(
                id = "sample_2",
                title = accessCardTitle,
                type = FOUND_TYPE,
                location = accessCardLocation,
                description = accessCardDescription
            ),
            LostFoundUiModel(
                id = "sample_3",
                title = bottleTitle,
                type = FOUND_TYPE,
                location = bottleLocation,
                description = bottleDescription
            )
        )
    }

    val savedListings = offlineItems.map { item ->
        LostFoundUiModel(
            id = "offline_${item.id}",
            title = item.itemName,
            type = item.itemType,
            location = item.location,
            description = item.description,
            syncStatus = item.syncStatus
        )
    }

    val allListings =
        savedListings + sampleListings

    val pendingCount =
        offlineItems.count { item ->
            !item.isSynced
        }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(
                            R.string.lost_and_found
                        )
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
            verticalArrangement =
                Arrangement.spacedBy(12.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = offlineReadyText,
                            style =
                                MaterialTheme.typography
                                    .titleMedium,
                            fontWeight = FontWeight.Bold,
                            color =
                                MaterialTheme.colorScheme
                                    .primary
                        )

                        Spacer(
                            modifier = Modifier.height(6.dp)
                        )

                        Text(
                            text = offlineExplanationText,
                            style =
                                MaterialTheme.typography
                                    .bodyMedium
                        )

                        if (pendingCount > 0) {
                            Spacer(
                                modifier =
                                    Modifier.height(8.dp)
                            )

                            Text(
                                text =
                                    "$pendingCount $pendingText",
                                color =
                                    MaterialTheme.colorScheme
                                        .error,
                                fontWeight =
                                    FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            item {
                Button(
                    onClick = {
                        showForm = !showForm
                        showError = false
                        saveMessage = null

                        Log.d(
                            "CampusLostFound",
                            "Report form visible: $showForm"
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        if (showForm) {
                            stringResource(
                                R.string.close_report_form
                            )
                        } else {
                            stringResource(
                                R.string.report_item
                            )
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
                                text = stringResource(
                                    R.string.new_item_report
                                ),
                                style =
                                    MaterialTheme.typography
                                        .titleLarge,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(
                                modifier =
                                    Modifier.height(12.dp)
                            )

                            Row(
                                modifier =
                                    Modifier.fillMaxWidth(),
                                horizontalArrangement =
                                    Arrangement.spacedBy(8.dp)
                            ) {
                                ItemTypeButton(
                                    text = stringResource(
                                        R.string.lost
                                    ),
                                    selected =
                                        itemType == LOST_TYPE,
                                    onClick = {
                                        itemType = LOST_TYPE
                                    },
                                    modifier =
                                        Modifier.weight(1f)
                                )

                                ItemTypeButton(
                                    text = stringResource(
                                        R.string.found
                                    ),
                                    selected =
                                        itemType == FOUND_TYPE,
                                    onClick = {
                                        itemType = FOUND_TYPE
                                    },
                                    modifier =
                                        Modifier.weight(1f)
                                )
                            }

                            Spacer(
                                modifier =
                                    Modifier.height(12.dp)
                            )

                            OutlinedTextField(
                                value = title,
                                onValueChange = {
                                    title = it
                                    showError = false
                                    saveMessage = null
                                },
                                label = {
                                    Text(
                                        stringResource(
                                            R.string.item_name
                                        )
                                    )
                                },
                                singleLine = true,
                                modifier =
                                    Modifier.fillMaxWidth()
                            )

                            Spacer(
                                modifier =
                                    Modifier.height(10.dp)
                            )

                            OutlinedTextField(
                                value = location,
                                onValueChange = {
                                    location = it
                                    showError = false
                                    saveMessage = null
                                },
                                label = {
                                    Text(
                                        stringResource(
                                            R.string.location
                                        )
                                    )
                                },
                                singleLine = true,
                                modifier =
                                    Modifier.fillMaxWidth()
                            )

                            Spacer(
                                modifier =
                                    Modifier.height(10.dp)
                            )

                            OutlinedTextField(
                                value = description,
                                onValueChange = {
                                    description = it
                                    showError = false
                                    saveMessage = null
                                },
                                label = {
                                    Text(
                                        stringResource(
                                            R.string.description
                                        )
                                    )
                                },
                                modifier =
                                    Modifier.fillMaxWidth()
                            )

                            if (showError) {
                                Spacer(
                                    modifier =
                                        Modifier.height(8.dp)
                                )

                                Text(
                                    text = stringResource(
                                        R.string
                                            .complete_all_fields
                                    ),
                                    color =
                                        MaterialTheme
                                            .colorScheme.error
                                )
                            }

                            Spacer(
                                modifier =
                                    Modifier.height(14.dp)
                            )

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
                                            "Offline report rejected"
                                        )
                                    } else {
                                        val insertedId =
                                            database.insertItem(
                                                itemName =
                                                    title.trim(),
                                                description =
                                                    description
                                                        .trim(),
                                                itemType =
                                                    itemType,
                                                location =
                                                    location.trim(),
                                                contactDetails =
                                                    "CampusConnect user"
                                            )

                                        if (insertedId != -1L) {
                                            offlineItems =
                                                database
                                                    .getAllItems()

                                            OfflineSyncScheduler
                                                .scheduleLostFoundSync(
                                                    applicationContext
                                                )

                                            saveMessage =
                                                storedMessageText

                                            Log.i(
                                                "CampusLostFound",
                                                "Offline report saved " +
                                                        "with ID $insertedId"
                                            )

                                            title = ""
                                            location = ""
                                            description = ""
                                            itemType = LOST_TYPE
                                            showError = false
                                            showForm = false
                                        } else {
                                            showError = true

                                            Log.e(
                                                "CampusLostFound",
                                                "Local database insert failed"
                                            )
                                        }
                                    }
                                },
                                modifier =
                                    Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    stringResource(
                                        R.string.submit_report
                                    )
                                )
                            }
                        }
                    }
                }
            }

            saveMessage?.let { message ->
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = message,
                            color =
                                MaterialTheme.colorScheme
                                    .primary,
                            fontWeight =
                                FontWeight.SemiBold,
                            modifier =
                                Modifier.padding(16.dp)
                        )
                    }
                }
            }

            item {
                Text(
                    text = stringResource(
                        R.string.recent_listings
                    ),
                    style =
                        MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            items(
                items = allListings,
                key = { listing ->
                    listing.id
                }
            ) { listing ->
                LostFoundListingCard(
                    listing = listing,
                    pendingText = pendingText,
                    syncedText = syncedText
                )
            }
        }
    }
}

@Composable
private fun ItemTypeButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (selected) {
        Button(
            onClick = onClick,
            modifier = modifier
        ) {
            Text(text)
        }
    } else {
        OutlinedButton(
            onClick = onClick,
            modifier = modifier
        ) {
            Text(text)
        }
    }
}

@Composable
private fun LostFoundListingCard(
    listing: LostFoundUiModel,
    pendingText: String,
    syncedText: String
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Text(
                text = listing.title,
                style =
                    MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text =
                    if (listing.type == LOST_TYPE) {
                        stringResource(R.string.lost)
                    } else {
                        stringResource(R.string.found)
                    },
                color =
                    if (listing.type == LOST_TYPE) {
                        MaterialTheme.colorScheme.error
                    } else {
                        MaterialTheme.colorScheme.primary
                    },
                fontWeight = FontWeight.Bold
            )

            Text(
                stringResource(
                    R.string.location_value,
                    listing.location
                )
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(listing.description)

            listing.syncStatus?.let { status ->
                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                val isSynced =
                    status ==
                            OfflineLostFoundItem.SYNCED

                Text(
                    text =
                        if (isSynced) {
                            syncedText
                        } else {
                            pendingText
                        },
                    color =
                        if (isSynced) {
                            MaterialTheme.colorScheme
                                .primary
                        } else {
                            MaterialTheme.colorScheme
                                .error
                        },
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}