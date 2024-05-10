package com.example.jiro.payment

import android.content.Context

interface PaymentObserver {
    fun update(paymentSuccessful: Boolean)
}

class PaymentProcessor(context: Context) {
    private val paymentFactory = PaymentFactory(context)
    private val observers = mutableListOf<PaymentObserver>()

    fun processPayment(methodType: String, amount: Double): Boolean {
        val paymentMethod = paymentFactory.getPaymentMethod(methodType)
        val result = paymentMethod.pay(amount)
        notifyObservers(result)
        return result
    }

    fun addObserver(observer: PaymentObserver) {
        observers.add(observer)
    }

    private fun notifyObservers(result: Boolean) {
        observers.forEach {
            it.update(result)
        }
    }
}


class PaymentStatusLogger : PaymentObserver {
    override fun update(paymentSuccessful: Boolean) {
        if (paymentSuccessful) {
            println("Payment succeeded")
        } else {
            println("Payment failed")
        }
    }
}



