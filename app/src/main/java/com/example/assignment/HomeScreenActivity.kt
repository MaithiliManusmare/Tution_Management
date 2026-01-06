package com.example.assignment

import VehicleViewModel
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R.id.recyclerView
import kotlinx.coroutines.launch

class HomeScreenActivity : AppCompatActivity() {
    lateinit var button: TextView
    private lateinit var viewModel: VehicleViewModel
    private lateinit var vehicleAdapter: VehicleAdapter
    private lateinit var filterTv: TextView

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
        setAdapter()
        initializeViewModel()

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

    private fun initializeViewModel() {
        val vehicleDao = VehicleDatabase.getDatabase(applicationContext).vehicleDao()
        val repository = VehicleRepository(vehicleDao)
        val factory = viewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[VehicleViewModel::class.java]
    }

    private fun setAdapter() {
        val recyclerView = findViewById<RecyclerView>(recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        vehicleAdapter = VehicleAdapter()
        recyclerView.adapter = vehicleAdapter
    }

    private fun onClickListener() {
        button.setOnClickListener {
            val intent = Intent(this, RegistrationFormActivity::class.java)
            startActivity(intent)
        }

        filterTv.setOnClickListener {
            val filterDialog = FilterDialogFragment()
            filterDialog.setOnApplyListener { selectedBrands, selectedFuelTypes ->
                viewModel.updateSelectedBrands(selectedBrands)
                viewModel.updateSelectedFuelTypes(selectedFuelTypes)
            }
            filterDialog.show(supportFragmentManager, "FilterDialog")
        }
    }

    fun createObject() {
    }

    fun initVariables() {
        button = findViewById(R.id.addButton)
        filterTv = findViewById(R.id.filterTv)
    }


}
