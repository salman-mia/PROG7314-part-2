package com.example.campusconnect.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.campusconnect.data.CampusRepository
import com.example.campusconnect.data.SettingsRepository
import com.example.campusconnect.model.ItemType
import com.example.campusconnect.model.UserSettings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * Connects the application interface to the data repositories.
 *
 * The ViewModel keeps screen data available when Android recreates
 * the activity, such as during a device rotation.
 */
class CampusViewModel(
    val campusRepository: CampusRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    companion object {
        private const val TAG = "CampusViewModel"
    }

    private val _isSignedIn = MutableStateFlow(false)

    /**
     * Indicates whether the current user is signed in.
     */
    val isSignedIn = _isSignedIn.asStateFlow()

    /**
     * Provides the latest saved settings to the interface.
     */
    val settings = settingsRepository.settings.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(
            stopTimeoutMillis = 5_000
        ),
        initialValue = UserSettings()
    )

    init {
        Log.i(
            TAG,
            "CampusViewModel created and application data loaded"
        )
    }

    /**
     * Updates the application sign-in state.
     *
     * Authentication itself is performed through the
     * CampusConnect REST API.
     */
    fun signIn() {

        _isSignedIn.value = true

        Log.i(
            TAG,
            "User sign-in state updated successfully"
        )
    }

    /**
     * Ends the current application session.
     */
    fun signOut() {

        _isSignedIn.value = false

        Log.i(
            TAG,
            "User signed out"
        )
    }

    /**
     * Records application navigation for Logcat evidence.
     */
    fun recordNavigation(
        destination: String
    ) {

        Log.d(
            TAG,
            "Navigation state changed to $destination"
        )
    }

    /**
     * Joins or leaves a student society.
     *
     * Society functionality is still using prototype data.
     */
    fun toggleSocietyMembership(
        societyId: Int
    ) {

        campusRepository.toggleSocietyMembership(
            societyId
        )
    }

    /**
     * Creates a new Lost and Found listing.
     *
     * Lost and Found will be connected to the REST API
     * in a later integration step.
     */
    fun addLostFoundListing(
        title: String,
        description: String,
        location: String,
        type: ItemType
    ) {

        campusRepository.addLostFoundListing(
            title = title,
            description = description,
            location = location,
            type = type
        )
    }

    /**
     * Saves the general notification preference.
     */
    fun setNotificationsEnabled(
        enabled: Boolean
    ) {

        viewModelScope.launch {

            settingsRepository
                .setNotificationsEnabled(enabled)
        }
    }

    /**
     * Saves the event notification preference.
     */
    fun setEventNotificationsEnabled(
        enabled: Boolean
    ) {

        viewModelScope.launch {

            settingsRepository
                .setEventNotificationsEnabled(enabled)
        }
    }

    /**
     * Saves the selected interface language.
     */
    fun setLanguage(
        language: String
    ) {

        viewModelScope.launch {

            settingsRepository
                .setLanguage(language)
        }
    }

    override fun onCleared() {

        Log.i(
            TAG,
            "CampusViewModel cleared from memory"
        )

        super.onCleared()
    }

    /**
     * Creates CampusViewModel with its required repositories.
     */
    class Factory(
        private val campusRepository: CampusRepository,
        private val settingsRepository: SettingsRepository
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(
            modelClass: Class<T>
        ): T {

            return CampusViewModel(
                campusRepository = campusRepository,
                settingsRepository = settingsRepository
            ) as T
        }
    }
}