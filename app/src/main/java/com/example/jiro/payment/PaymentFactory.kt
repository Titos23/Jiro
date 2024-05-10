package com.example.jiro.payment

class PaymentFactory {
    fun getPaymentMethod(methodType: String): PaymentMethod {
        return when (methodType) {
            "CreditCard" -> CreditCardPayment()
            "DebitCard" -> DebitCardPayment()
            else -> throw IllegalArgumentException("Invalid payment method type")
        }
    }
}
