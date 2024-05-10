package com.example.jiro.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.jiro.R
import com.example.jiro.helpers.ConnectionHelper

class SignupActivity : AppCompatActivity() {

    private lateinit var signemailEditText: EditText
    private lateinit var signpasswordEditText: EditText
    private lateinit var submitButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        signemailEditText = findViewById(R.id.signupmail)
        signpasswordEditText = findViewById(R.id.signupassword)
        submitButton = findViewById(R.id.submitBtn)


        submitButton.setOnClickListener {
            registerUser(signemailEditText.text.toString(), signpasswordEditText.text.toString())
            val intent = Intent(this, FragmentActivity::class.java)
            startActivity(intent)
        }

    }

    fun registerUser(username: String, password: String): Boolean {
        val dbHelper = ConnectionHelper(this)
        if (dbHelper.addUser(username, password)) {
            Toast.makeText(this, "User registered successfully!", Toast.LENGTH_SHORT).show()
            return true
        } else {
            Toast.makeText(this, "Registration failed or user already exists", Toast.LENGTH_SHORT).show()
            return false
        }
    }
}
