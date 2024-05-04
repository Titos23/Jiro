package com.example.jiro

class Airport(val name: String, val iataCode: String, val country: String) {
    override fun toString(): String {
        return "$name ($iataCode), $country"
    }
}
