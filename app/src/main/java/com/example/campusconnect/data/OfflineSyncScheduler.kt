package com.example.campusconnect.data

import android.content.Context
import android.util.Log
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

object OfflineSyncScheduler {

    fun scheduleLostFoundSync(
        context: Context
    ) {
        val networkConstraints =
            Constraints.Builder()
                .setRequiredNetworkType(
                    NetworkType.CONNECTED
                )
                .build()

        val syncRequest =
            OneTimeWorkRequestBuilder<
                    LostFoundSyncWorker
                    >()
                .setConstraints(networkConstraints)
                .setBackoffCriteria(
                    BackoffPolicy.EXPONENTIAL,
                    10,
                    TimeUnit.SECONDS
                )
                .addTag(
                    LostFoundSyncWorker.UNIQUE_WORK_NAME
                )
                .build()

        WorkManager
            .getInstance(context)
            .enqueueUniqueWork(
                LostFoundSyncWorker.UNIQUE_WORK_NAME,
                ExistingWorkPolicy.REPLACE,
                syncRequest
            )

        Log.i(
            "OfflineSyncScheduler",
            "Lost and Found synchronisation queued. " +
                    "It will run when internet is available."
        )
    }
}