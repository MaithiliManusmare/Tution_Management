package com.example.assignment

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate
import java.time.Period

class VehicleAdapter : RecyclerView.Adapter<VehicleAdapter.VehicleViewHolder>() {

    private val items = mutableListOf<Student>()

    fun setData(students: List<Student>) {
        items.clear()
        items.addAll(students)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_vehicle_data, parent, false)
        return VehicleViewHolder(view)
    }

    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
//        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class VehicleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvModel: TextView = itemView.findViewById(R.id.textModel)
        private val tvBrand: TextView = itemView.findViewById(R.id.textBrand)
        private val tvVehicleNo: TextView = itemView.findViewById(R.id.textVehicleNumber)
        private val tvFuelType: TextView = itemView.findViewById(R.id.textFuelType)
        private val tvYear: TextView = itemView.findViewById(R.id.textYearOfPurchase)
        private val tvPurchase: TextView = itemView.findViewById(R.id.textDuration)

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(student: Student) {
            tvModel.text = student.name
//            tvBrand.text = student.brand
//            tvVehicleNo.text = student.vehicleNumber
//            tvFuelType.text = student.fuelType
//            tvYear.text = student.yearOfPurchase
//            tvPurchase.text = student.yearOfPurchase

            try {
//                val purchaseYear = student.yearOfPurchase.toInt()
//                val purchaseDate = LocalDate.of(purchaseYear, 1, 1)
                val now = LocalDate.now()

//                val period = Period.between(purchaseDate, now)
//                val years = period.years
//                val months = period.months

//                val yearsText = if (years > 0) "$years year${if (years > 1) "s" else ""}" else ""
//                val monthsText = if (months > 0) "$months month${if (months > 1) "s" else ""}" else ""
//
//                val durationText = listOf(yearsText, monthsText).filter { it.isNotEmpty() }.joinToString(" ")

//                tvPurchase.text = if (durationText.isNotEmpty()) durationText else "Less than a month"
            } catch (e: Exception) {
                tvPurchase.text = "Invalid date"
            }
        }


    }
}
