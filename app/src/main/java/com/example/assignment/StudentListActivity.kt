package com.example.assignment

import StudentViewModel
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class StudentListActivity : AppCompatActivity() {
    lateinit var button: TextView
    private lateinit var viewModel: StudentViewModel
    private lateinit var studentAdapter: StudentAdapter
    private lateinit var filterTv: TextView
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_student_list)
        generateIds()
        onClickListeners()
        initializeViewModel()
        setAdapter()
    }



    private fun observeStudents() {
        lifecycleScope.launch {
            viewModel.allStudents.collect { studentList ->
                studentAdapter.setData(studentList)
            }
        }
    }


    private fun initializeViewModel() {
        val studentDao = StudentDatabase.getDatabase(applicationContext).studentDao()
        val batchDao = StudentDatabase.getDatabase(applicationContext).batchDao()
        val repository = StudentRepository(studentDao)
        val batchRepository = BatchRepository(batchDao)
        val factory = StudentViewModelFactory(repository, batchRepository)
        viewModel = ViewModelProvider(this, factory)[StudentViewModel::class.java]
    }

    private fun setAdapter() {
        recyclerView.layoutManager = LinearLayoutManager(this)

        studentAdapter = StudentAdapter(
            onEditClick = { student ->
//                handleEditStudent(student)
            },
            onDeleteClick = { student ->
//                showDeleteConfirmation(student)
            }
        )

        recyclerView.adapter = studentAdapter
        observeStudents()
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

