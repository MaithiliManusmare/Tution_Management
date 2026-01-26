package com.example.assignment

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate

class StudentAdapter(
    private val onEditClick: (Student) -> Unit,
    private val onDeleteClick: (Student) -> Unit
)  : RecyclerView.Adapter<StudentAdapter.VehicleViewHolder>() {

    private val items = mutableListOf<Student>()

    fun setData(students: List<Student>) {
        Log.d("studentList", students.toString())
        items.clear()
        items.addAll(students)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_student_data, parent, false)
        return VehicleViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class VehicleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvStudentName: TextView = itemView.findViewById(R.id.studentName)
        private val tvCourse: TextView = itemView.findViewById(R.id.courseName)
        private val tvClassGrade: TextView = itemView.findViewById(R.id.classGrade)
        private val icon: ImageView = itemView.findViewById(R.id.icon)

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(student: Student) {
            tvStudentName.text = student.name
            tvCourse.text = student.batchName
            tvClassGrade.text = student.grade.toString()
            icon.setOnClickListener {
                val popup = PopupMenu(it.context, it)
                popup.menuInflater.inflate(R.menu.menu_item_actions, popup.menu)

                popup.setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.action_edit -> {
                            onEditClick(student)
                            true
                        }
                        R.id.action_delete -> {
                            onDeleteClick(student)
                            true
                        }
                        else -> false
                    }
                }

                popup.show()
            }

        }


    }
}
