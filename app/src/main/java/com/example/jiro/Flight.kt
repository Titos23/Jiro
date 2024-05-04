package com.example.jiro

class Flight(
    override val origin: String,
    override val destination: String,
    val flightNumber: String,
    var duration: Double
) : Travel() {
    override fun displayInfo() {
        println("Flight Number: $flightNumber, from $origin to $destination, duration: $duration hours.")
    }
}
