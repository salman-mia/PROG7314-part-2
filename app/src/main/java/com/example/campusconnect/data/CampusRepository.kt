package com.example.campusconnect.data

import android.util.Log
import com.example.campusconnect.model.AcademicResource
import com.example.campusconnect.model.Announcement
import com.example.campusconnect.model.CampusEvent
import com.example.campusconnect.model.CampusService
import com.example.campusconnect.model.ItemType
import com.example.campusconnect.model.LeaderboardEntry
import com.example.campusconnect.model.LostFoundItem
import com.example.campusconnect.model.Society
import com.example.campusconnect.network.ApiClient
import com.example.campusconnect.network.AuthResponse
import com.example.campusconnect.network.LoginRequest
import com.example.campusconnect.network.RegisterRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Repository for CampusConnect.
 *
 * Authentication and Campus Events are connected to the ASP.NET REST API.
 * Prototype data is temporarily retained for features that
 * have not yet been connected to backend endpoints.
 */
class CampusRepository {

    companion object {
        private const val TAG = "CampusRepository"
    }

    // ---------------------------------------------------------
    // AUTHENTICATION - REAL BACKEND
    // ---------------------------------------------------------

    suspend fun login(
        email: String,
        password: String
    ): Result<AuthResponse> {

        return try {

            val response = ApiClient.service.login(
                LoginRequest(
                    email = email.trim(),
                    password = password
                )
            )

            Log.i(
                TAG,
                "Login successful for ${response.email}"
            )

            Result.success(response)

        } catch (exception: Exception) {

            Log.e(
                TAG,
                "Login failed",
                exception
            )

            Result.failure(exception)
        }
    }

    suspend fun register(
        fullName: String,
        email: String,
        password: String
    ): Result<AuthResponse> {

        return try {

            val response = ApiClient.service.register(
                RegisterRequest(
                    fullName = fullName.trim(),
                    email = email.trim(),
                    password = password
                )
            )

            Log.i(
                TAG,
                "Registration successful for ${response.email}"
            )

            Result.success(response)

        } catch (exception: Exception) {

            Log.e(
                TAG,
                "Registration failed",
                exception
            )

            Result.failure(exception)
        }
    }

    // ---------------------------------------------------------
    // CAMPUS EVENTS - REAL BACKEND
    // ---------------------------------------------------------

    suspend fun getEventsFromApi(): Result<List<CampusEvent>> {

        return try {

            val events = ApiClient.service.getEvents()

            Log.i(
                TAG,
                "Loaded ${events.size} events from API"
            )

            Result.success(events)

        } catch (exception: Exception) {

            Log.e(
                TAG,
                "Failed to load events from API",
                exception
            )

            Result.failure(exception)
        }
    }

    suspend fun createEventRsvp(
        eventId: Int
    ): Result<Unit> {

        return try {

            ApiClient.service.createRsvp(eventId)

            Log.i(
                TAG,
                "RSVP created for eventId=$eventId"
            )

            Result.success(Unit)

        } catch (exception: Exception) {

            Log.e(
                TAG,
                "Failed to create RSVP for eventId=$eventId",
                exception
            )

            Result.failure(exception)
        }
    }

    suspend fun cancelEventRsvp(
        eventId: Int
    ): Result<Unit> {

        return try {

            ApiClient.service.cancelRsvp(eventId)

            Log.i(
                TAG,
                "RSVP cancelled for eventId=$eventId"
            )

            Result.success(Unit)

        } catch (exception: Exception) {

            Log.e(
                TAG,
                "Failed to cancel RSVP for eventId=$eventId",
                exception
            )

            Result.failure(exception)
        }
    }

    // ---------------------------------------------------------
    // PROTOTYPE ANNOUNCEMENTS
    // ---------------------------------------------------------

    val announcements = listOf(
        Announcement(
            id = 1,
            title = "Library hours extended",
            content = "The library will remain open until 22:00 during tests.",
            category = "Academic",
            publishedAt = "18 September 2026"
        ),
        Announcement(
            id = 2,
            title = "Student wellness day",
            content = "Visit the main quad for wellness support and activities.",
            category = "Campus",
            publishedAt = "16 September 2026"
        ),
        Announcement(
            id = 3,
            title = "Registration reminder",
            content = "Students must confirm their module registration before Friday.",
            category = "Administration",
            publishedAt = "15 September 2026"
        )
    )

    // ---------------------------------------------------------
    // PROTOTYPE ACADEMIC RESOURCES
    // ---------------------------------------------------------

    val resources = listOf(
        AcademicResource(
            id = 1,
            title = "Kotlin fundamentals",
            moduleCode = "PROG7314",
            type = "Study guide",
            description = "A short guide covering Kotlin and Android development."
        ),
        AcademicResource(
            id = 2,
            title = "Database revision",
            moduleCode = "INSY7314",
            type = "Notes",
            description = "Revision notes covering relational database design."
        ),
        AcademicResource(
            id = 3,
            title = "Project management template",
            moduleCode = "PMIC7312",
            type = "Template",
            description = "A planning template for student group projects."
        )
    )

    // ---------------------------------------------------------
    // PROTOTYPE SOCIETIES
    // ---------------------------------------------------------

    private val _societies = MutableStateFlow(
        listOf(
            Society(
                id = 1,
                name = "Developers Society",
                description = "Coding workshops, hackathons and peer learning.",
                contactEmail = "developers@campus.ac.za"
            ),
            Society(
                id = 2,
                name = "Debating Society",
                description = "Weekly debates and public-speaking practice.",
                contactEmail = "debate@campus.ac.za"
            ),
            Society(
                id = 3,
                name = "Photography Society",
                description = "Campus photography walks and editing workshops.",
                contactEmail = "photography@campus.ac.za"
            )
        )
    )

    val societies = _societies.asStateFlow()

    // ---------------------------------------------------------
    // PROTOTYPE LOST AND FOUND
    // ---------------------------------------------------------

    private val _lostFound = MutableStateFlow(
        listOf(
            LostFoundItem(
                id = 1,
                title = "Black water bottle",
                description = "Metal bottle with a blue sticker.",
                location = "Library first floor",
                type = ItemType.LOST,
                owner = "Demo Student"
            ),
            LostFoundItem(
                id = 2,
                title = "Student card",
                description = "The card was handed to campus security.",
                location = "Main quad",
                type = ItemType.FOUND,
                owner = "Campus Security"
            )
        )
    )

    val lostFound = _lostFound.asStateFlow()

    // ---------------------------------------------------------
    // PROTOTYPE CAMPUS SERVICES
    // ---------------------------------------------------------

    val services = listOf(
        CampusService(
            id = 1,
            name = "Library",
            description = "Books, study spaces and research assistance.",
            location = "Learning Centre",
            contact = "library@campus.ac.za"
        ),
        CampusService(
            id = 2,
            name = "IT Support",
            description = "Password, Wi-Fi and device assistance.",
            location = "Administration Block 2",
            contact = "support@campus.ac.za"
        ),
        CampusService(
            id = 3,
            name = "Student Administration",
            description = "Registration and academic-record support.",
            location = "Administration Block 1",
            contact = "admin@campus.ac.za"
        )
    )

    // ---------------------------------------------------------
    // PROTOTYPE LEADERBOARD
    // ---------------------------------------------------------

    val leaderboard = listOf(
        LeaderboardEntry(
            position = 1,
            name = "Naledi M.",
            points = 480
        ),
        LeaderboardEntry(
            position = 2,
            name = "Aiden K.",
            points = 430
        ),
        LeaderboardEntry(
            position = 3,
            name = "Demo Student",
            points = 390
        )
    )

    // ---------------------------------------------------------
    // PROTOTYPE SOCIETY FUNCTIONS
    // ---------------------------------------------------------

    fun toggleSocietyMembership(
        societyId: Int
    ) {

        _societies.value = _societies.value.map { society ->

            if (society.id == societyId) {

                society.copy(
                    isJoined = !society.isJoined
                )

            } else {

                society
            }
        }

        Log.i(
            TAG,
            "Society membership changed for societyId=$societyId"
        )
    }

    // ---------------------------------------------------------
    // PROTOTYPE LOST AND FOUND FUNCTIONS
    // ---------------------------------------------------------

    fun addLostFoundListing(
        title: String,
        description: String,
        location: String,
        type: ItemType
    ) {

        val nextId =
            (_lostFound.value.maxOfOrNull { it.id } ?: 0) + 1

        val newListing = LostFoundItem(
            id = nextId,
            title = title,
            description = description,
            location = location,
            type = type,
            owner = "Demo Student"
        )

        _lostFound.value =
            _lostFound.value + newListing

        Log.i(
            TAG,
            "Lost and Found listing created: id=$nextId, type=$type"
        )
    }
}