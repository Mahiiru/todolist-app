package com.mahiiru.todolistapp

data class Task(val name: String, val category: TaskCategory, var isSelected: Boolean = false)
