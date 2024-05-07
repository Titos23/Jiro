package com.example.jiro

data class Ticket(
    val id: String? = null,  // UUID as String
    val passengerName: String,
    val departureCity: String,
    val arrivalCity: String,
    val date: String,
    val randomStop: String?,
    val seatNumber: String
)
