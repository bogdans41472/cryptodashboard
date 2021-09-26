package com.bogdan.cryptodashboard.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bogdan.cryptodashboard.R
import com.bogdan.cryptodashboard.ui.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.content_frame, MainFragment(), MainFragment.FRAGMENT_TAG)
            .commit()
    }
}