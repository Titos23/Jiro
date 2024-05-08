package com.example.jiro.payment

class CreditCardPayment : PaymentMethod {
    override fun pay(amount: Double): Boolean {
        println("Processing credit card payment for $$amount")
        return true // Simulate successful payment
    }
}
