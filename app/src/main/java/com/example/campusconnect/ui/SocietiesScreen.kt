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
 * Data displayed by a translated society card.
 */
private data class SocietyUiModel(
    val id: Int,
    val name: String,
    val category: String,
    val description: String,
    val isMember: Boolean = false
)

/**
 * Displays translated campus societies and membership controls.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SocietiesScreen(
    onBack: () -> Unit
) {
    var searchText by rememberSaveable {
        mutableStateOf("")
    }

    val technologyName = stringResource(
        R.string.society_technology_name
    )
    val technologyCategory = stringResource(
        R.string.society_technology_category
    )
    val technologyDescription = stringResource(
        R.string.society_technology_description
    )

    val sportsName = stringResource(
        R.string.society_sports_name
    )
    val sportsCategory = stringResource(
        R.string.society_sports_category
    )
    val sportsDescription = stringResource(
        R.string.society_sports_description
    )

    val artsName = stringResource(
        R.string.society_arts_name
    )
    val artsCategory = stringResource(
        R.string.society_arts_category
    )
    val artsDescription = stringResource(
        R.string.society_arts_description
    )

    val communityName = stringResource(
        R.string.society_community_name
    )
    val communityCategory = stringResource(
        R.string.society_community_category
    )
    val communityDescription = stringResource(
        R.string.society_community_description
    )

    val societies = remember(
        technologyName,
        sportsName,
        artsName,
        communityName
    ) {
        mutableStateListOf(
            SocietyUiModel(
                id = 1,
                name = technologyName,
                category = technologyCategory,
                description = technologyDescription
            ),
            SocietyUiModel(
                id = 2,
                name = sportsName,
                category = sportsCategory,
                description = sportsDescription
            ),
            SocietyUiModel(
                id = 3,
                name = artsName,
                category = artsCategory,
                description = artsDescription
            ),
            SocietyUiModel(
                id = 4,
                name = communityName,
                category = communityCategory,
                description = communityDescription
            )
        )
    }

    val filteredSocieties = societies.filter { society ->
        society.name.contains(
            searchText,
            ignoreCase = true
        ) || society.category.contains(
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
                            R.string.societies
                        )
                    )
                },
                navigationIcon = {
                    TextButton(
                        onClick = onBack
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
                            "CampusSocieties",
                            "Society search changed: $it"
                        )
                    },
                    label = {
                        Text(
                            stringResource(
                                R.string.search_societies
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

            if (filteredSocieties.isEmpty()) {
                item {
                    Text(
                        stringResource(
                            R.string.no_societies_found
                        )
                    )
                }
            }

            items(
                items = filteredSocieties,
                key = { society -> society.id }
            ) { society ->
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp)
                    ) {
                        Text(
                            text = society.name,
                            style =
                                MaterialTheme.typography
                                    .titleLarge,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(4.dp)
                        )

                        Text(
                            text = society.category,
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
                            text = society.description
                        )

                        Spacer(
                            modifier = Modifier.height(16.dp)
                        )

                        Button(
                            onClick = {
                                val position =
                                    societies.indexOfFirst {
                                        it.id == society.id
                                    }

                                if (position >= 0) {
                                    val updatedSociety =
                                        society.copy(
                                            isMember =
                                                !society.isMember
                                        )

                                    societies[position] =
                                        updatedSociety

                                    Log.i(
                                        "CampusSocieties",
                                        "Membership changed for " +
                                                "${society.name}: " +
                                                updatedSociety.isMember
                                    )
                                }
                            },
                            modifier =
                                Modifier.fillMaxWidth()
                        ) {
                            Text(
                                if (society.isMember) {
                                    stringResource(
                                        R.string.leave_society
                                    )
                                } else {
                                    stringResource(
                                        R.string.join_society
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}