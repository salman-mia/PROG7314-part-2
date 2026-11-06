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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.campusconnect.R

private data class LeaderboardUiModel(
    val position: Int,
    val studentName: String,
    val points: Int,
    val isCurrentUser: Boolean = false
)

/**
 * Displays translated participation points and rankings.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaderboardScreen(
    onBack: () -> Unit
) {
    val leaders = listOf(
        LeaderboardUiModel(1, "Naledi M.", 1250),
        LeaderboardUiModel(2, "Thabo K.", 1100),
        LeaderboardUiModel(3, "Ayesha P.", 980),
        LeaderboardUiModel(
            position = 4,
            studentName = stringResource(R.string.you),
            points = 850,
            isCurrentUser = true
        ),
        LeaderboardUiModel(5, "Liam S.", 790)
    )

    LaunchedEffect(Unit) {
        Log.i(
            "CampusLeaderboard",
            "Leaderboard screen opened"
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(
                            R.string.leaderboard
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
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor =
                            MaterialTheme.colorScheme
                                .primaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = stringResource(
                                R.string.your_campus_points
                            ),
                            style =
                                MaterialTheme.typography
                                    .titleMedium
                        )

                        Spacer(
                            modifier = Modifier.height(6.dp)
                        )

                        Text(
                            text = stringResource(
                                R.string.points_value,
                                850
                            ),
                            style =
                                MaterialTheme.typography
                                    .headlineMedium,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            stringResource(
                                R.string.current_position,
                                4
                            )
                        )
                    }
                }
            }

            item {
                Text(
                    text = stringResource(
                        R.string.top_students
                    ),
                    style =
                        MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            items(
                items = leaders,
                key = { leader -> leader.position }
            ) { leader ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors =
                        if (leader.isCurrentUser) {
                            CardDefaults.cardColors(
                                containerColor =
                                    MaterialTheme.colorScheme
                                        .secondaryContainer
                            )
                        } else {
                            CardDefaults.cardColors()
                        }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        horizontalArrangement =
                            Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(
                                R.string.leaderboard_entry,
                                leader.position,
                                leader.studentName
                            ),
                            fontWeight =
                                if (leader.isCurrentUser) {
                                    FontWeight.Bold
                                } else {
                                    FontWeight.Normal
                                }
                        )

                        Text(
                            text = stringResource(
                                R.string.points_value,
                                leader.points
                            ),
                            fontWeight =
                                FontWeight.SemiBold
                        )
                    }
                }
            }

            item {
                Text(
                    text = stringResource(
                        R.string.leaderboard_explanation
                    ),
                    style =
                        MaterialTheme.typography.bodyMedium,
                    color =
                        MaterialTheme.colorScheme
                            .onSurfaceVariant
                )
            }
        }
    }
}