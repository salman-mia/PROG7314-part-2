package com.example.campusconnect.network

data class RegisterRequest(
    val fullName: String,
    val email: String,
    val password: String
)