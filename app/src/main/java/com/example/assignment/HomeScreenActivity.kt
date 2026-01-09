package com.example.assignment

import StudentViewModel
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HomeScreenActivity : AppCompatActivity() {
    private lateinit var studentCardButton: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        createObject()
        initVariables()
        onClickListener()

//        lifecycleScope.launch {
//            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.filteredVehicles.collect { filteredList ->
//                    vehicleAdapter.setData(filteredList)
//                }
//            }
//        }

//        lifecycleScope.launch {
//            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.filteredVehicles.collect { vehicles ->
//                    vehicleAdapter.setData(vehicles)
//
//                    val totalVehiclesCount = vehicles.size
//                    val totalEVCount = vehicles.count { it.fuelType.equals(getString(R.string.electric), ignoreCase = true) }
//
//                    findViewById<TextView>(R.id.totalVehicleTv).text = totalVehiclesCount.toString()
//                    findViewById<TextView>(R.id.totalEvTv).text = totalEVCount.toString()
//                }
//            }
//        }

    }

    private fun onClickListener() {


//        filterTv.setOnClickListener {
//            val filterDialog = FilterDialogFragment()
//            filterDialog.setOnApplyListener { selectedBrands, selectedFuelTypes ->
//                viewModel.updateSelectedBrands(selectedBrands)
//                viewModel.updateSelectedFuelTypes(selectedFuelTypes)
//            }
//            filterDialog.show(supportFragmentManager, "FilterDialog")
//        }

        studentCardButton.setOnClickListener {
            val intent = Intent(this, StudentListActivity::class.java)
            startActivity(intent)
        }

    }

    fun createObject() {
    }

    fun initVariables() {
//        filterTv = findViewById(R.id.filterTv)
        studentCardButton = findViewById(R.id.studentCard)
    }

}
