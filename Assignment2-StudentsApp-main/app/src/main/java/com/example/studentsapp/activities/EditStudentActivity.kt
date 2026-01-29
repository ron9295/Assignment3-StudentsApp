package com.example.studentsapp.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.studentsapp.R
import com.example.studentsapp.databinding.ActivityEditStudentBinding
import com.example.studentsapp.models.Student
import com.example.studentsapp.repository.StudentsRepository

class EditStudentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditStudentBinding
    private var originalStudentId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        originalStudentId = intent.getStringExtra("STUDENT_ID")

        setupToolbar()
        loadStudentData()
        setupButtons()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun loadStudentData() {
        originalStudentId?.let { id ->
            val student = StudentsRepository.getStudentById(id)
            if (student != null) {
                binding.editTextId.setText(student.id)
                binding.editTextName.setText(student.name)
                binding.editTextPhone.setText(student.phone)
                binding.editTextAddress.setText(student.address)
            } else {
                Toast.makeText(this, "Student not found", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun setupButtons() {
        binding.buttonSave.setOnClickListener {
            saveStudent()
        }

        binding.buttonCancel.setOnClickListener {
            finish()
        }

        binding.buttonDelete.setOnClickListener {
            showDeleteConfirmation()
        }
    }

    private fun saveStudent() {
        val newId = binding.editTextId.text.toString().trim()
        val name = binding.editTextName.text.toString().trim()
        val phone = binding.editTextPhone.text.toString().trim()
        val address = binding.editTextAddress.text.toString().trim()

        // Validate inputs
        if (newId.isEmpty() || name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            Toast.makeText(this, R.string.error_empty_field, Toast.LENGTH_SHORT).show()
            return
        }

        // Check for duplicate ID (if ID was changed)
        originalStudentId?.let { oldId ->
            if (newId != oldId && StudentsRepository.getStudentById(newId) != null) {
                Toast.makeText(this, R.string.error_duplicate_id, Toast.LENGTH_SHORT).show()
                return
            }

            // Get current checked status
            val currentStudent = StudentsRepository.getStudentById(oldId)
            val isChecked = currentStudent?.isChecked ?: false

            // Create updated student
            val updatedStudent = Student(
                id = newId,
                name = name,
                phone = phone,
                address = address,
                isChecked = isChecked
            )

            // Update in repository
            StudentsRepository.updateStudent(oldId, updatedStudent)
            Toast.makeText(this, R.string.student_updated, Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun showDeleteConfirmation() {
        AlertDialog.Builder(this)
            .setTitle(R.string.delete)
            .setMessage(R.string.delete_confirmation)
            .setPositiveButton(R.string.yes) { _, _ ->
                deleteStudent()
            }
            .setNegativeButton(R.string.no, null)
            .show()
    }

    private fun deleteStudent() {
        originalStudentId?.let { id ->
            StudentsRepository.deleteStudent(id)
            Toast.makeText(this, R.string.student_deleted, Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
