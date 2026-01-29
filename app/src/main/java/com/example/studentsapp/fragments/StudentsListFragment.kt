package com.example.studentsapp.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studentsapp.R
import com.example.studentsapp.adapters.StudentsAdapter
import com.example.studentsapp.databinding.FragmentStudentsListBinding
import com.example.studentsapp.repository.StudentsRepository

class StudentsListFragment : Fragment() {

    private var _binding: FragmentStudentsListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: StudentsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        refreshStudentsList()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add_student -> {
                findNavController().navigate(
                    R.id.action_studentsListFragment_to_addStudentFragment
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupRecyclerView() {
        adapter = StudentsAdapter(
            students = emptyList(),
            onStudentClick = { student ->
                val action = StudentsListFragmentDirections
                    .actionStudentsListFragmentToStudentDetailsFragment(student.id)
                findNavController().navigate(action)
            },
            onCheckboxClick = { student, isChecked ->
                StudentsRepository.updateCheckStatus(student.id, isChecked)
            }
        )

        binding.recyclerViewStudents.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewStudents.adapter = adapter
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
