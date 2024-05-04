package com.example.jiro

abstract class SeatClass(val name: String) {
    abstract fun comfortLevel(): String
    abstract fun amenities(): List<String>
}
