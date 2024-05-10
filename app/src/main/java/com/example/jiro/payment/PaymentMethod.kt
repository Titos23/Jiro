package com.example.jiro.payment

import android.content.Context
import android.widget.Toast

interface PaymentMethod {
    fun pay(amount: Double): Boolean
}

class CreditCardPayment(private val context: Context) : PaymentMethod {
    override fun pay(amount: Double): Boolean {
        Toast.makeText(context, "The sum of $$amount was paid using your credit card", Toast.LENGTH_LONG).show()
        return true // Simulate a successful payment
    }
}

class DebitCardPayment(private val context: Context) : PaymentMethod {
    override fun pay(amount: Double): Boolean {
        Toast.makeText(context, "The sum of $$amount was paid using your debit card", Toast.LENGTH_LONG).show()
        return true // Simulate a successful payment
    }
}
