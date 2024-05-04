package com.example.jiro

class Customer(val name: String, val email: String) {
    private val bookingHistory = mutableListOf<Reservation>()

    fun addReservation(reservation: Reservation) {
        bookingHistory.add(reservation)
    }

    fun getBookingHistory() {
        bookingHistory.forEach { res ->
            println("Reservation for ${res.flight.destination} on ${res.flight.flightNumber}. Status: ${res.status}")
        }
    }
}
