package com.example.jiro.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.jiro.R

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
            val intent = Intent(this, FragmentActivity::class.java)
            startActivity(intent)
        }

    }
}
