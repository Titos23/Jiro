package com.example.jiro

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TicketActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket)  // Make sure you have a corresponding layout XML file named activity_ticket.xml

        // Retrieve data from intent
        val passengerName = intent.getStringExtra("passengerName") ?: "Not Provided"
        val departureCity = intent.getStringExtra("departureCity") ?: "Not Provided"
        val arrivalCity = intent.getStringExtra("arrivalCity") ?: "Not Provided"
        val date = intent.getStringExtra("date") ?: "Not Provided"
        val randomStop = intent.getStringExtra("randomStop") ?: "None"
        val seatNumber = intent.getStringExtra("seatNumber") ?: "Not Assigned"

        // Set the data to TextViews
        findViewById<TextView>(R.id.tvPassengerName).text = "Passenger: $passengerName"
        findViewById<TextView>(R.id.tvFlightDepartureCity).text = "Departure City: $departureCity"
        findViewById<TextView>(R.id.tvFlightArrivalCity).text = "Arrival City: $arrivalCity"
        findViewById<TextView>(R.id.tvFlightDate).text = "Flight Date: $date"
        findViewById<TextView>(R.id.tvRandomStop).text = "Random Stop: $randomStop"
        findViewById<TextView>(R.id.tvSeatNumber).text = "Seat Number: $seatNumber"
    }
}
