package com.example.assignment

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate

class BatchListAdapter : RecyclerView.Adapter<BatchListAdapter.CourseViewHolder>() {

    private val items = mutableListOf<Batch>()

    fun setData(batches: List<Batch>) {
        Log.d("batchList", batches.toString())
        items.clear()
        items.addAll(batches)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_student_data, parent, false)
        return CourseViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvStudentName: TextView = itemView.findViewById(R.id.studentName)
        private val tvCourse: TextView = itemView.findViewById(R.id.courseName)
        private val tvClassGrade: TextView = itemView.findViewById(R.id.classGrade)

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(batch: Batch) {
            tvStudentName.text = batch.name
//            tvCourse.text = batch.batchName
//            tvClassGrade.text = batch.grade.toString()

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
//                tvPurchase.text = "Invalid date"
            }
        }


    }
}
