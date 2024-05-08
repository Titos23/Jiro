// src/main/java/com/example/jiro/payment/Payment.kt
package com.example.jiro.payment

import com.example.jiro.Customer

class Payment(
    private val customer: Customer,
    private val amount: Double,
    private var paymentMethod: PaymentMethod // Default or injected method
) {
    fun setPaymentMethod(method: PaymentMethod) {
        paymentMethod = method
    }

    fun validatePaymentDetails(): Boolean {
        return customer.getCardNumber().length == 16 &&
                customer.getExpiryDate().isNotEmpty() &&
                customer.getCardHolderName().isNotEmpty() &&
                customer.getCvv().length == 3
    }

    fun processPayment(): PaymentResult {
        if (!validatePaymentDetails()) {
            return PaymentResult(success = false, message = "Invalid payment details")
        }
        val paymentSuccess = paymentMethod.pay(amount)
        return if (paymentSuccess) {
            PaymentResult(success = true, message = "Payment successful")
        } else {
            PaymentResult(success = false, message = "Payment failed")
        }
    }

    data class PaymentResult(val success: Boolean, val message: String)
}

