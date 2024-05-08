package com.example.jiro.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jiro.R
import com.example.jiro.Ticket


class TicketAdapter(private var tickets: MutableList<Ticket>, private val onDelete: (Ticket) -> Unit) : RecyclerView.Adapter<TicketAdapter.TicketViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ticket_item, parent, false)
        return TicketViewHolder(view, onDelete)
    }

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        holder.bind(tickets[position])
    }

    override fun getItemCount() = tickets.size

    class TicketViewHolder(itemView: View, private val onDelete: (Ticket) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val passengerName: TextView = itemView.findViewById(R.id.tvPassengerName)
        private val deleteButton: Button = itemView.findViewById(R.id.btnDelete)

        fun bind(ticket: Ticket) {
            passengerName.text = ticket.passengerName
            deleteButton.setOnClickListener { onDelete(ticket) }
        }
    }

    // Public methods to modify the tickets list
    fun addTicket(ticket: Ticket) {
        tickets.add(ticket)
        notifyItemInserted(tickets.size - 1)
    }

    fun removeTicket(ticket: Ticket) {
        val index = tickets.indexOf(ticket)
        if (index != -1) {
            tickets.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun updateTickets(newTickets: List<Ticket>) {
        tickets.clear()
        tickets.addAll(newTickets)
        notifyDataSetChanged()
    }
}
