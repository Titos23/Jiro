package com.example.jiro

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import android.content.res.XmlResourceParser
import org.xmlpull.v1.XmlPullParser

class MainActivity2 : AppCompatActivity() {

    private lateinit var departureSpinner: Spinner
    private lateinit var arrivalSpinner: Spinner
    private var selectedDeparture: Airport? = null
    private var selectedArrival: Airport? = null
    private lateinit var listItems: List<Airport>
    private lateinit var layoutContainer: LinearLayout
    private lateinit var selectedDate: String  // For formatted date in the flight info section
    private lateinit var displayDate: String  // For display in TextView from DatePicker

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main2)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        layoutContainer = findViewById(R.id.flightContainer)
        findViewById<Button>(R.id.searchFlight).setOnClickListener {
            addFlightInfoView()
        }

        departureSpinner = findViewById(R.id.departure)
        arrivalSpinner = findViewById(R.id.arrival)
        listItems = parseXML() // Parsing XML data
        setupSpinners()

        setCurrentDateOnView()
        findViewById<TextView>(R.id.tvSelectDate).setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun setupSpinners() {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listItems)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        departureSpinner.adapter = adapter
        arrivalSpinner.adapter = adapter

        departureSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                selectedDeparture = parent.getItemAtPosition(position) as Airport
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                selectedDeparture = null
            }
        }

        arrivalSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                selectedArrival = parent.getItemAtPosition(position) as Airport
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                selectedArrival = null
            }
        }
    }

    private fun addFlightInfoView() {
        val inflater = LayoutInflater.from(this)
        layoutContainer.removeAllViews()  // Clear previous views
        val view = inflater.inflate(R.layout.flight_info_section, layoutContainer, false)

        view.findViewById<TextView>(R.id.textViewDepartureCity).text = selectedDeparture?.name ?: "Default Departure"
        view.findViewById<TextView>(R.id.textViewDepartureCode).text = selectedDeparture?.iataCode ?: "AAA"
        view.findViewById<TextView>(R.id.textViewArrivalCity).text = selectedArrival?.name ?: "Default Arrival"
        view.findViewById<TextView>(R.id.textViewArrivalCode).text = selectedArrival?.iataCode ?: "AMS"
        view.findViewById<TextView>(R.id.textViewDate).text = "Flight Date: $selectedDate"

        val randomAirport = getRandomAirport()
        randomAirport?.let {
            view.findViewById<TextView>(R.id.textViewDate).text = "${it.name}(${it.country})"
        }

        layoutContainer.addView(view)
    }

    private fun getRandomAirport(): Airport? {
        val excludedAirports = listOfNotNull(selectedDeparture, selectedArrival)
        return listItems.filterNot { it in excludedAirports }.shuffled().firstOrNull()
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        DatePickerDialog(this, { _, year, month, dayOfMonth ->
            val date = Calendar.getInstance()
            date.set(year, month, dayOfMonth)
            val dateFormatter = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault())
            val dateFlightFormatter = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
            displayDate = dateFormatter.format(date.time)
            selectedDate = dateFlightFormatter.format(date.time)
            findViewById<TextView>(R.id.tvSelectDate).text = displayDate
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun parseXML(): List<Airport> {
        val parser: XmlResourceParser = resources.getXml(R.xml.airports)
        val items = mutableListOf<Airport>()
        try {
            var eventType = parser.eventType
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG && "row" == parser.name) {
                    var name: String? = null
                    var code: String? = null
                    var countryName: String? = null
                    while (!(eventType == XmlPullParser.END_TAG && "row" == parser.name)) {
                        if (eventType == XmlPullParser.START_TAG) {
                            when (parser.name) {
                                "name" -> name = parser.nextText()
                                "code" -> code = parser.nextText()
                                "countryName" -> countryName = parser.nextText()
                            }
                        }
                        eventType = parser.next()
                    }
                    if (name != null && code != null && countryName != null) {
                        items.add(Airport(name, code, countryName))
                    }
                }
                eventType = parser.next()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            parser.close()
        }
        return items
    }

    private fun setCurrentDateOnView() {
        val currentDate = Calendar.getInstance()
        val formatter = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault())
        findViewById<TextView>(R.id.tvSelectDate).text = formatter.format(currentDate.time)
    }
}
