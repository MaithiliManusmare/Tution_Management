package com.example.assignment

import StudentViewModel
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
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

        addButton.setOnClickListener {
            submitForm()
        }


        backArrowIv.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        gradeEditText.setOnClickListener {
            val brandNames = listOf(getString(R.string.tata),
                getString(R.string.honda), getString(R.string.hero),
                getString(R.string.bajaj), getString(R.string.yamaha), getString(R.string.others))
            val brandIcons = listOf(
                R.drawable.tata,
                R.drawable.honda,
                R.drawable.hero,
                R.drawable.bajaj,
                R.drawable.yamaha
            )
            showSelectionPopup(
                title = getString(R.string.select_brand),
                items = brandNames,
                icons = brandIcons,
                targetEditText = gradeEditText
            )
        }

        boardEditText.setOnClickListener {
            val modelNames = listOf(
                getString(R.string.activa_4g),
                getString(R.string.activa_5g),
                getString(R.string.activa_6g),
                getString(R.string.activa_125),
                getString(R.string.activa_125_bsg),
                getString(R.string.activa_h_smart)
            )
            showSelectionPopup(
                title = getString(R.string.select_vehicle_model),
                items = modelNames,
                icons = null,
                targetEditText = boardEditText
            )
        }

        enrolledSubjectsEditText.setOnClickListener {
            val fuelTypes = listOf(getString(R.string.petrol),
                getString(R.string.electric), getString(R.string.diesel), getString(R.string.cng))

//            showSelectionPopup(
//                title = getString(R.string.select_fuel_type),
//                items = fuelTypes,
//                icons = null,
//                targetEditText = fuelTypeEditText
//            )
        }

        studentNameEditText.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                studentNameEditText.setText("")
            }
            false
        }

        studentNameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()) {
                    studentNameEditText.setTextColor(
                        ContextCompat.getColor(
                            this@RegistrationFormActivity,
                            R.color.dark_text_color
                        )
                    )
                    studentNameEditText.setTypeface(studentNameEditText.typeface, Typeface.BOLD)
                    studentNameEditText.textSize = 14f
                } else {
                    studentNameEditText.setTextColor(
                        ContextCompat.getColor(
                            this@RegistrationFormActivity,
                            R.color.dark_text_color
                        )
                    )
                    studentNameEditText.setTypeface(null, Typeface.NORMAL)
                    studentNameEditText.textSize = 14f
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })



        genderEditText.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                genderEditText.setText("")
            }
            false
        }

        genderEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()) {
                    genderEditText.setTextColor(
                        ContextCompat.getColor(
                            this@RegistrationFormActivity,
                            R.color.dark_text_color
                        )
                    )
                    genderEditText.setTypeface(genderEditText.typeface, Typeface.BOLD)
                    genderEditText.textSize = 14f
                } else {
                    genderEditText.setTextColor(
                        ContextCompat.getColor(
                            this@RegistrationFormActivity,
                            R.color.dark_text_color
                        )
                    )
                    genderEditText.setTypeface(null, Typeface.NORMAL)
                    genderEditText.textSize = 14f
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        dateOfBirthEditText.setOnClickListener {
            showDatePickerDialog()
        }

        dateOfBirthEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()) {
                    dateOfBirthEditText.setTextColor(
                        ContextCompat.getColor(
                            this@RegistrationFormActivity,
                            R.color.dark_text_color
                        )
                    )
                    dateOfBirthEditText.setTypeface(dateOfBirthEditText.typeface, Typeface.BOLD)
                    dateOfBirthEditText.textSize = 14f
                } else {
                    dateOfBirthEditText.setTextColor(
                        ContextCompat.getColor(
                            this@RegistrationFormActivity,
                            R.color.dark_text_color
                        )
                    )
                    dateOfBirthEditText.setTypeface(null, Typeface.NORMAL)
                    dateOfBirthEditText.textSize = 14f
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, _, _ ->
                dateOfBirthEditText.setText(selectedYear.toString())
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
        icons: List<Int>?,
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

        val adapter = SelectOptionsAdapter(items, icons) { selectedItem ->
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
        val dateOfBirth = dateOfBirthEditText.text?.toString()?.toLong() ?: 0
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
        studentNameEditText = findViewById(R.id.vehicleNoTypeEditText)
        dateOfBirthEditText = findViewById(R.id.yearEditText)
        genderEditText = findViewById(R.id.ownerNameEditText)
        gradeEditText = findViewById(R.id.brandEditText)
        boardEditText = findViewById(R.id.nameEditText)
        enrolledSubjectsEditText = findViewById(R.id.fuelTypeEditText)
        parentNameEditText = findViewById(R.id.parentsNameEditText)
        parentMobNoEditText = findViewById(R.id.parentsMobileNumberEditText)
        studentMobNoEditText = findViewById(R.id.studentMobileNumberEditText)
        batchNameEditText = findViewById(R.id.batchNameEditText)
        joiningDateEditText = findViewById(R.id.joiningDateEditText)
        feeAmountEditText = findViewById(R.id.feeAmountEditText)
        lastYearPercentEditText = findViewById(R.id.lastYearPercentEditText)
    }
}