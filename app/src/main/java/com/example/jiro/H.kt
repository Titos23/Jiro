/*

package com.example.jiro

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Calendar
import java.util.Locale
import android.util.Xml;
import org.xmlpull.v1.XmlPullParser;
import android.content.res.XmlResourceParser

class MainActivity2 : AppCompatActivity() {
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

        val departureSpinner: Spinner = findViewById(R.id.departure)
        val arrivalSpinner: Spinner = findViewById(R.id.arrival)
        val listItems = parseXML() // Parsing XML data
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listItems)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        departureSpinner.adapter = adapter
        arrivalSpinner.adapter = adapter



        setCurrentDateOnView()
        val tvSelectDate = findViewById<TextView>(R.id.tvSelectDate)
        tvSelectDate.setOnClickListener {
            showDatePickerDialog()
        }

        val optionSpinner: Spinner = findViewById(R.id.classcat)
        ArrayAdapter.createFromResource(
            this,
            R.array.options_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            optionSpinner.adapter = adapter
        }

        optionSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                // Handle item selection
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Handle no item selection
            }
        }
    }

    private fun setCurrentDateOnView() {
        val tvSelectDate = findViewById<TextView>(R.id.tvSelectDate)
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val dayOfWeek = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())
        val currentDate = String.format("%s, %d/%d/%d", dayOfWeek, day, month + 1, year)

        tvSelectDate.text = currentDate
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            // Set the calendar to the selected date
            calendar.set(Calendar.YEAR, selectedYear)
            calendar.set(Calendar.MONTH, selectedMonth)
            calendar.set(Calendar.DAY_OF_MONTH, selectedDay)

            // Retrieve the day of the week in a full textual format
            val dayOfWeek = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())
            // Format the date where the day of the week comes first
            val selectedDate = String.format("%s, %d/%d/%d", dayOfWeek, selectedDay, selectedMonth + 1, selectedYear)

            // Update the TextView with the selected date
            findViewById<TextView>(R.id.tvSelectDate).text = selectedDate
        }, year, month, day)

        // Optionally set a date range
        datePickerDialog.datePicker.minDate = calendar.timeInMillis
        // Set maximum date to one year from today
        calendar.add(Calendar.YEAR, 1)
        datePickerDialog.datePicker.maxDate = calendar.timeInMillis

        datePickerDialog.show()
    }

    private fun parseXML(): List<String> {
        val parser: XmlResourceParser = resources.getXml(R.xml.airports) // 'airports.xml' is the name of your XML file
        val items = mutableListOf<String>()
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
                        items.add("$name, $code, $countryName")
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
}

*/