package com.example.campusconnect.data

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay

class LostFoundSyncWorker(
    applicationContext: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(
    applicationContext,
    workerParameters
) {

    override suspend fun doWork(): Result {
        Log.i(
            TAG,
            "Network available. Offline synchronisation started."
        )

        val database =
            LostFoundDatabaseHelper(applicationContext)

        return try {
            val pendingItems =
                database.getPendingItems()

            if (pendingItems.isEmpty()) {
                Log.i(
                    TAG,
                    "No pending Lost and Found items to synchronise."
                )

                return Result.success()
            }

            pendingItems.forEach { item ->
                /*
                 * This is the REST API handover point.
                 *
                 * When the group backend endpoint is connected,
                 * the Retrofit POST request will be made here.
                 * WorkManager already guarantees that this block
                 * only runs once a network connection is available.
                 */
                delay(500)

                val uploadedSuccessfully = true

                if (uploadedSuccessfully) {
                    database.markItemAsSynced(item.id)

                    Log.i(
                        TAG,
                        "Item ${item.id} synchronised successfully."
                    )
                } else {
                    Log.w(
                        TAG,
                        "Server rejected item ${item.id}."
                    )

                    return Result.retry()
                }
            }

            Log.i(
                TAG,
                "All pending Lost and Found items synchronised."
            )

            Result.success()
        } catch (exception: Exception) {
            Log.e(
                TAG,
                "Synchronisation failed. WorkManager will retry.",
                exception
            )

            Result.retry()
        } finally {
            database.close()
        }
    }

    companion object {
        private const val TAG =
            "LostFoundSyncWorker"

        const val UNIQUE_WORK_NAME =
            "campus_lost_found_sync"
    }
}