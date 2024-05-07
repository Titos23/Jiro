package com.example.jiro

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class FragmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Set default fragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment.newInstance())
                .commitNow()
            bottomNavigationView.selectedItemId = R.id.navigation_home
        }

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    val fragment = HomeFragment.newInstance()
                    loadFragment(fragment)
                    true
                }
                R.id.navigation_tickets -> {
                    val fragment = TicketFragment.newInstance(
                        passengerName = "Sample Name", // These values should be dynamically set
                        departureCity = "City A",
                        arrivalCity = "City B",
                        date = "2021-12-15",
                        randomStop = "City C",
                        seatNumber = "12A"
                    )
                    loadFragment(fragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        // Replace the current fragment with the new fragment and add the transaction to back stack
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
