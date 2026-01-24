package com.example.assignment

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class BatchListActivity : AppCompatActivity() {
    lateinit var courseButton : Button
    lateinit var batchAdapter : BatchListAdapter
    lateinit var viewModel : BatchViewmodel
    lateinit var recyclerView : RecyclerView
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
        initializeViewModel()
        setAdapter()
        onClickListeners()
    }
    private fun initializeViewModel() {
        val dao = StudentDatabase.getDatabase(applicationContext).batchDao()
        val studentDao = StudentDatabase.getDatabase(applicationContext).studentDao()
        val repository = BatchRepository(dao)
        val studentRepository = StudentRepository(studentDao)
        val factory = BatchViewModelFactory(repository,studentRepository)
        viewModel = ViewModelProvider(this, factory)[BatchViewmodel::class.java]
    }
    private fun setAdapter() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        batchAdapter = BatchListAdapter()
        recyclerView.adapter = batchAdapter
        observeStudents()
    }

    private fun observeStudents() {
        val studentCount : Int = viewModel.getStudentCount()

        lifecycleScope.launch {
            viewModel.allBatch.collect { batchList ->
                batchAdapter.setData(batchList)
            }
        }
    }

    private fun onClickListeners() {
        courseButton.setOnClickListener {
            val intent = Intent(this, BatchRegistrationActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initializeIds() {
        courseButton = findViewById(R.id.courseButton)
        recyclerView = findViewById(R.id.recyclerView)
    }
}