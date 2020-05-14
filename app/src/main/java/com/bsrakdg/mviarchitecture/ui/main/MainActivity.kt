package com.bsrakdg.mviarchitecture.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bsrakdg.mviarchitecture.R

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showMainFragment()
    }

    private fun showMainFragment() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container,
                MainFragment(), "Main Fragment")
            .commit()
    }
}
