package com.example.studentsapp.repository

import com.example.studentsapp.models.Student

object StudentsRepository {
    private val students = mutableListOf<Student>()
    
    init {
        // Add sample students
        students.add(Student(id = "1", name = "Alice Smith", phone = "050-1234567", address = "Tel Aviv", isChecked = false))
        students.add(Student(id = "2", name = "Bob Johnson", phone = "052-9876543", address = "Jerusalem", isChecked = false))
        students.add(Student(id = "3", name = "Charlie Brown", phone = "054-5555555", address = "Haifa", isChecked = false))
    }
    
    fun getAllStudents(): List<Student> {
        return students.toList()
    }
    
    fun getStudentById(id: String): Student? {
        return students.find { it.id == id }
    }
    
    fun addStudent(student: Student) {
        students.add(student)
    }
    
    fun updateStudent(oldId: String, updatedStudent: Student) {
        val index = students.indexOfFirst { it.id == oldId }
        if (index != -1) {
            students[index] = updatedStudent
        }
    }
    
    fun deleteStudent(id: String) {
        students.removeAll { it.id == id }
    }
    
    fun updateCheckStatus(id: String, isChecked: Boolean) {
        students.find { it.id == id }?.isChecked = isChecked
    }
}
