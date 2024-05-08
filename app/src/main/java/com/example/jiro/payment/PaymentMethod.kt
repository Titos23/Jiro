
package com.example.jiro.payment

interface PaymentMethod {
    fun pay(amount: Double): Boolean
}
