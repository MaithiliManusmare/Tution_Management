package com.example.assignment
import android.view.View
import android.widget.PopupMenu

class moreOptionsActionMenu<T>(
    private val view: View,
    private val item: T,
    private val listener: Listener<T>
) {
    interface Listener<T> {
        fun onEdit(item: T)
        fun onDelete(item: T)
    }

    fun show() {
        val popup = PopupMenu(view.context, view)
        popup.menuInflater.inflate(R.menu.menu_item_actions, popup.menu)

        popup.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_edit -> {
                    listener.onEdit(item)
                    true
                }
                R.id.action_delete -> {
                    listener.onDelete(item)
                    true
                }
                else -> false
            }
        }
        popup.show()
    }
}
