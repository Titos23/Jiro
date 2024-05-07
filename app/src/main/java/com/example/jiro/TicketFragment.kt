package com.example.jiro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jiro.adapters.TicketAdapter



class TicketFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var ticketAdapter: TicketAdapter
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_ticket, container, false)

        // Initialize database helper
        dbHelper = DatabaseHelper(view.context)

        // Setup RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        loadTickets()

        return view
    }

    private fun loadTickets() {
        val tickets = dbHelper.getAllTickets()
        ticketAdapter = TicketAdapter(tickets)
        recyclerView.adapter = ticketAdapter
    }

    companion object {
        fun newInstance(): TicketFragment = TicketFragment()
    }
}
