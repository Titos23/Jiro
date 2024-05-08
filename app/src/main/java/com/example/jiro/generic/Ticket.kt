package com.example.jiro.generic

import java.util.UUID

data class Ticket(
    val id: String = UUID.randomUUID().toString(),
    val passengerName: String,
    val departureCity: String,
    val arrivalCity: String,
    val date: String,
    val randomStop: String?,
    val seatNumber: String
)
