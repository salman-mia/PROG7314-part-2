package com.example.campusconnect.model

data class OfflineLostFoundItem(
    val id: Long,
    val itemName: String,
    val description: String,
    val itemType: String,
    val location: String,
    val contactDetails: String,
    val createdAt: Long,
    val syncStatus: String
) {
    val isSynced: Boolean
        get() = syncStatus == SYNCED

    companion object {
        const val PENDING = "PENDING"
        const val SYNCED = "SYNCED"
    }
}