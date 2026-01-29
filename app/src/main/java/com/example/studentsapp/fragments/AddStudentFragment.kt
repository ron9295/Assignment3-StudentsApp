package com.example.studentsapp.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.studentsapp.R
import com.example.studentsapp.databinding.FragmentAddStudentBinding
import com.example.studentsapp.models.Student
import com.example.studentsapp.repository.StudentsRepository
import java.text.SimpleDateFormat
import java.util.*

class AddStudentFragment : Fragment() {

    private var _binding: FragmentAddStudentBinding? = null
    private val binding get() = _binding!!
    
    private var selectedBirthDate = ""
    private var selectedBirthTime = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddStudentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtons()
        setupDateTimePickers()
    }

    private fun setupButtons() {
        binding.buttonSave.setOnClickListener {
            saveStudent()
        }

        binding.buttonCancel.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupDateTimePickers() {
        // Birth Date Picker
        binding.editTextBirthDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(year, month, dayOfMonth)
                    val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
                    selectedBirthDate = dateFormat.format(selectedDate.time)
                    binding.editTextBirthDate.setText(selectedBirthDate)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        // Birth Time Picker
        binding.editTextBirthTime.setOnClickListener {
            val calendar = Calendar.getInstance()
            TimePickerDialog(
                requireContext(),
                { _, hourOfDay, minute ->
                    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                    val selectedTime = Calendar.getInstance()
                    selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    selectedTime.set(Calendar.MINUTE, minute)
                    selectedBirthTime = timeFormat.format(selectedTime.time)
                    binding.editTextBirthTime.setText(selectedBirthTime)
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            ).show()
        }
    }

    private fun saveStudent() {
        val id = binding.editTextId.text.toString().trim()
        val name = binding.editTextName.text.toString().trim()
        val phone = binding.editTextPhone.text.toString().trim()
        val address = binding.editTextAddress.text.toString().trim()

        // Validate inputs
        if (id.isEmpty() || name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            showErrorDialog(getString(R.string.error_empty_field))
            return
        }

        // Check for duplicate ID
        if (StudentsRepository.getStudentById(id) != null) {
            showErrorDialog(getString(R.string.error_duplicate_id))
            return
        }

        // Create and add student
        val student = Student(
            id = id,
            name = name,
            phone = phone,
            address = address,
            birthDate = selectedBirthDate,
            birthTime = selectedBirthTime,
            isChecked = false
        )

        StudentsRepository.addStudent(student)
        showSuccessDialog()
    }

    private fun showSuccessDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.success)
            .setMessage(R.string.save_success_message)
            .setPositiveButton(R.string.ok) { _, _ ->
                findNavController().navigateUp()
            }
            .setCancelable(false)
            .show()
    }

    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton(R.string.ok, null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
