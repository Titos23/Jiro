package com.example.jiro
import android.annotation.SuppressLint
import java.util.UUID
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_NAME = "tickets.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "tickets"
        const val COLUMN_ID = "id"
        const val COLUMN_PASSENGER_NAME = "passengerName"
        const val COLUMN_DEPARTURE_CITY = "departureCity"
        const val COLUMN_ARRIVAL_CITY = "arrivalCity"
        const val COLUMN_DATE = "date"
        const val COLUMN_RANDOM_STOP = "randomStop"
        const val COLUMN_SEAT_NUMBER = "seatNumber"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE_STATEMENT = """
        CREATE TABLE $TABLE_NAME (
            $COLUMN_ID TEXT PRIMARY KEY,
            $COLUMN_PASSENGER_NAME TEXT,
            $COLUMN_DEPARTURE_CITY TEXT,
            $COLUMN_ARRIVAL_CITY TEXT,
            $COLUMN_DATE TEXT,
            $COLUMN_RANDOM_STOP TEXT,
            $COLUMN_SEAT_NUMBER TEXT
        )
    """.trimIndent()
        db.execSQL(CREATE_TABLE_STATEMENT)
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addTicket(ticket: Ticket): String {
        val db = this.writableDatabase
        val ticketId = ticket.id ?: UUID.randomUUID().toString() // Generate UUID if not provided

        val values = ContentValues().apply {
            put(COLUMN_ID, ticketId)
            put(COLUMN_PASSENGER_NAME, ticket.passengerName)
            put(COLUMN_DEPARTURE_CITY, ticket.departureCity)
            put(COLUMN_ARRIVAL_CITY, ticket.arrivalCity)
            put(COLUMN_DATE, ticket.date)
            put(COLUMN_RANDOM_STOP, ticket.randomStop)
            put(COLUMN_SEAT_NUMBER, ticket.seatNumber)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
        return ticketId  // Return the UUID of the new or existing ticket
    }


    fun getTicket(ticketId: String): Ticket? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_NAME, null, "$COLUMN_ID = ?",
            arrayOf(ticketId), null, null, null
        )
        var ticket: Ticket? = null
        if (cursor.moveToFirst()) {
            ticket = Ticket(
                id = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                passengerName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSENGER_NAME)),
                departureCity = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DEPARTURE_CITY)),
                arrivalCity = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ARRIVAL_CITY)),
                date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE)),
                randomStop = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RANDOM_STOP)),
                seatNumber = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SEAT_NUMBER))
            )
        }
        cursor.close()
        db.close()
        return ticket
    }

    @SuppressLint("Range")
    fun getAllTickets(): List<Ticket> {
        val tickets = mutableListOf<Ticket>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        if (cursor.moveToFirst()) {
            do {
                val ticket = Ticket(
                    cursor.getString(cursor.getColumnIndex(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_PASSENGER_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_DEPARTURE_CITY)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_ARRIVAL_CITY)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_DATE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_RANDOM_STOP)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_SEAT_NUMBER))
                )
                tickets.add(ticket)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return tickets
    }




}
