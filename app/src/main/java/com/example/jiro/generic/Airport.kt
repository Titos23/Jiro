package com.example.jiro.generic

import android.content.Context
import android.util.Log
import com.example.jiro.R
import org.xmlpull.v1.XmlPullParser

class Airport(val name: String, val iataCode: String, val country: String) {

    companion object {
        private var cachedAirports: List<Airport>? = null

        fun getAirports(context: Context): List<Airport> {
            if (cachedAirports == null) {
                val airports = mutableListOf<Airport>()
                val parser = context.resources.getXml(R.xml.airports)
                try {
                    var eventType = parser.eventType
                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        if (eventType == XmlPullParser.START_TAG && parser.name == "row") {
                            var name: String? = null
                            var code: String? = null
                            var countryName: String? = null
                            while (!(eventType == XmlPullParser.END_TAG && parser.name == "row")) {
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
                                airports.add(Airport(name, code, countryName))
                            }
                        }
                        eventType = parser.next()
                    }
                } catch (e: Exception) {
                    Log.e("Airport", "Error parsing airports", e)
                    throw RuntimeException("Error parsing airports", e)
                } finally {
                    parser.close()
                }
                cachedAirports = airports
            }
            return cachedAirports!!
        }


        fun getRandomAirport(context: Context, departure: Airport?, arrival: Airport?): Airport? {
            val airports = getAirports(context)
            val excludedAirports = listOf(departure, arrival)
            return airports.filterNot { it in excludedAirports }.shuffled().firstOrNull()
        }
        fun getAiportName(airport: Airport) :String {
            return airport.name
        }

        fun getAiportCode(airport: Airport) :String {
            return airport.iataCode
        }
    }
    override fun toString(): String {
        return "$name ($iataCode), $country"
    }




}
