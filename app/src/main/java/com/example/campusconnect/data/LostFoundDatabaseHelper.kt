package com.example.campusconnect.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.campusconnect.model.OfflineLostFoundItem

class LostFoundDatabaseHelper(
    context: Context
) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {

    override fun onCreate(database: SQLiteDatabase) {
        database.execSQL(
            """
            CREATE TABLE $TABLE_ITEMS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_ITEM_NAME TEXT NOT NULL,
                $COLUMN_DESCRIPTION TEXT NOT NULL,
                $COLUMN_ITEM_TYPE TEXT NOT NULL,
                $COLUMN_LOCATION TEXT NOT NULL,
                $COLUMN_CONTACT_DETAILS TEXT NOT NULL,
                $COLUMN_CREATED_AT INTEGER NOT NULL,
                $COLUMN_SYNC_STATUS TEXT NOT NULL
            )
            """.trimIndent()
        )

        Log.i(
            TAG,
            "Local Lost and Found database created"
        )
    }

    override fun onUpgrade(
        database: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        database.execSQL(
            "DROP TABLE IF EXISTS $TABLE_ITEMS"
        )

        onCreate(database)

        Log.i(
            TAG,
            "Local database upgraded from version " +
                    "$oldVersion to $newVersion"
        )
    }

    fun insertItem(
        itemName: String,
        description: String,
        itemType: String,
        location: String,
        contactDetails: String
    ): Long {
        val values = ContentValues().apply {
            put(COLUMN_ITEM_NAME, itemName)
            put(COLUMN_DESCRIPTION, description)
            put(COLUMN_ITEM_TYPE, itemType)
            put(COLUMN_LOCATION, location)
            put(
                COLUMN_CONTACT_DETAILS,
                contactDetails
            )
            put(
                COLUMN_CREATED_AT,
                System.currentTimeMillis()
            )
            put(
                COLUMN_SYNC_STATUS,
                OfflineLostFoundItem.PENDING
            )
        }

        val insertedId = writableDatabase.insert(
            TABLE_ITEMS,
            null,
            values
        )

        if (insertedId != -1L) {
            Log.i(
                TAG,
                "Offline item stored locally with ID $insertedId"
            )
        } else {
            Log.e(
                TAG,
                "Failed to store offline item locally"
            )
        }

        return insertedId
    }

    fun getAllItems(): List<OfflineLostFoundItem> {
        return readItems(
            selection = null,
            selectionArguments = null
        )
    }

    fun getPendingItems():
            List<OfflineLostFoundItem> {
        return readItems(
            selection =
                "$COLUMN_SYNC_STATUS = ?",
            selectionArguments = arrayOf(
                OfflineLostFoundItem.PENDING
            )
        )
    }

    private fun readItems(
        selection: String?,
        selectionArguments: Array<String>?
    ): List<OfflineLostFoundItem> {
        val items =
            mutableListOf<OfflineLostFoundItem>()

        val cursor = readableDatabase.query(
            TABLE_ITEMS,
            null,
            selection,
            selectionArguments,
            null,
            null,
            "$COLUMN_CREATED_AT DESC"
        )

        cursor.use {
            val idIndex =
                it.getColumnIndexOrThrow(COLUMN_ID)

            val nameIndex =
                it.getColumnIndexOrThrow(
                    COLUMN_ITEM_NAME
                )

            val descriptionIndex =
                it.getColumnIndexOrThrow(
                    COLUMN_DESCRIPTION
                )

            val typeIndex =
                it.getColumnIndexOrThrow(
                    COLUMN_ITEM_TYPE
                )

            val locationIndex =
                it.getColumnIndexOrThrow(
                    COLUMN_LOCATION
                )

            val contactIndex =
                it.getColumnIndexOrThrow(
                    COLUMN_CONTACT_DETAILS
                )

            val createdIndex =
                it.getColumnIndexOrThrow(
                    COLUMN_CREATED_AT
                )

            val statusIndex =
                it.getColumnIndexOrThrow(
                    COLUMN_SYNC_STATUS
                )

            while (it.moveToNext()) {
                items.add(
                    OfflineLostFoundItem(
                        id = it.getLong(idIndex),
                        itemName =
                            it.getString(nameIndex),
                        description =
                            it.getString(
                                descriptionIndex
                            ),
                        itemType =
                            it.getString(typeIndex),
                        location =
                            it.getString(
                                locationIndex
                            ),
                        contactDetails =
                            it.getString(contactIndex),
                        createdAt =
                            it.getLong(createdIndex),
                        syncStatus =
                            it.getString(statusIndex)
                    )
                )
            }
        }

        return items
    }

    fun markItemAsSynced(itemId: Long): Boolean {
        val values = ContentValues().apply {
            put(
                COLUMN_SYNC_STATUS,
                OfflineLostFoundItem.SYNCED
            )
        }

        val updatedRows = writableDatabase.update(
            TABLE_ITEMS,
            values,
            "$COLUMN_ID = ?",
            arrayOf(itemId.toString())
        )

        if (updatedRows > 0) {
            Log.i(
                TAG,
                "Item $itemId marked as synchronised"
            )
        }

        return updatedRows > 0
    }

    fun pendingItemCount(): Int {
        val cursor = readableDatabase.rawQuery(
            """
            SELECT COUNT(*)
            FROM $TABLE_ITEMS
            WHERE $COLUMN_SYNC_STATUS = ?
            """.trimIndent(),
            arrayOf(OfflineLostFoundItem.PENDING)
        )

        return cursor.use {
            if (it.moveToFirst()) {
                it.getInt(0)
            } else {
                0
            }
        }
    }

    companion object {
        private const val TAG =
            "LostFoundDatabase"

        private const val DATABASE_NAME =
            "campus_connect_offline.db"

        private const val DATABASE_VERSION = 1

        private const val TABLE_ITEMS =
            "lost_found_items"

        private const val COLUMN_ID = "id"
        private const val COLUMN_ITEM_NAME =
            "item_name"
        private const val COLUMN_DESCRIPTION =
            "description"
        private const val COLUMN_ITEM_TYPE =
            "item_type"
        private const val COLUMN_LOCATION =
            "location"
        private const val COLUMN_CONTACT_DETAILS =
            "contact_details"
        private const val COLUMN_CREATED_AT =
            "created_at"
        private const val COLUMN_SYNC_STATUS =
            "sync_status"
    }
}