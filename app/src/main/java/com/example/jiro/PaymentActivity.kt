package com.example.jiro

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PaymentActivity : AppCompatActivity() {

    private lateinit var paymentButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        val passengerNameEditText = findViewById<EditText>(R.id.passengerNameInput)
        val passengerNameTextView = findViewById<TextView>(R.id.passengerName)

        val button = findViewById<Button>(R.id.paymentButton)
        button.setOnClickListener {

            val intent = Intent(this, TicketActivity::class.java).apply {
                putExtra("passengerName", passengerNameEditText.text.toString())
                putExtra("departureCity", findViewById<TextView>(R.id.flightDepartureCity).text.toString())
                putExtra("arrivalCity", findViewById<TextView>(R.id.flightArrivalCity).text.toString())
                putExtra("date", findViewById<TextView>(R.id.flightDate).text.toString())
                putExtra("randomStop", findViewById<TextView>(R.id.randomStop).text.toString())
                putExtra("seatNumber", findViewById<TextView>(R.id.seatNumber).text.toString())
            }
            startActivity(intent)
        }



        // Initializing TextViews with data received via Intent
        val departureCity = intent.getStringExtra("departureCity") ?: "Not Provided"
        val arrivalCity = intent.getStringExtra("arrivalCity") ?: "Not Provided"
        val price = intent.getIntExtra("price", 0)
        val date = intent.getStringExtra("date") ?: "Not Provided"
        val randomStop = intent.getStringExtra("randomStop") ?: "None"
        val seatNumber = intent.getStringExtra("seatNumber") ?: "Not Assigned"

        findViewById<TextView>(R.id.flightDepartureCity).text = "Departure: $departureCity"
        findViewById<TextView>(R.id.flightArrivalCity).text = "Arrival: $arrivalCity"
        findViewById<TextView>(R.id.flightDate).text = "Date: $date"
        findViewById<TextView>(R.id.randomStop).text = "Stop: $randomStop"
        findViewById<TextView>(R.id.seatNumber).text = "Seat Number: $seatNumber"

        // Set up a TextWatcher to update the passenger name TextView in real time
        passengerNameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // This method can be empty
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Update the TextView to reflect the name as it's typed
                passengerNameTextView.text = "Passenger: ${s.toString()}"
            }

            override fun afterTextChanged(s: Editable?) {
                // This method can be empty
            }
        })
    }

}
