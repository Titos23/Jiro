
package com.example.jiro.payment

class DebitCardPayment : PaymentMethod {
    override fun pay(amount: Double): Boolean {
        println("Processing Debit Card payment for $$amount")
        return true // Simulate successful payment
    }
}
