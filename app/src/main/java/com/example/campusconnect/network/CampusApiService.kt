package com.example.campusconnect.network

import com.example.campusconnect.model.AcademicResource
import com.example.campusconnect.model.Announcement
import com.example.campusconnect.model.CampusEvent
import com.example.campusconnect.model.CampusService
import com.example.campusconnect.model.LeaderboardEntry
import com.example.campusconnect.model.LostFoundItem
import com.example.campusconnect.model.Society
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Defines the REST API endpoints used by CampusConnect.
 *
 * These endpoint addresses follow the system design completed
 * during Part 1. The backend group member must implement matching
 * endpoints in the backend application.
 */
interface CampusApiService {

    /**
     * Returns all active campus announcements.
     */
    @GET("api/announcements")
    suspend fun getAnnouncements(): List<Announcement>

    /**
     * Returns all available academic resources.
     */
    @GET("api/resources")
    suspend fun getResources(): List<AcademicResource>

    /**
     * Returns all available campus events.
     */
    @GET("api/events")
    suspend fun getEvents(): List<CampusEvent>

    /**
     * Registers the authenticated student for an event.
     */
    @POST("api/events/{id}/rsvp")
    suspend fun createRsvp(
        @Path("id") eventId: Int
    )

    /**
     * Cancels the authenticated student's event registration.
     */
    @DELETE("api/events/{id}/rsvp")
    suspend fun cancelRsvp(
        @Path("id") eventId: Int
    )

    /**
     * Returns all active student societies.
     */
    @GET("api/societies")
    suspend fun getSocieties(): List<Society>

    /**
     * Joins a selected student society.
     */
    @POST("api/societies/{id}/join")
    suspend fun joinSociety(
        @Path("id") societyId: Int
    )

    /**
     * Leaves a selected student society.
     */
    @DELETE("api/societies/{id}/leave")
    suspend fun leaveSociety(
        @Path("id") societyId: Int
    )

    /**
     * Returns Lost and Found listings.
     */
    @GET("api/lostfound")
    suspend fun getLostFoundListings(): List<LostFoundItem>

    /**
     * Creates a new Lost and Found listing.
     */
    @POST("api/lostfound")
    suspend fun createLostFoundListing(
        @Body listing: LostFoundItem
    ): LostFoundItem

    /**
     * Returns available campus services.
     */
    @GET("api/services")
    suspend fun getCampusServices(): List<CampusService>

    /**
     * Returns the student participation leaderboard.
     */
    @GET("api/leaderboard")
    suspend fun getLeaderboard(): List<LeaderboardEntry>
}