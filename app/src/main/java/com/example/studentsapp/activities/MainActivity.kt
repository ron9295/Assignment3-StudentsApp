package com.example.studentsapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studentsapp.adapters.StudentsAdapter
import com.example.studentsapp.databinding.ActivityMainBinding
import com.example.studentsapp.repository.StudentsRepository

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: StudentsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupFab()
    }

    override fun onResume() {
        super.onResume()
        refreshStudentsList()
    }

    private fun setupRecyclerView() {
        adapter = StudentsAdapter(
            students = emptyList(),
            onStudentClick = { student ->
                val intent = Intent(this, StudentDetailsActivity::class.java)
                intent.putExtra("STUDENT_ID", student.id)
                startActivity(intent)
            },
            onCheckboxClick = { student, isChecked ->
                StudentsRepository.updateCheckStatus(student.id, isChecked)
            }
        )

        binding.recyclerViewStudents.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewStudents.adapter = adapter
    }

    private fun setupFab() {
        binding.fabAddStudent.setOnClickListener {
            val intent = Intent(this, AddStudentActivity::class.java)
            startActivity(intent)
        }
    }

    private fun refreshStudentsList() {
        val students = StudentsRepository.getAllStudents()
        adapter.updateStudents(students)

        // Show/hide empty state
        if (students.isEmpty()) {
            binding.recyclerViewStudents.visibility = View.GONE
            binding.textViewEmpty.visibility = View.VISIBLE
        } else {
            binding.recyclerViewStudents.visibility = View.VISIBLE
            binding.textViewEmpty.visibility = View.GONE
        }
    }
}
