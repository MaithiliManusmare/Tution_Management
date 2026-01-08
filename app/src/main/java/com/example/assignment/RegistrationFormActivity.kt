package com.example.assignment

import StudentViewModel
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import java.util.Calendar

class RegistrationFormActivity : AppCompatActivity() {
    private lateinit var studentNameEditText: TextInputEditText
    private lateinit var dateOfBirthEditText: TextInputEditText
    private lateinit var genderEditText: TextInputEditText
    private lateinit var gradeEditText: TextInputEditText
    private lateinit var boardEditText: TextInputEditText
    private lateinit var enrolledSubjectsEditText: TextInputEditText

    private lateinit var parentNameEditText: TextInputEditText

    private lateinit var parentMobNoEditText: TextInputEditText
    private lateinit var studentMobNoEditText: TextInputEditText
    private lateinit var batchNameEditText: TextInputEditText
    private lateinit var joiningDateEditText: TextInputEditText
    private lateinit var feeAmountEditText: TextInputEditText
    private lateinit var lastYearPercentEditText: TextInputEditText
    private lateinit var db: StudentDatabase
    private lateinit var dao: StudentDao
    private lateinit var viewModel: StudentViewModel
    private lateinit var repository: StudentRepository
    private lateinit var factory: viewModelFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registration_form)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        createObject()
        initIds()
        onClickListners()
    }

    private fun createObject() {
        db = StudentDatabase.getDatabase(this)
        dao = db.studentDao()
        repository = StudentRepository(dao)
        factory = viewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(StudentViewModel::class.java)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun onClickListners() {
        val addButton = findViewById<TextView>(R.id.addButtonTv)
        val backArrowIv = findViewById<ImageView>(R.id.backArrowIv)
        val genderList : List<String> = listOf("Male","Female","Other")
        val boardList : List<String> = listOf("State Board","CBSE","ICSE")
        val enrolledSubjectList : List<String> = listOf("All Subjects")
        val classList = mutableListOf<String>()

        for (i in 1..12) {
            classList.add(i.toString())
        }

        addButton.setOnClickListener {
            submitForm()
        }

        backArrowIv.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        dateOfBirthEditText.setOnClickListener {
            showDatePickerDialog(true)
        }

        joiningDateEditText.setOnClickListener {
            showDatePickerDialog(false)
        }

        genderEditText.setOnClickListener{
            showSelectionPopup("Choose Gender",genderList, genderEditText);
        }

        gradeEditText.setOnClickListener{
            showSelectionPopup("Choose Class/Grade",classList, gradeEditText);
        }

        boardEditText.setOnClickListener{
            showSelectionPopup("Choose Board",boardList, boardEditText);
        }

        enrolledSubjectsEditText.setOnClickListener{
            showSelectionPopup("Select Subject",enrolledSubjectList, enrolledSubjectsEditText);
        }

    }

    private fun showDatePickerDialog(isBirthDate : Boolean) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, month, date ->
                val formattedDate = String.format(
                    "%02d/%02d/%04d",
                    date,
                    month + 1,
                    selectedYear
                )

                (if (isBirthDate) dateOfBirthEditText else joiningDateEditText)
                    .setText(formattedDate)

            },
            year,
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.datePicker.findViewById<View>(
            resources.getIdentifier("android:id/day", null, null)
        )?.visibility = View.GONE

        datePickerDialog.datePicker.findViewById<View>(
            resources.getIdentifier("android:id/month", null, null)
        )?.visibility = View.GONE

        datePickerDialog.show()
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


    private fun submitForm() {
        val studentName = studentNameEditText.text?.toString()?.trim().orEmpty()
        val dateOfBirth = dateOfBirthEditText.text?.toString()?.trim().orEmpty()
        val gender = genderEditText.text?.toString()?.trim().orEmpty()
        val grade = gradeEditText.text?.toString()?.toIntOrNull() ?: 0
        val board = boardEditText.text?.toString()?.trim().orEmpty()
        val enrolledSubjects = enrolledSubjectsEditText.text?.toString()?.trim().orEmpty()
        val parentName = parentNameEditText.text?.toString()?.trim().orEmpty()
        val parentMobileNumber = parentMobNoEditText.text?.toString()?.trim().orEmpty()
        val studentMobileNumber = studentMobNoEditText.text?.toString()?.trim().orEmpty()
        val courseName = batchNameEditText.text?.toString()?.trim().orEmpty()
        val dateOfJoining = joiningDateEditText.text?.toString()?.trim().orEmpty()
        val feeAmount = feeAmountEditText.text?.toString()?.toIntOrNull() ?: 0
        val lastYearPercent = lastYearPercentEditText.text.toString()?.trim().orEmpty()

        val placeholders = listOf(
            getString(R.string.enter_vehicle_number),
            getString(R.string.enter_year),
            getString(R.string.enter_owner_name), getString(R.string.select_brand),
            getString(R.string.select_model),
            getString(R.string.select_model)
        )

        if (studentName.isEmpty() || placeholders.contains(studentName) ||
            gender.isEmpty() || placeholders.contains(gender) ||
            board.isEmpty() || placeholders.contains(board) ||
            enrolledSubjects.isEmpty() || placeholders.contains(enrolledSubjects)
        ) {
            Toast.makeText(this, getString(R.string.please_fill_in_all_fields), Toast.LENGTH_SHORT).show()
            return
        }

        val student = Student(
            grade = grade,
            board = board,
            subjects = enrolledSubjects,
            name = studentName,
            dob = dateOfBirth,
            gender = gender,
            guardianName = parentName,
            guardianMobNumber = parentMobileNumber,
            studentMobNumber = studentMobileNumber,
            batchName = courseName,
            joiningDate = dateOfJoining,
            feeAmount = feeAmount,
            lastYearPercent = lastYearPercent
        )

        viewModel.insert(student)
        finish()
    }


    private fun initIds() {
        studentNameEditText = findViewById(R.id.nameEditText)
        dateOfBirthEditText = findViewById(R.id.dobEditText)
        genderEditText = findViewById(R.id.genderEditText)
        gradeEditText = findViewById(R.id.classEditText)
        boardEditText = findViewById(R.id.boardEditText)
        enrolledSubjectsEditText = findViewById(R.id.enrolledSubjectsEditText)
        parentNameEditText = findViewById(R.id.parentsNameEditText)
        parentMobNoEditText = findViewById(R.id.parentsMobileNumberEditText)
        studentMobNoEditText = findViewById(R.id.studentMobileNumberEditText)
        batchNameEditText = findViewById(R.id.batchNameEditText)
        joiningDateEditText = findViewById(R.id.joiningDateEditText)
        feeAmountEditText = findViewById(R.id.feeAmountEditText)
        lastYearPercentEditText = findViewById(R.id.lastYearPercentEditText)
    }
}