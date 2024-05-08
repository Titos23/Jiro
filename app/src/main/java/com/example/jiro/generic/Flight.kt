package com.example.jiro.generic
import android.content.Context

class Flight(
    val departure: Airport,
    val arrival: Airport,
    val departureAirportCode: String,
    val arrivalAirportCode: String,
    val flightDate: String,

    val price: String,
    val flightTime: String
) {
    companion object {
        fun searchFlights(context: Context, departure: Airport, arrival: Airport, date:String): List<Flight> {
            return listOf(
                Flight(departure, arrival, Airport.getAiportCode(departure), Airport.getAiportCode(arrival), date,"1500","6h45"),
                Flight(departure, arrival, Airport.getAiportCode(departure), Airport.getAiportCode(arrival), date, "700","6h30"),
            )
        }
    }
}
