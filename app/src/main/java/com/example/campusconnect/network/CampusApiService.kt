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
 */
interface CampusApiService {

    // -------------------------
    // AUTHENTICATION
    // -------------------------

    @POST("api/Auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): AuthResponse

    @POST("api/Auth/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): AuthResponse

    // -------------------------
    // ANNOUNCEMENTS
    // -------------------------

    @GET("api/announcements")
    suspend fun getAnnouncements(): List<Announcement>

    // -------------------------
    // ACADEMIC RESOURCES
    // -------------------------

    @GET("api/resources")
    suspend fun getResources(): List<AcademicResource>

    // -------------------------
    // EVENTS
    // -------------------------

    @GET("api/events")
    suspend fun getEvents(): List<CampusEvent>

    @POST("api/events/{id}/rsvp")
    suspend fun createRsvp(
        @Path("id") eventId: Int
    )

    @DELETE("api/events/{id}/rsvp")
    suspend fun cancelRsvp(
        @Path("id") eventId: Int
    )

    // -------------------------
    // SOCIETIES
    // -------------------------

    @GET("api/societies")
    suspend fun getSocieties(): List<Society>

    @POST("api/societies/{id}/join")
    suspend fun joinSociety(
        @Path("id") societyId: Int
    )

    @DELETE("api/societies/{id}/leave")
    suspend fun leaveSociety(
        @Path("id") societyId: Int
    )

    // -------------------------
    // LOST AND FOUND
    // -------------------------

    @GET("api/lostfound")
    suspend fun getLostFoundListings(): List<LostFoundItem>

    @POST("api/lostfound")
    suspend fun createLostFoundListing(
        @Body listing: LostFoundItem
    ): LostFoundItem

    // -------------------------
    // CAMPUS SERVICES
    // -------------------------

    @GET("api/services")
    suspend fun getCampusServices(): List<CampusService>

    // -------------------------
    // LEADERBOARD
    // -------------------------

    @GET("api/leaderboard")
    suspend fun getLeaderboard(): List<LeaderboardEntry>
}