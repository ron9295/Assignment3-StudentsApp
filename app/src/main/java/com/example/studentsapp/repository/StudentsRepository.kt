package com.example.studentsapp.repository

import com.example.studentsapp.models.Student

object StudentsRepository {
    private val students = mutableListOf<Student>()
    
    init {
        // Add sample students
        students.add(Student("1", "Alice Smith", "050-1234567", "Tel Aviv", false))
        students.add(Student("2", "Bob Johnson", "052-9876543", "Jerusalem", false))
        students.add(Student("3", "Charlie Brown", "054-5555555", "Haifa", false))
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
