package com.example.jiro.payment

import android.content.Context

class PaymentFactory(private val context: Context) {
    fun getPaymentMethod(methodType: String): PaymentMethod {
        return when (methodType) {
            "CreditCard" -> CreditCardPayment(context)
            "DebitCard" -> DebitCardPayment(context)
            else -> throw IllegalArgumentException("Invalid payment method type")
        }
    }
}
