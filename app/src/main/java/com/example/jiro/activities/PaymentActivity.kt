package com.example.jiro.activities
import com.example.jiro.payment.PaymentProcessor
import android.view.View
import android.widget.LinearLayout
import com.google.android.material.chip.ChipGroup
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.jiro.R
import com.example.jiro.generic.ReservationHelper
import com.example.jiro.generic.Ticket
import com.example.jiro.payment.PaymentStatusLogger


class PaymentActivity : AppCompatActivity() {
    private lateinit var paymentButton: Button
    private lateinit var reservationHelper: ReservationHelper
    private lateinit var paymentProcessor: PaymentProcessor
    private lateinit var paymentMethod: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        paymentProcessor = PaymentProcessor(this)
        paymentProcessor.addObserver(PaymentStatusLogger())

        val passengerNameEditText = findViewById<EditText>(R.id.passengerNameInput)
        val passengerNameTextView = findViewById<TextView>(R.id.passengerName)

        val button = findViewById<Button>(R.id.paymentButton)
        button.setOnClickListener {
            reservationHelper = ReservationHelper(this)
            val newTicket = Ticket(
                passengerName = passengerNameEditText.text.toString(),
                departureCity = findViewById<TextView>(R.id.flightDepartureCity).text.toString(),
                arrivalCity = findViewById<TextView>(R.id.flightArrivalCity).text.toString(),
                date = findViewById<TextView>(R.id.flightDate).text.toString(),
                randomStop = findViewById<TextView>(R.id.randomStop).text.toString(),
                seatNumber = findViewById<TextView>(R.id.seatNumber).text.toString()
            )
            val ticketId = reservationHelper.addTicket(newTicket)
            Toast.makeText(this, "Ticket added with ID: $ticketId", Toast.LENGTH_LONG).show()
            paymentProcessor.processPayment(paymentMethod,500.00)
            finish()
        }
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
        // UI references
        val chipGroup: ChipGroup = findViewById(R.id.chipGroupPaymentMethod)
        val creditCardLayout: LinearLayout = findViewById(R.id.creditCardLayout)
        val debitCardLayout: LinearLayout = findViewById(R.id.debitCardLayout)
        val paymentButton: Button = findViewById(R.id.paymentButton)

        // Initially hide all payment specific details
        creditCardLayout.visibility = View.GONE
        debitCardLayout.visibility = View.GONE

        // Chip selection listener
        chipGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.chipCreditCard -> {
                    creditCardLayout.visibility = View.VISIBLE
                    debitCardLayout.visibility = View.GONE
                    paymentMethod = "CreditCard"

                }
                R.id.chipDebitCard -> {
                    debitCardLayout.visibility = View.VISIBLE
                    creditCardLayout.visibility = View.GONE
                    paymentMethod = "DebitCard"
                }
            }
        }


    }

}
