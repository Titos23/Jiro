package com.example.jiro.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jiro.R
import com.example.jiro.Ticket
import com.example.jiro.DatabaseHelper
import com.example.jiro.HomeFragment
import com.example.jiro.TicketFragment

class TicketAdapter(private var tickets: List<Ticket>) : RecyclerView.Adapter<TicketAdapter.TicketViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ticket_item, parent, false)
        return TicketViewHolder(view)
    }

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        holder.bind(tickets[position])
    }

    override fun getItemCount() = tickets.size

    class TicketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val passengerName: TextView = itemView.findViewById(R.id.tvPassengerName)
        private val departureCity: TextView = itemView.findViewById(R.id.tvDepartureCity)
        private val arrivalCity: TextView = itemView.findViewById(R.id.tvArrivalCity)
        private val date: TextView = itemView.findViewById(R.id.tvDate)
        private val randomStop: TextView = itemView.findViewById(R.id.tvRandomStop)
        private val seatNumber: TextView = itemView.findViewById(R.id.tvSeatNumber)

        fun bind(ticket: Ticket) {
            passengerName.text = ticket.passengerName
            departureCity.text = ticket.departureCity
            arrivalCity.text = ticket.arrivalCity
            date.text = ticket.date
            randomStop.text = ticket.randomStop
            seatNumber.text = ticket.seatNumber
        }
    }

    // Method to update the tickets list and refresh the RecyclerView
    fun updateTickets(newTickets: List<Ticket>) {
        tickets = newTickets
        notifyDataSetChanged()
    }


}
