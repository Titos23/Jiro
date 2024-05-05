package com.example.jiro

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TicketActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket)

        val tvPassengerName = findViewById<TextView>(R.id.tvPassengerName)
        val tvFlightDepartureCity = findViewById<TextView>(R.id.tvFlightDepartureCity)
        val tvFlightArrivalCity = findViewById<TextView>(R.id.tvFlightArrivalCity)
        val tvFlightDate = findViewById<TextView>(R.id.tvFlightDate)
        val tvRandomStop = findViewById<TextView>(R.id.tvRandomStop)
        val tvSeatNumber = findViewById<TextView>(R.id.tvSeatNumber)


        // Retrieve and display the data from the intent
        intent.extras?.let {
            tvPassengerName.text = "Passenger Name: ${it.getString("passengerName")}"
            tvFlightDepartureCity.text = "Departure City: ${it.getString("departureCity")}"
            tvFlightArrivalCity.text = "Arrival City: ${it.getString("arrivalCity")}"
            tvFlightDate.text = "Flight Date: ${it.getString("flightDate")}"
            tvRandomStop.text = "Random Stop: ${it.getString("randomStop") ?: "None"}"
            tvSeatNumber.text = "Seat Number: ${it.getString("seatNumber")}"
        }
    }
}
