package com.example.jiro

class Business : SeatClass("Business") {
    override fun comfortLevel() = "High"
    override fun amenities() = listOf("Extra Leg Room", "Priority Boarding", "Free Meals")
}
