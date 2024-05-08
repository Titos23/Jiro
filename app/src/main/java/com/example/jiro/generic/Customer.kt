package com.example.jiro.generic

class Customer (
    private val cardHolderName: String,
    private val cardNumber: String,
    private val expiryDate: String,
    private val cvv: String,
    private val email: String,
    private val password: String
){


    fun getCardNumber() : String{
        return cardNumber
    }

    fun getExpiryDate() : String{
        return expiryDate
    }

    fun getCvv() : String{
        return cvv
    }

    fun getCardHolderName() : String{
        return cardNumber
    }



}