package com.example.jiro.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.jiro.R
import com.example.jiro.helpers.ConnectionHelper

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var connectButton: Button
    private lateinit var clearButton: Button
    private lateinit var signUpTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        connectButton = findViewById(R.id.connectButton)
        clearButton = findViewById(R.id.clearButton)
        signUpTextView = findViewById(R.id.signUpTextView)

        connectButton.setOnClickListener {
            val intent = Intent(this, FragmentActivity::class.java)
            loginUser(emailEditText.text.toString(), passwordEditText.text.toString())
            startActivity(intent)
        }

        clearButton.setOnClickListener {
            // Clear fields
            emailEditText.text.clear()
            passwordEditText.text.clear()
        }

        signUpTextView.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser(username: String, password: String) {
        val dbHelper = ConnectionHelper(this)
        if (dbHelper.checkUser(username, password)) {
            Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            Toast.makeText(this, "Login failed. Invalid username or password", Toast.LENGTH_SHORT).show()
        }
    }



}
