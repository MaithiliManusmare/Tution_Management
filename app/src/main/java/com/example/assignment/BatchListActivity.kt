package com.example.assignment

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class BatchListActivity : AppCompatActivity() {
    lateinit var courseButton : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_batch_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initializeIds()
        onClickListeners()
    }

    private fun onClickListeners() {
        courseButton.setOnClickListener {
            val intent = Intent(this, BatchRegistrationActivity::class.java)
            startActivity(intent)
        }

    }

    private fun initializeIds() {
        courseButton = findViewById(R.id.courseButton)
    }
}