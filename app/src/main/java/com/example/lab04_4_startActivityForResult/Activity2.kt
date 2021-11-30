package com.example.lab04_4_startActivityForResult

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView

class Activity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_2)

        val btnToActivity1 = findViewById<View>(R.id.bnToFirst)
        btnToActivity1.setOnClickListener { finish() }

        val btnToActivity3 = findViewById<View>(R.id.bnToThird)
        btnToActivity3.setOnClickListener {
            startActivityForResult(Intent(applicationContext, Activity3::class.java), 0)
        }

        val navBottom : BottomNavigationView = findViewById<View>(R.id.nav_view) as BottomNavigationView
        navBottom.setOnNavigationItemReselectedListener {
            startActivity(Intent(applicationContext, ActivityAbout::class.java))
        }

        //Включаем кнопку "Назад" (Navigation Up) в верхней строке приложения
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 1) { finish() }
    }
}