package com.example.jiro

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.UUID
import android.content.ContentValues

class ReservationHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_NAME = "reservations.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "reservations"
        const val COLUMN_ID = "id"
        const val COLUMN_PASSENGER_NAME = "passengerName"
        const val COLUMN_DEPARTURE_CITY = "departureCity"
        const val COLUMN_ARRIVAL_CITY = "arrivalCity"
        const val COLUMN_DATE = "date"
        const val COLUMN_RANDOM_STOP = "randomStop"
        const val COLUMN_SEAT_NUMBER = "seatNumber"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableStatement = """
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
        db.execSQL(createTableStatement)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addTicket(ticket: Ticket): String {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_ID, ticket.id)
            put(COLUMN_PASSENGER_NAME, ticket.passengerName)
            put(COLUMN_DEPARTURE_CITY, ticket.departureCity)
            put(COLUMN_ARRIVAL_CITY, ticket.arrivalCity)
            put(COLUMN_DATE, ticket.date)
            put(COLUMN_RANDOM_STOP, ticket.randomStop)
            put(COLUMN_SEAT_NUMBER, ticket.seatNumber)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
        return ticket.id
    }

    fun deleteTicket(ticketId: String) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(ticketId))
        db.close()
    }

    fun getTicket(ticketId: String): Ticket? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_NAME, null, "$COLUMN_ID = ?",
            arrayOf(ticketId), null, null, null
        )
        var ticket: Ticket? = null
        if (cursor.moveToFirst()) {
            val idIndex = cursor.getColumnIndex(COLUMN_ID)
            val passengerNameIndex = cursor.getColumnIndex(COLUMN_PASSENGER_NAME)
            val departureCityIndex = cursor.getColumnIndex(COLUMN_DEPARTURE_CITY)
            val arrivalCityIndex = cursor.getColumnIndex(COLUMN_ARRIVAL_CITY)
            val dateIndex = cursor.getColumnIndex(COLUMN_DATE)
            val randomStopIndex = cursor.getColumnIndex(COLUMN_RANDOM_STOP)
            val seatNumberIndex = cursor.getColumnIndex(COLUMN_SEAT_NUMBER)

            if (idIndex != -1 && passengerNameIndex != -1 && departureCityIndex != -1 &&
                arrivalCityIndex != -1 && dateIndex != -1 && randomStopIndex != -1 && seatNumberIndex != -1) {
                ticket = Ticket(
                    id = cursor.getString(idIndex),
                    passengerName = cursor.getString(passengerNameIndex),
                    departureCity = cursor.getString(departureCityIndex),
                    arrivalCity = cursor.getString(arrivalCityIndex),
                    date = cursor.getString(dateIndex),
                    randomStop = cursor.getString(randomStopIndex),
                    seatNumber = cursor.getString(seatNumberIndex)
                )
            }
        }
        cursor.close()
        db.close()
        return ticket
    }

    fun getAllTickets(): List<Ticket> {
        val tickets = mutableListOf<Ticket>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        while (cursor.moveToNext()) {
            val idIndex = cursor.getColumnIndex(COLUMN_ID)
            val passengerNameIndex = cursor.getColumnIndex(COLUMN_PASSENGER_NAME)
            val departureCityIndex = cursor.getColumnIndex(COLUMN_DEPARTURE_CITY)
            val arrivalCityIndex = cursor.getColumnIndex(COLUMN_ARRIVAL_CITY)
            val dateIndex = cursor.getColumnIndex(COLUMN_DATE)
            val randomStopIndex = cursor.getColumnIndex(COLUMN_RANDOM_STOP)
            val seatNumberIndex = cursor.getColumnIndex(COLUMN_SEAT_NUMBER)

            if (idIndex != -1 && passengerNameIndex != -1 && departureCityIndex != -1 &&
                arrivalCityIndex != -1 && dateIndex != -1 && randomStopIndex != -1 && seatNumberIndex != -1) {
                tickets.add(
                    Ticket(
                        id = cursor.getString(idIndex),
                        passengerName = cursor.getString(passengerNameIndex),
                        departureCity = cursor.getString(departureCityIndex),
                        arrivalCity = cursor.getString(arrivalCityIndex),
                        date = cursor.getString(dateIndex),
                        randomStop = cursor.getString(randomStopIndex),
                        seatNumber = cursor.getString(seatNumberIndex)
                    )
                )
            }
        }
        cursor.close()
        db.close()
        return tickets
    }
}
