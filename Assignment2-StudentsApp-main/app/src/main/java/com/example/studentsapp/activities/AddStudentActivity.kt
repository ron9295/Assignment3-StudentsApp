package com.example.studentsapp.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.studentsapp.R
import com.example.studentsapp.databinding.ActivityAddStudentBinding
import com.example.studentsapp.models.Student
import com.example.studentsapp.repository.StudentsRepository

class AddStudentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddStudentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupButtons()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setupButtons() {
        binding.buttonSave.setOnClickListener {
            saveStudent()
        }

        binding.buttonCancel.setOnClickListener {
            finish()
        }
    }

    private fun saveStudent() {
        val id = binding.editTextId.text.toString().trim()
        val name = binding.editTextName.text.toString().trim()
        val phone = binding.editTextPhone.text.toString().trim()
        val address = binding.editTextAddress.text.toString().trim()

        // Validate inputs
        if (id.isEmpty() || name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            Toast.makeText(this, R.string.error_empty_field, Toast.LENGTH_SHORT).show()
            return
        }

        // Check for duplicate ID
        if (StudentsRepository.getStudentById(id) != null) {
            Toast.makeText(this, R.string.error_duplicate_id, Toast.LENGTH_SHORT).show()
            return
        }

        // Create and add student
        val student = Student(
            id = id,
            name = name,
            phone = phone,
            address = address,
            isChecked = false
        )

        StudentsRepository.addStudent(student)
        Toast.makeText(this, R.string.student_added, Toast.LENGTH_SHORT).show()
        finish()
    }
}
