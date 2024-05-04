package com.example.jiro

class Airline(val name: String, val iataCode: String, val mainHub: String) {
    val flights = mutableListOf<Flight>()

    fun addFlight(flight: Flight) {
        flights.add(flight)
    }

    fun removeFlight(flight: Flight) {
        flights.remove(flight)
    }
}

