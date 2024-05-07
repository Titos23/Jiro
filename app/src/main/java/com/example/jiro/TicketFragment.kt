package com.example.jiro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class TicketFragment : Fragment() {

    private var passengerName: String? = null
    private var departureCity: String? = null
    private var arrivalCity: String? = null
    private var flightDate: String? = null
    private var randomStop: String? = null
    private var seatNumber: String? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            passengerName = it.getString("passengerName")
            departureCity = it.getString("departureCity")
            arrivalCity = it.getString("arrivalCity")
            flightDate = it.getString("date")
            randomStop = it.getString("randomStop")
            seatNumber = it.getString("seatNumber")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_ticket, container, false)

        // Set the flight details
        view.findViewById<TextView>(R.id.tvPassengerName).text = "Passenger: $passengerName"
        view.findViewById<TextView>(R.id.tvFlightDepartureCity).text = "Departure City: $departureCity"
        view.findViewById<TextView>(R.id.tvFlightArrivalCity).text = "Arrival City: $arrivalCity"
        view.findViewById<TextView>(R.id.tvFlightDate).text = "Flight Date: $flightDate"
        view.findViewById<TextView>(R.id.tvRandomStop).text = "Random Stop: $randomStop"
        view.findViewById<TextView>(R.id.tvSeatNumber).text = "Seat Number: $seatNumber"

        return view
    }

    companion object {
        fun newInstance(passengerName: String, departureCity: String, arrivalCity: String, date: String, randomStop: String, seatNumber: String): TicketFragment {
            val args = Bundle().apply {
                putString("passengerName", passengerName)
                putString("departureCity", departureCity)
                putString("arrivalCity", arrivalCity)
                putString("date", date)
                putString("randomStop", randomStop)
                putString("seatNumber", seatNumber)
            }
            return TicketFragment().apply {
                arguments = args
            }
        }
    }
}
