package com.mahiiru.todolistapp

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahiiru.todolistapp.databinding.ActivityMainBinding
import com.mahiiru.todolistapp.databinding.DialogTaskBinding

class MainActivity : AppCompatActivity() {

    private val categories = listOf(
        TaskCategory.Business,
        TaskCategory.Personal,
        TaskCategory.Other
    )

    private val tasks = mutableListOf(
        Task("PruebaBusiness", TaskCategory.Business),
        Task("PruebaPersonal", TaskCategory.Personal),
        Task("PruebaOther", TaskCategory.Other)
    )

    private lateinit var binding : ActivityMainBinding
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var tasksAdapter: TasksAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
        initListeners()
    }

    private fun initUI() {
        categoriesAdapter = CategoriesAdapter(categories) { position -> updateCategories(position)}
        binding.rvCategories.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        binding.rvCategories.adapter = categoriesAdapter

        tasksAdapter = TasksAdapter(tasks) { position -> onItemSelected(position)}
        binding.rvTasks.layoutManager = LinearLayoutManager(this)
        binding.rvTasks.adapter = tasksAdapter
    }

    private fun onItemSelected(position: Int) {
        tasks[position].isSelected = !tasks[position].isSelected
        updateTasks()
    }

    private fun updateTasks() {
        val selectedCategories : List<TaskCategory> = categories.filter { it.isSelected }
        val filteredTasks : List<Task> = tasks.filter { selectedCategories.contains(it.category) }
        tasksAdapter.tasks = filteredTasks
        tasksAdapter.notifyDataSetChanged()
    }


    private fun updateCategories(position: Int) {
        categories[position].isSelected = !categories[position].isSelected
        categoriesAdapter.notifyItemChanged(position)
        updateTasks()
    }

    private fun initListeners() {
        binding.fabAddTask.setOnClickListener { showDialog() }
    }

    private fun showDialog() {
        val dialog = Dialog(this)
        val dialogBinding = DialogTaskBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.btnAddTask.setOnClickListener {
            val currentTask = dialogBinding.etTask.text.toString()
            if(currentTask.isNotEmpty()){
                val selectedId = dialogBinding.rgCategories.checkedRadioButtonId
                val selectedRadioButton : RadioButton= dialogBinding.rgCategories.findViewById(selectedId)
                val currentCategory:TaskCategory = when(selectedRadioButton.text){
                    getString(R.string.business) -> TaskCategory.Business
                    getString(R.string.personal) -> TaskCategory.Personal
                    else -> TaskCategory.Other
                }

                tasks.add(Task(currentTask, currentCategory))
                updateTasks()
                dialog.hide()
            }
        }

        dialog.show()
    }
}