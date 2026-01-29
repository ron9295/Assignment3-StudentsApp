package com.example.studentsapp.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.studentsapp.R
import com.example.studentsapp.databinding.FragmentStudentDetailsBinding
import com.example.studentsapp.repository.StudentsRepository

class StudentDetailsFragment : Fragment() {

    private var _binding: FragmentStudentDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: StudentDetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadStudentDetails()
    }

    override fun onResume() {
        super.onResume()
        loadStudentDetails()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_details, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_edit_student -> {
                val action = StudentDetailsFragmentDirections
                    .actionStudentDetailsFragmentToEditStudentFragment(args.studentId)
                findNavController().navigate(action)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun loadStudentDetails() {
        val student = StudentsRepository.getStudentById(args.studentId)
        if (student != null) {
            binding.textViewId.text = student.id
            binding.textViewName.text = student.name
            binding.textViewPhone.text = student.phone
            binding.textViewAddress.text = student.address
            binding.textViewBirthDate.text = student.birthDate.ifEmpty { "Not set" }
            binding.textViewBirthTime.text = student.birthTime.ifEmpty { "Not set" }
            binding.textViewStatus.text = if (student.isChecked) {
                getString(R.string.checked)
            } else {
                getString(R.string.not_checked)
            }
        } else {
            // Student not found, navigate back
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
