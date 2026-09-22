package com.example.campusconnect.network

data class AuthResponse(
    val userId: Int,
    val fullName: String,
    val email: String,
    val token: String
)