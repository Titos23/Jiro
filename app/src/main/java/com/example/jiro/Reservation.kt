package com.example.jiro

class Reservation(val customer: Customer, val flight: Flight) {
    var status: String = "Booked"

    fun createReservation() {
        println("Reservation created for ${customer.name} on flight ${flight.flightNumber}.")
    }

    fun cancelReservation() {
        status = "Cancelled"
        println("Reservation cancelled for ${customer.name} on flight ${flight.flightNumber}.")
    }
}
