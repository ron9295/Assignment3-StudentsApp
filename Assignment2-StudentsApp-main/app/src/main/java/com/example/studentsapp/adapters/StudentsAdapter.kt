package com.example.studentsapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studentsapp.databinding.ItemStudentBinding
import com.example.studentsapp.models.Student

class StudentsAdapter(
    private var students: List<Student>,
    private val onStudentClick: (Student) -> Unit,
    private val onCheckboxClick: (Student, Boolean) -> Unit
) : RecyclerView.Adapter<StudentsAdapter.StudentViewHolder>() {

    inner class StudentViewHolder(private val binding: ItemStudentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(student: Student) {
            binding.textViewName.text = student.name
            binding.textViewId.text = "ID: ${student.id}"
            binding.checkBoxStudent.isChecked = student.isChecked

            // Handle row click
            binding.root.setOnClickListener {
                onStudentClick(student)
            }

            // Handle checkbox click
            binding.checkBoxStudent.setOnCheckedChangeListener { _, isChecked ->
                onCheckboxClick(student, isChecked)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val binding = ItemStudentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return StudentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(students[position])
    }

    override fun getItemCount(): Int = students.size

    fun updateStudents(newStudents: List<Student>) {
        students = newStudents
        notifyDataSetChanged()
    }
}
