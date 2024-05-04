package com.example.jiro

class Economy : SeatClass("Economy") {
    override fun comfortLevel() = "Standard"
    override fun amenities() = listOf("Standard Leg Room")
}
