package com.example.assignment

import StudentViewModel
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class StudentListActivity : AppCompatActivity() {
    lateinit var button: TextView
    private lateinit var viewModel: StudentViewModel
    private lateinit var vehicleAdapter: VehicleAdapter
    private lateinit var filterTv: TextView
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_student_list)
        generateIds()
        onClickListeners()
        setAdapter()
        initializeViewModel()

    }
    private fun initializeViewModel() {
        val vehicleDao = StudentDatabase.getDatabase(applicationContext).studentDao()
        val repository = StudentRepository(vehicleDao)
        val factory = viewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[StudentViewModel::class.java]
    }

    private fun setAdapter() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        vehicleAdapter = VehicleAdapter()
        recyclerView.adapter = vehicleAdapter
    }
    private fun generateIds() {
        button = findViewById(R.id.addButton)
        recyclerView = findViewById(R.id.recyclerView)
    }

    private fun onClickListeners() {
        button.setOnClickListener {
            val intent = Intent(this, RegistrationFormActivity::class.java)
            startActivity(intent)
        }
    }
}

