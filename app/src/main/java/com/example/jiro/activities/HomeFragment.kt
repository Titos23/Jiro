package com.example.jiro.activities

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.jiro.generic.Airport
import com.example.jiro.generic.Flight
import com.example.jiro.R

class HomeFragment : Fragment() {

    private lateinit var departureSpinner: Spinner
    private lateinit var arrivalSpinner: Spinner
    private var selectedDeparture: Airport? = null
    private var selectedArrival: Airport? = null
    private lateinit var listItems: List<Airport>
    private lateinit var layoutContainer: LinearLayout
    private lateinit var selectedDate: String  // For formatted date in the flight info section

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI(view)
    }

    private fun setupUI(view: View) {
        layoutContainer = view.findViewById(R.id.flightContainer)
        view.findViewById<Button>(R.id.searchFlight).setOnClickListener {
            setupFlightSearch()
        }

        departureSpinner = view.findViewById(R.id.departure)
        arrivalSpinner = view.findViewById(R.id.arrival)
        listItems = Airport.getAirports(requireContext()) // Parsing XML data
        setupSpinners()

        initializeDate(view) // Initialize date on start
        view.findViewById<TextView>(R.id.tvSelectDate).setOnClickListener {
            showDatePickerDialog(view)
        }
    }

    private fun setupSpinners() {
        val context = context ?: return
        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, listItems)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        departureSpinner.adapter = adapter
        arrivalSpinner.adapter = adapter

        departureSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedDeparture = parent.getItemAtPosition(position) as Airport
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                selectedDeparture = null
            }
        }

        arrivalSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedArrival = parent.getItemAtPosition(position) as Airport
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                selectedArrival = null
            }
        }
    }

    private fun setupFlightSearch() {
        layoutContainer.removeAllViews()
        selectedDeparture?.let { departure ->
            selectedArrival?.let { arrival ->
                val flights =
                    Flight.searchFlights(requireContext(), departure, arrival, selectedDate)
                flights.forEach { flight ->
                    addFlightDetailsView(false, flight.price.toInt())
                    addFlightDetailsView(true, flight.price.toInt())
                }
            }
        } ?: run {
            // Handle the case where either departure or arrival is null
            // Maybe show a message to the user or log an error
            Log.e("FlightSearch", "Departure or arrival is not selected.")
        }
    }


    private fun addFlightDetailsView(includeRandomAirport: Boolean, price: Int) {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.flight_info_section, layoutContainer, false)

        // Using local constants to hold the values safely for smart casting
        val safeSelectedDeparture = selectedDeparture
        val safeSelectedArrival = selectedArrival

        if (safeSelectedDeparture != null && safeSelectedArrival != null) {
            val flight = Flight.searchFlights(
                requireContext(),
                safeSelectedDeparture,
                safeSelectedArrival,
                selectedDate
            ).firstOrNull()
            flight?.let {
                view.findViewById<TextView>(R.id.textViewDepartureCity).text =
                    Airport.getAiportName(safeSelectedDeparture)
                view.findViewById<TextView>(R.id.textViewDepartureCode).text =
                    Airport.getAiportCode(safeSelectedDeparture)
                view.findViewById<TextView>(R.id.textViewArrivalCity).text =
                    Airport.getAiportName(safeSelectedArrival)
                view.findViewById<TextView>(R.id.textViewArrivalCode).text =
                    Airport.getAiportCode(safeSelectedArrival)
                view.findViewById<TextView>(R.id.textViewDate).text = selectedDate
                view.findViewById<TextView>(R.id.textViewPrice).text = "Price: $${it.price}"
            }

            // Handle random airport inclusion if needed
            var randomAirport: Airport? = null
            if (includeRandomAirport) {
                randomAirport = Airport.getRandomAirport(
                    requireContext(),
                    safeSelectedDeparture,
                    safeSelectedArrival
                )
                randomAirport?.let {
                    view.findViewById<TextView>(R.id.textViewDate).text =
                        Airport.getAiportName(randomAirport)
                }
            }

            view.findViewById<Button>(R.id.proceedToPaymentButton).setOnClickListener {
                proceedToPayment(safeSelectedDeparture, safeSelectedArrival, price, randomAirport)
            }
        } else {
            // Inform the user that selection is incomplete
            Toast.makeText(context, "Both departure and arrival airports must be selected.", Toast.LENGTH_SHORT).show()
        }

        layoutContainer.addView(view)
    }



    private fun showDatePickerDialog(view: View) {
        val calendar = Calendar.getInstance()
        DatePickerDialog(requireContext(), { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val sdf = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault())
            selectedDate = sdf.format(calendar.time)
            view.findViewById<TextView>(R.id.tvSelectDate).text = selectedDate
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
    }




    private fun initializeDate(view: View) {
        val currentDate = Calendar.getInstance()
        val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        selectedDate = sdf.format(currentDate.time)
        view.findViewById<TextView>(R.id.tvSelectDate).text = selectedDate
    }


    private fun proceedToPayment(departure: Airport?, arrival: Airport?, price: Int, randomAirport: Airport?) {
        val intent = Intent(context, PaymentActivity::class.java).apply {
            putExtra("departureCity", departure?.name)
            putExtra("arrivalCity", arrival?.name)
            putExtra("price", price)
            putExtra("date", selectedDate)
            putExtra("randomStop", randomAirport?.name)
            putExtra("seatNumber", (1..150).shuffled().first().toString())  // Generating a random seat number
        }
        startActivity(intent)
    }

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }
}
