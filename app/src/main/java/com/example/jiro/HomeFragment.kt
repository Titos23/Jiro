/*package com.example.jiro

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
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

        initializeDate() // Initialize date on start
        findViewById<TextView>(R.id.tvSelectDate).setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun setupSpinners() {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listItems)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        departureSpinner.adapter = adapter
        arrivalSpinner.adapter = adapter

        val spinnerListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if (parent.id == R.id.departure) {
                    selectedDeparture = parent.getItemAtPosition(position) as Airport
                } else if (parent.id == R.id.arrival) {
                    selectedArrival = parent.getItemAtPosition(position) as Airport
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        departureSpinner.onItemSelectedListener = spinnerListener
        arrivalSpinner.onItemSelectedListener = spinnerListener
    }

    private fun addFlightInfoView() {
        layoutContainer.removeAllViews()  // Clear previous views
        addFlightDetailsView(includeRandomAirport = false, price = 500)  // More expensive without random airport
        addFlightDetailsView(includeRandomAirport = true, price = 300)   // Less expensive with random airport
    }

    private fun addFlightDetailsView(includeRandomAirport: Boolean, price: Int) {
        val inflater = LayoutInflater.from(this)
        val view = inflater.inflate(R.layout.flight_info_section, layoutContainer, false)

        view.findViewById<TextView>(R.id.textViewDepartureCity).text = selectedDeparture?.name ?: "Default Departure"
        view.findViewById<TextView>(R.id.textViewDepartureCode).text = selectedDeparture?.iataCode ?: "AAA"
        view.findViewById<TextView>(R.id.textViewArrivalCity).text = selectedArrival?.name ?: "Default Arrival"
        view.findViewById<TextView>(R.id.textViewArrivalCode).text = selectedArrival?.iataCode ?: "AMS"
        view.findViewById<TextView>(R.id.textViewDate).text = selectedDate
        view.findViewById<TextView>(R.id.textViewPrice).text = "Price: $$price"

        var randomAirport: Airport? = null
        if (includeRandomAirport) {
            randomAirport = getRandomAirport()
            randomAirport?.let {
                view.findViewById<TextView>(R.id.textViewDate).text = "Random Stop: ${it.name} (${it.country})"
            }
        }

        view.findViewById<Button>(R.id.proceedToPaymentButton).setOnClickListener {
            proceedToPayment(selectedDeparture, selectedArrival, price, randomAirport)
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
            calendar.set(year, month, dayOfMonth)
            val sdf = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault())
            val dateFlightFormatter = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
            selectedDate = dateFlightFormatter.format(calendar.time)
            findViewById<TextView>(R.id.tvSelectDate).text = sdf.format(calendar.time)
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
                    if (name != null) {
                        items.add(Airport(name, code.toString(), countryName.toString()))
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

    private fun initializeDate() {
        // Set the initial date to the current date on load
        val currentDate = Calendar.getInstance()
        val dateFormatter = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        selectedDate = dateFormatter.format(currentDate.time)
        findViewById<TextView>(R.id.tvSelectDate).text = selectedDate
    }

    private fun proceedToPayment(departure: Airport?, arrival: Airport?, price: Int, randomAirport: Airport?) {
        val intent = Intent(this, PaymentActivity::class.java).apply {
            putExtra("departureCity", departure?.name)
            putExtra("arrivalCity", arrival?.name)
            putExtra("price", price)
            putExtra("date", selectedDate)
            putExtra("randomStop", randomAirport?.name)
            putExtra("seatNumber", (1..150).shuffled().first().toString())  // Generating a random seat number
        }
        startActivity(intent)
    }
}
*/

package com.example.jiro

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import java.io.IOException
import android.content.Intent
import android.util.Log
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
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.xmlpull.v1.XmlPullParserException



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
            addFlightInfoView()
        }

        departureSpinner = view.findViewById(R.id.departure)
        arrivalSpinner = view.findViewById(R.id.arrival)
        listItems = parseXML()  // Parsing XML data
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

    private fun addFlightInfoView() {
        layoutContainer.removeAllViews()  // Clear previous views
        addFlightDetailsView(false, 500)  // Example: No random airport and high price
        addFlightDetailsView(true, 300)   // Example: With random airport and lower price
    }

    private fun addFlightDetailsView(includeRandomAirport: Boolean, price: Int) {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.flight_info_section, layoutContainer, false)

        // Set flight details
        view.findViewById<TextView>(R.id.textViewDepartureCity).text = selectedDeparture?.name ?: "Default Departure"
        view.findViewById<TextView>(R.id.textViewDepartureCode).text = selectedDeparture?.iataCode ?: "AAA"
        view.findViewById<TextView>(R.id.textViewArrivalCity).text = selectedArrival?.name ?: "Default Arrival"
        view.findViewById<TextView>(R.id.textViewArrivalCode).text = selectedArrival?.iataCode ?: "AMS"
        view.findViewById<TextView>(R.id.textViewDate).text = selectedDate
        view.findViewById<TextView>(R.id.textViewPrice).text = "Price: $$price"

        // Handle random airport inclusion
        var randomAirport: Airport? = null
        if (includeRandomAirport) {
            randomAirport = getRandomAirport()
            randomAirport?.let {
                view.findViewById<TextView>(R.id.textViewDate).text = "Random Stop: ${it.name} (${it.country})"
            }
        }

        view.findViewById<Button>(R.id.proceedToPaymentButton).setOnClickListener {
            proceedToPayment(selectedDeparture, selectedArrival, price, randomAirport)
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
        } catch (e: XmlPullParserException) {
            Log.e("HomeFragment", "Error parsing XML", e)
            throw RuntimeException("Failed to parse XML resource", e)
        } catch (e: IOException) {
            Log.e("HomeFragment", "IO error during XML parsing", e)
            throw RuntimeException("IO error while accessing XML resource", e)
        } finally {
            parser.close()
        }
        return items
    }


    private fun initializeDate(view: View) {
        val currentDate = Calendar.getInstance()
        val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        selectedDate = sdf.format(currentDate.time)
        view.findViewById<TextView>(R.id.tvSelectDate).text = selectedDate
    }

    private fun getRandomAirport(): Airport? {
        val excludedAirports = listOfNotNull(selectedDeparture, selectedArrival)
        return listItems.filterNot { it in excludedAirports }.shuffled().firstOrNull()
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