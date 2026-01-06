package com.example.assignment

import VehicleViewModel
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
    private lateinit var vehicleNoEditText: TextInputEditText
    private lateinit var yearEditText: TextInputEditText
    private lateinit var ownerNameEditText: TextInputEditText
    private lateinit var brandEditText: TextInputEditText
    private lateinit var modelEditText: TextInputEditText
    private lateinit var fuelTypeEditText: TextInputEditText
    private lateinit var db: VehicleDatabase
    private lateinit var dao: VehicleDao
    private lateinit var viewModel: VehicleViewModel
    private lateinit var repository: VehicleRepository
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
        db = VehicleDatabase.getDatabase(this)
        dao = db.vehicleDao()
        repository = VehicleRepository(dao)
        factory = viewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(VehicleViewModel::class.java)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun onClickListners() {
        val addVehicleButton = findViewById<TextView>(R.id.addButtonTv)
        val backArrowIv = findViewById<ImageView>(R.id.backArrowIv)

        addVehicleButton.setOnClickListener {
            submitForm()
        }


        backArrowIv.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        brandEditText.setOnClickListener {
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
                targetEditText = brandEditText
            )
        }

        modelEditText.setOnClickListener {
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
                targetEditText = modelEditText
            )
        }

        fuelTypeEditText.setOnClickListener {
            val fuelTypes = listOf(getString(R.string.petrol),
                getString(R.string.electric), getString(R.string.diesel), getString(R.string.cng))

//            showSelectionPopup(
//                title = getString(R.string.select_fuel_type),
//                items = fuelTypes,
//                icons = null,
//                targetEditText = fuelTypeEditText
//            )
        }

        vehicleNoEditText.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                vehicleNoEditText.setText("")
            }
            false
        }

        vehicleNoEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()) {
                    vehicleNoEditText.setTextColor(
                        ContextCompat.getColor(
                            this@RegistrationFormActivity,
                            R.color.dark_text_color
                        )
                    )
                    vehicleNoEditText.setTypeface(vehicleNoEditText.typeface, Typeface.BOLD)
                    vehicleNoEditText.textSize = 14f
                } else {
                    vehicleNoEditText.setTextColor(
                        ContextCompat.getColor(
                            this@RegistrationFormActivity,
                            R.color.dark_text_color
                        )
                    )
                    vehicleNoEditText.setTypeface(null, Typeface.NORMAL)
                    vehicleNoEditText.textSize = 14f
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })



        ownerNameEditText.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                ownerNameEditText.setText("")
            }
            false
        }

        ownerNameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()) {
                    ownerNameEditText.setTextColor(
                        ContextCompat.getColor(
                            this@RegistrationFormActivity,
                            R.color.dark_text_color
                        )
                    )
                    ownerNameEditText.setTypeface(ownerNameEditText.typeface, Typeface.BOLD)
                    ownerNameEditText.textSize = 14f
                } else {
                    ownerNameEditText.setTextColor(
                        ContextCompat.getColor(
                            this@RegistrationFormActivity,
                            R.color.dark_text_color
                        )
                    )
                    ownerNameEditText.setTypeface(null, Typeface.NORMAL)
                    ownerNameEditText.textSize = 14f
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        yearEditText.setOnClickListener {
            showDatePickerDialog()
        }

        yearEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()) {
                    yearEditText.setTextColor(
                        ContextCompat.getColor(
                            this@RegistrationFormActivity,
                            R.color.dark_text_color
                        )
                    )
                    yearEditText.setTypeface(yearEditText.typeface, Typeface.BOLD)
                    yearEditText.textSize = 14f
                } else {
                    yearEditText.setTextColor(
                        ContextCompat.getColor(
                            this@RegistrationFormActivity,
                            R.color.dark_text_color
                        )
                    )
                    yearEditText.setTypeface(null, Typeface.NORMAL)
                    yearEditText.textSize = 14f
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
                yearEditText.setText(selectedYear.toString())
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
        val vehicleNumber = vehicleNoEditText.text?.toString()?.trim().orEmpty()
        val yearOfPurchase = yearEditText.text?.toString()?.trim().orEmpty()
        val ownerName = ownerNameEditText.text?.toString()?.trim().orEmpty()
        val brand = brandEditText.text?.toString()?.trim().orEmpty()
        val model = modelEditText.text?.toString()?.trim().orEmpty()
        val fuelType = fuelTypeEditText.text?.toString()?.trim().orEmpty()

        val placeholders = listOf(
            getString(R.string.enter_vehicle_number),
            getString(R.string.enter_year),
            getString(R.string.enter_owner_name),
            getString(R.string.select_brand),
            getString(R.string.select_model),
            getString(R.string.select_model)
        )

        if (vehicleNumber.isEmpty() || placeholders.contains(vehicleNumber) ||
            yearOfPurchase.isEmpty() || placeholders.contains(yearOfPurchase) ||
            ownerName.isEmpty() || placeholders.contains(ownerName) ||
            brand.isEmpty() || placeholders.contains(brand) ||
            model.isEmpty() || placeholders.contains(model) ||
            fuelType.isEmpty() || placeholders.contains(fuelType)
        ) {
            Toast.makeText(this, getString(R.string.please_fill_in_all_fields), Toast.LENGTH_SHORT).show()
            return
        }

//        val student = Student(
//            brand = brand,
//            model = model,
//            fuelType = fuelType,
//            vehicleNumber = vehicleNumber,
//            yearOfPurchase = yearOfPurchase,
//            ownerName = ownerName
//        )
//        viewModel.insert(v)
        finish()
    }


    private fun initIds() {
        vehicleNoEditText = findViewById(R.id.vehicleNoTypeEditText)
        yearEditText = findViewById(R.id.yearEditText)
        ownerNameEditText = findViewById(R.id.ownerNameEditText)
        brandEditText = findViewById(R.id.brandEditText)
        modelEditText = findViewById(R.id.nameEditText)
        fuelTypeEditText = findViewById(R.id.fuelTypeEditText)
    }
}