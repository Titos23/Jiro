package com.example.jiro

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class TicketActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket)

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

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.navigation_tickets

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    startActivity(Intent(this, MainActivity2::class.java))
                    finish() // Optionally finish to clear the activity stack
                    true
                }
                R.id.navigation_tickets -> true // Already here
                else -> false
            }
        }


    }
}
