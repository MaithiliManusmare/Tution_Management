package com.example.assignment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FilterAdapter(
    private val options: List<FilterOption>,
    private val onOptionClicked: (position: Int) -> Unit
) : RecyclerView.Adapter<FilterAdapter.FilterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.filter_item, parent, false)
        return FilterViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        holder.bind(options[position])
    }

    override fun getItemCount(): Int = options.size

    inner class FilterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
        private val optionText: TextView = itemView.findViewById(R.id.optionText)

        fun bind(option: FilterOption) {
            optionText.text = option.name

            checkBox.setOnCheckedChangeListener(null)
            checkBox.isChecked = option.isSelected

            checkBox.setOnCheckedChangeListener { _, isChecked ->
                option.isSelected = isChecked
            }


        }
    }
}

