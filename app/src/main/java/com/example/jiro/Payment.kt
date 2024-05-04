package com.example.jiro

interface Payment {
    fun pay(amount: Double)
    fun refund(amount: Double)
}
