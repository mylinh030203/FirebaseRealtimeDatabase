package com.example.firebaserealtimedatabase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnInsert.setOnClickListener {
            var intent = Intent(this, InsertionActivity::class.java)
            startActivity(intent)
        }
        btnfetch.setOnClickListener {
            var intent = Intent(this, FetchingActivity::class.java)
            startActivity(intent)
        }
    }
}