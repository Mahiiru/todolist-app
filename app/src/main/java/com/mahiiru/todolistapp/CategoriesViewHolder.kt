package com.mahiiru.todolistapp

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class CategoriesViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    private val tvCategoryName = view.findViewById<TextView>(R.id.tvCategoryName)
    private val viewDivider = view.findViewById<View>(R.id.viewDivider)
    private val viewContainer = view.findViewById<CardView>(R.id.cvItemTaskCategory)

    fun render(taskCategory: TaskCategory, onItemSelected: (Int) -> Unit) {

        val color = if (taskCategory.isSelected) { R.color.medium_gray } else { R.color.dark_gray }

        viewContainer.setCardBackgroundColor(ContextCompat.getColor(viewContainer.context,color))
        itemView.setOnClickListener{onItemSelected(layoutPosition)}

        when(taskCategory) {
            TaskCategory.Business -> {
                tvCategoryName.text = "Business"
                viewDivider.setBackgroundColor(
                    ContextCompat.getColor(viewDivider.context,R.color.todo_business_category)
                )
            }
            TaskCategory.Personal -> {
                tvCategoryName.text = "Personal"
                viewDivider.setBackgroundColor(
                    ContextCompat.getColor(viewDivider.context,R.color.todo_personal_category)
                )
            }
            TaskCategory.Other -> {
                tvCategoryName.text = "Other"
                viewDivider.setBackgroundColor(
                    ContextCompat.getColor(viewDivider.context,R.color.todo_other_category)
                )
            }
        }
    }
}