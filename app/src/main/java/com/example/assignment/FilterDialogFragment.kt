package com.example.assignment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

data class FilterOption(
    val name: String,
    var isSelected: Boolean = false
)

class FilterDialogFragment : DialogFragment() {

    private var onApply: ((selectedBrands: List<String>, selectedFuelTypes: List<String>) -> Unit)? = null

    fun setOnApplyListener(listener: (List<String>, List<String>) -> Unit) {
        onApply = listener
    }

    private lateinit var leftBrandOption: TextView
    private lateinit var leftFuelTypeOption: TextView
    private lateinit var rightRecyclerView: RecyclerView
    private lateinit var clearButton: TextView
    private lateinit var applyButton: TextView
    private lateinit var closeButton: ImageButton

    private lateinit var brandAdapter: FilterAdapter
    private lateinit var fuelTypeAdapter: FilterAdapter

    private lateinit var brandOptions: MutableList<FilterOption>
    private lateinit var fuelTypeOptions: MutableList<FilterOption>

    private var currentFilterType: FilterType = FilterType.BRAND

    enum class FilterType { BRAND, FUEL_TYPE }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_filter, container, false)

        leftBrandOption = view.findViewById(R.id.brandOption)
        leftFuelTypeOption = view.findViewById(R.id.fuelTypeOption)
        rightRecyclerView = view.findViewById(R.id.rightList)
        clearButton = view.findViewById(R.id.clearButton)
        applyButton = view.findViewById(R.id.applyButton)
        closeButton = view.findViewById(R.id.closeButton)


        brandOptions = mutableListOf(
            FilterOption(getString(R.string.tata)),
            FilterOption(getString(R.string.honda)),
            FilterOption(getString(R.string.hero)),
            FilterOption(getString(R.string.bajaj)),
            FilterOption(getString(R.string.yamaha)),
            FilterOption(getString(R.string.others)),
        )
        fuelTypeOptions = mutableListOf(
            FilterOption(getString(R.string.petrol)),
            FilterOption(getString(R.string.electric)),
            FilterOption(getString(R.string.diesel)),
            FilterOption(getString(R.string.cng)),
        )
        setupAdapters()
        setupLeftMenu()
        setupButtons()

        return view
    }

    private fun setupAdapters() {
        brandAdapter = FilterAdapter(brandOptions) { position ->
            brandOptions[position].isSelected = !brandOptions[position].isSelected
            brandAdapter.notifyItemChanged(position)
        }
        fuelTypeAdapter = FilterAdapter(fuelTypeOptions) { position ->
            fuelTypeOptions[position].isSelected = !fuelTypeOptions[position].isSelected
            fuelTypeAdapter.notifyItemChanged(position)
        }

        rightRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        rightRecyclerView.adapter = brandAdapter
    }

    private fun setupLeftMenu() {
        leftBrandOption.setOnClickListener {
            currentFilterType = FilterType.BRAND
            updateLeftMenuSelection()
            updateRightList()
        }
        leftFuelTypeOption.setOnClickListener {
            currentFilterType = FilterType.FUEL_TYPE
            updateLeftMenuSelection()
            updateRightList()
        }
        updateLeftMenuSelection()
    }

    private fun updateLeftMenuSelection() {
        if (currentFilterType == FilterType.BRAND) {
            leftBrandOption.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_blue))
            leftFuelTypeOption.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_text_color))
        } else {
            leftFuelTypeOption.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_blue))
            leftBrandOption.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_text_color))
        }
    }

    private fun updateRightList() {
        rightRecyclerView.adapter = if (currentFilterType == FilterType.BRAND) brandAdapter else fuelTypeAdapter
    }

    private fun getCurrentOptions(): MutableList<FilterOption> {
        return if (currentFilterType == FilterType.BRAND) brandOptions else fuelTypeOptions
    }

    private fun setupButtons() {
        clearButton.setOnClickListener {
            brandOptions.forEach { it.isSelected = false }
            fuelTypeOptions.forEach { it.isSelected = false }

            brandAdapter.notifyDataSetChanged()
            fuelTypeAdapter.notifyDataSetChanged()

            onApply?.invoke(emptyList(), emptyList())

            dismiss()
        }



        applyButton.setOnClickListener {
            val selectedBrands = brandOptions.filter { it.isSelected }.map { it.name }
            val selectedFuelTypes = fuelTypeOptions.filter { it.isSelected }.map { it.name }
            onApply?.invoke(selectedBrands, selectedFuelTypes)
            dismiss()
        }

        closeButton.setOnClickListener {
            dismiss()
        }
    }
}
