package com.example.assignment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SelectOptionsAdapter(
    private val names: List<String>,
    private var selectedPosition: Int = -1,
    private val onItemSelected: (String) -> Unit
) : RecyclerView.Adapter<SelectOptionsAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icon: ImageView = view.findViewById(R.id.brandIcon)
        val name: TextView = view.findViewById(R.id.brandName)
        val radioButton: RadioButton = view.findViewById(R.id.radioButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_radio, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = names[position]
//        val iconRes: Int? = (icons ?: emptyList()).getOrNull(position)

//        if (iconRes != null && iconRes != 0) {
//            holder.icon.visibility = View.VISIBLE
//            holder.icon.setImageResource(iconRes)
//        } else {
//            holder.icon.visibility = View.GONE
//        }
        holder.radioButton.isChecked = position == selectedPosition

        holder.itemView.setOnClickListener {
            val oldPosition = selectedPosition
            selectedPosition = position
            notifyItemChanged(oldPosition)
            notifyItemChanged(selectedPosition)
            onItemSelected(names[position])
        }

        holder.radioButton.setOnClickListener {
            holder.itemView.performClick()
        }
    }

    override fun getItemCount() = names.size
}



