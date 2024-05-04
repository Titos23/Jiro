package com.example.jiro

class Seat(val seatNumber: String, var isAvailable: Boolean, val seatClass: SeatClass) {
    fun reserveSeat() {
        if (isAvailable) {
            isAvailable = false
            println("Seat $seatNumber in ${seatClass.name} class reserved.")
        } else {
            println("Seat $seatNumber is already reserved.")
        }
    }

    fun releaseSeat() {
        isAvailable = true
        println("Seat $seatNumber released.")
    }

    fun displaySeatDetails() {
        println("Seat Number: $seatNumber, Class: ${seatClass.name}, Available: $isAvailable, Comfort: ${seatClass.comfortLevel()}, Amenities: ${seatClass.amenities().joinToString(", ")}")
    }
}
