package com.example.studentsapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.studentsapp.R
import com.example.studentsapp.databinding.ActivityStudentDetailsBinding
import com.example.studentsapp.repository.StudentsRepository

class StudentDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStudentDetailsBinding
    private var studentId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        studentId = intent.getStringExtra("STUDENT_ID")

        setupToolbar()
        loadStudentDetails()
        setupButtons()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun loadStudentDetails() {
        studentId?.let { id ->
            val student = StudentsRepository.getStudentById(id)
            if (student != null) {
                binding.textViewId.text = student.id
                binding.textViewName.text = student.name
                binding.textViewPhone.text = student.phone
                binding.textViewAddress.text = student.address
                binding.textViewStatus.text = if (student.isChecked) {
                    getString(R.string.checked)
                } else {
                    getString(R.string.not_checked)
                }
            } else {
                Toast.makeText(this, "Student not found", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun setupButtons() {
        binding.buttonEdit.setOnClickListener {
            studentId?.let { id ->
                val intent = Intent(this, EditStudentActivity::class.java)
                intent.putExtra("STUDENT_ID", id)
                startActivity(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadStudentDetails()
    }
}
