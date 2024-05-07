package com.example.jiro

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment

class FragmentActivity : AppCompatActivity() {
    private lateinit var homeFragment: HomeFragment
    private lateinit var ticketsFragment: TicketFragment
    private var currentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)

        // Initialize fragments and setup the BottomNavigationView
        setupFragments()
        setupBottomNavigationView()
    }

    private fun setupFragments() {
        homeFragment = HomeFragment.newInstance()
        ticketsFragment = TicketFragment.newInstance()

        // Initially load the home fragment
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, homeFragment, "Home")
            .commit()
        currentFragment = homeFragment
    }

    private fun setupBottomNavigationView() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    switchFragment(homeFragment, "Home")
                    true
                }
                R.id.navigation_tickets -> {
                    switchFragment(ticketsFragment, "Tickets")
                    true
                }
                else -> false
            }
        }
        // Set initial selected item, if required.
        bottomNavigationView.selectedItemId = R.id.navigation_home
    }

    private fun switchFragment(fragment: Fragment, tag: String) {
        if (fragment != currentFragment) {
            val transaction = supportFragmentManager.beginTransaction()
            // Hide the current fragment
            currentFragment?.let {
                transaction.hide(it)
            }

            // Show the new fragment or add it if it's not added
            if (fragment.isAdded) {
                transaction.show(fragment)
            } else {
                transaction.add(R.id.fragment_container, fragment, tag)
            }

            // Commit the transaction
            transaction.commit()
            currentFragment = fragment
        }
    }
}
