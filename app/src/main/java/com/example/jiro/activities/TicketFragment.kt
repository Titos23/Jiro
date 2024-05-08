package com.example.jiro.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jiro.R
import com.example.jiro.generic.ReservationHelper
import com.example.jiro.generic.Ticket
import com.example.jiro.helpers.TicketAdapter


class TicketFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var ticketAdapter: TicketAdapter
    private lateinit var reservationHelper: ReservationHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_ticket, container, false)
        reservationHelper = ReservationHelper(view.context)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        setupAdapter()

        return view
    }

    override fun onResume() {
        super.onResume()
        refreshTickets()
    }

    private fun setupAdapter() {
        val tickets = reservationHelper.getAllTickets().toMutableList()
        ticketAdapter = TicketAdapter(tickets) { ticket ->
            deleteTicket(ticket)
        }
        recyclerView.adapter = ticketAdapter
    }

    private fun refreshTickets() {
        val newTickets = reservationHelper.getAllTickets()
        ticketAdapter.updateTickets(newTickets)
    }

    private fun deleteTicket(ticket: Ticket) {
        reservationHelper.deleteTicket(ticket.id)
        ticketAdapter.removeTicket(ticket)
    }

    companion object {
        fun newInstance(): TicketFragment = TicketFragment()
    }
}
