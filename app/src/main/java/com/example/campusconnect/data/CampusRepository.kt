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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Provides prototype data while the group's REST API is being developed.
 *
 * The prototype data will later be replaced with information obtained
 * from the CampusConnect REST API.
 */
class CampusRepository {

    companion object {
        private const val TAG = "CampusRepository"
    }

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

    private val _events = MutableStateFlow(
        listOf(
            CampusEvent(
                id = 1,
                title = "Spring Career Fair",
                date = "25 September 2026",
                time = "10:00",
                location = "Main Hall",
                description = "Meet graduate employers and explore internship opportunities."
            ),
            CampusEvent(
                id = 2,
                title = "Android Study Jam",
                date = "30 September 2026",
                time = "14:00",
                location = "Computer Lab 3",
                description = "Build a small Android application with fellow students."
            ),
            CampusEvent(
                id = 3,
                title = "Campus Sports Day",
                date = "4 October 2026",
                time = "09:00",
                location = "Sports Field",
                description = "Participate in social sporting activities with other students."
            )
        )
    )

    val events = _events.asStateFlow()

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

    /**
     * Registers or unregisters the student for an event.
     */
    fun toggleRsvp(eventId: Int) {
        _events.value = _events.value.map { event ->
            if (event.id == eventId) {
                event.copy(
                    isRegistered = !event.isRegistered
                )
            } else {
                event
            }
        }

        Log.i(
            TAG,
            "RSVP state changed for eventId=$eventId"
        )
    }

    /**
     * Joins or leaves a selected society.
     */
    fun toggleSocietyMembership(societyId: Int) {
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

    /**
     * Creates a new Lost and Found listing.
     */
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

        _lostFound.value = _lostFound.value + newListing

        Log.i(
            TAG,
            "Lost and Found listing created: id=$nextId, type=$type"
        )
    }
}