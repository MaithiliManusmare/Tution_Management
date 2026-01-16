package com.example.assignment

import android.app.TimePickerDialog
import android.graphics.Typeface
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import java.util.Calendar

class BatchRegistrationActivity : AppCompatActivity() {
    lateinit var nameEditText : EditText
    lateinit var enrolledSubjectsEditText : EditText
    lateinit var enrollStudentsEditText : EditText
    lateinit var startTimeEt: TextInputEditText
    lateinit var endTimeEt: TextInputEditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_batch_registration)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        generateIds()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        val enrolledSubjectList : List<String> = listOf("All Subjects")

        enrolledSubjectsEditText.setOnClickListener{
            showSelectionPopup("Select Subject",enrolledSubjectList, enrolledSubjectsEditText);
        }
        enrollStudentsEditText.setOnClickListener{
            showSelectionPopup("Select Students",enrolledSubjectList, enrollStudentsEditText);
        }
        startTimeEt.setOnClickListener {
            showTimePicker(startTimeEt)
        }
        endTimeEt.setOnClickListener {
            showTimePicker(endTimeEt)
        }

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