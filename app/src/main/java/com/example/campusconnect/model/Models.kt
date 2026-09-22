package com.example.campusconnect.model

/**
 * Represents an announcement displayed to students.
 */
data class Announcement(
    val id: Int,
    val title: String,
    val content: String,
    val category: String,
    val publishedAt: String
)

/**
 * Represents an academic resource available in the application.
 */
data class AcademicResource(
    val id: Int,
    val title: String,
    val description: String,
    val resourceUrl: String,
    val moduleCode: String,
    val createdAt: String
)

/**
 * Represents a campus event returned by the CampusConnect API.
 */
data class CampusEvent(
    val id: Int,
    val title: String,
    val description: String,
    val location: String,
    val eventDate: String,
    val createdBy: Int,
    val createdAt: String,
    val rsvpCount: Int
)

/**
 * Represents a student society.
 */
data class Society(
    val id: Int,
    val name: String,
    val description: String,
    val contactEmail: String,
    val isJoined: Boolean = false
)

/**
 * Indicates whether an item was lost or found.
 */
enum class ItemType {
    LOST,
    FOUND
}

/**
 * Represents a Lost and Found listing.
 */
data class LostFoundItem(
    val id: Int,
    val title: String,
    val description: String,
    val location: String,
    val type: ItemType,
    val owner: String,
    val isResolved: Boolean = false
)

/**
 * Represents a service available on campus.
 */
data class CampusService(
    val id: Int,
    val name: String,
    val description: String,
    val location: String,
    val contact: String
)

/**
 * Represents one student on the participation leaderboard.
 */
data class LeaderboardEntry(
    val position: Int,
    val name: String,
    val points: Int
)

/**
 * Represents preferences selected in the Settings screen.
 */
data class UserSettings(
    val notificationsEnabled: Boolean = true,
    val eventNotifications: Boolean = true,
    val language: String = "English"
)