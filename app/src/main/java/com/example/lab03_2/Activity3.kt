package com.example.lab03_2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView

class Activity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_3)

        val btnToActivity2 = findViewById<View>(R.id.btn2second)
        btnToActivity2.setOnClickListener { finish() }

        val btnToActivity1 = findViewById<View>(R.id.btn2first)
        btnToActivity1.setOnClickListener {
            setResult(1)
            finish()
        }

        val navBottom : BottomNavigationView = findViewById<View>(R.id.nav_bottom) as BottomNavigationView
        navBottom.setOnNavigationItemReselectedListener {
            startActivity(Intent(applicationContext, ActivityAbout::class.java))
        }
    }
}