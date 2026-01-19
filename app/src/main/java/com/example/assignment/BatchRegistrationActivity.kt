package com.example.assignment

import android.app.TimePickerDialog
import android.graphics.Typeface
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class BatchRegistrationActivity : AppCompatActivity() {
    lateinit var nameEditText : EditText
    lateinit var enrolledSubjectsEditText : EditText
    lateinit var enrollStudentsEditText : EditText
    lateinit var startTimeEt: TextInputEditText
    lateinit var endTimeEt: TextInputEditText
    lateinit var addButtonLL: LinearLayout
    lateinit var viewModel: BatchViewmodel
    private lateinit var db: StudentDatabase
    private lateinit var dao: BatchDao
    private lateinit var repository: BatchRepository
    private lateinit var factory: BatchViewModelFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_batch_registration)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        createObject()
        generateIds()
        setOnClickListeners()
    }

    private fun createObject() {
        db = StudentDatabase.getDatabase(this)
        dao = db.batchDao()
        repository = BatchRepository(dao)
        val studentRepository = StudentRepository(db.studentDao())
        factory = BatchViewModelFactory(repository, studentRepository)
        viewModel = ViewModelProvider(this, factory).get(BatchViewmodel::class.java)
    }


    private fun setOnClickListeners() {
        val enrolledSubjectList : List<String> = listOf("All Subjects")

        enrolledSubjectsEditText.setOnClickListener{
            showSelectionPopup("Select Subject",enrolledSubjectList, enrolledSubjectsEditText)
        }

        enrollStudentsEditText.setOnClickListener{
            lifecycleScope.launch {
                viewModel.allStudents.collect { studentList ->
                     val studentNameList: List<String> = studentList.map { it.name }
                    showSelectionPopup("Select Students",studentNameList, enrollStudentsEditText)
                }
            }
        }
        startTimeEt.setOnClickListener {
            showTimePicker(startTimeEt)
        }
        endTimeEt.setOnClickListener {
            showTimePicker(endTimeEt)
        }
        addButtonLL.setOnClickListener {
            saveBatch()
        }
    }

    private fun saveBatch() {
        try {
            val name = nameEditText.text.toString().trim()
            val subjects = enrollStudentsEditText.text.toString().trim()
            val startTimeText = startTimeEt.text.toString().trim()
            val endTimeText = endTimeEt.text.toString().trim()

            if (name.isEmpty() || subjects.isEmpty() ||
                startTimeText.isEmpty() || endTimeText.isEmpty()
            ) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return
            }

            val batch = Batch(
                name = name,
                subjects = subjects,
                startTime = parseTimeToMillis(startTimeText),
                endTime = parseTimeToMillis(endTimeText)
            )

            viewModel.insert(batch)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun parseTimeToMillis(time: String): Long {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.parse(time)?.time ?: 0L
    }

    private fun showTimePicker(editText: TextInputEditText) {
        val calendar = Calendar.getInstance()

        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        TimePickerDialog(
            this,
            { _, selectedHour, selectedMinute ->
                val formattedTime = String.format(
                    "%02d:%02d %s",
                    if (selectedHour > 12) selectedHour - 12 else selectedHour,
                    selectedMinute,
                    if (selectedHour >= 12) "PM" else "AM"
                )
                editText.setText(formattedTime)
            },
            hour,
            minute,
            false
        ).show()
    }

    private fun generateIds() {
        nameEditText = findViewById(R.id.nameEditText)
        enrolledSubjectsEditText = findViewById(R.id.enrolledSubjectsEditText)
        enrollStudentsEditText = findViewById(R.id.enrollStudentsEditText)
        startTimeEt = findViewById(R.id.startTimeEt)
        endTimeEt = findViewById(R.id.endTimeEt)
        addButtonLL = findViewById(R.id.addButtonLL)
    }

    private fun showSelectionPopup(
        title: String,
        items: List<String>,
        targetEditText: EditText,
    ) {
        val dialog = AlertDialog.Builder(this)
            .create()

        val inflater = layoutInflater
        val view = inflater.inflate(R.layout.dialog_items_list, null)
        dialog.setView(view)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewBrands)
        val dialogTitle = view.findViewById<TextView>(R.id.dialogTitle)
        val closeButton = view.findViewById<ImageButton>(R.id.closeButton)

        dialogTitle.setText(title)

        closeButton.setOnClickListener {
            dialog.dismiss()
        }

        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = SelectOptionsAdapter(items,) { selectedItem ->
            targetEditText.setText(selectedItem)
            targetEditText.setTextColor(ContextCompat.getColor(this, R.color.black))
            targetEditText.setTypeface(null, Typeface.BOLD)
            targetEditText.textSize = 14f
            dialog.dismiss()
        }

        recyclerView.adapter = adapter
        dialog.show()
    }
}