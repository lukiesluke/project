package com.example.mksob.itunesandroidapplication

import android.annotation.SuppressLint
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class MainActivity : AppCompatActivity() {

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.main_container, TrackFragment()).commit()

    }

    /** Shows the project detail fragment  */
    fun show(int : Int) {
        val trackDetailsFragment = TrackDetailsFragment.forProject(int)
        supportFragmentManager
                .beginTransaction()
                .addToBackStack("project")
                .replace(R.id.main_container,
                        trackDetailsFragment, null).commit()
    }
}
