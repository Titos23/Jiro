package com.example.jiro

class FlightStopover(
    override val origin: String,
    override val destination: String,
    val stopoverDuration: Double
) : Travel() {
    override fun displayInfo() {
        println("Stopover at $destination for $stopoverDuration hours.")
    }
}
