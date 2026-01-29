package com.projects.studentsapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.projects.studentsapp.databinding.ActivityNewStudentBinding
import com.projects.studentsapp.model.Student
import com.projects.studentsapp.model.Model

class NewStudentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewStudentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener { finish() }

        binding.btnCancel.setOnClickListener { finish() }

        binding.btnSave.setOnClickListener {
            val student = Student(
                name = binding.etName.text?.toString()?.trim().orEmpty(),
                studentId = binding.etId.text?.toString()?.trim().orEmpty(),
                phone = binding.etPhone.text?.toString()?.trim().orEmpty(),
                address = binding.etAddress.text?.toString()?.trim().orEmpty(),
                isChecked = binding.cbChecked.isChecked
            )

            if (student.name.isBlank() || student.studentId.isBlank()) {
                Toast.makeText(this, "Name and ID are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val ok = Model.add(student)
            if (!ok) {
                Toast.makeText(this, "Student ID already exists (or invalid)", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Toast.makeText(this, "Student added", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}