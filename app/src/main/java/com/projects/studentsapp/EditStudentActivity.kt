package com.projects.studentsapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.projects.studentsapp.students.R
import com.projects.studentsapp.databinding.ActivityEditStudentBinding
import com.projects.studentsapp.model.Student
import com.projects.studentsapp.model.Model

class EditStudentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditStudentBinding
    private var originalId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener { finish() }

        originalId = intent.getStringExtra(EXTRA_STUDENT_ID)

        binding.btnCancel.setOnClickListener { finish() }

        binding.btnDelete.setOnClickListener {
            val id = originalId ?: return@setOnClickListener
            AlertDialog.Builder(this)
                .setTitle("Delete student")
                .setMessage("Are you sure you want to delete this student?")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Delete") { _, _ ->
                    Model.deleteById(id)
                    Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .show()
        }

        binding.btnSave.setOnClickListener {
            val id = originalId
            if (id.isNullOrBlank()) {
                Toast.makeText(this, "Missing original id", Toast.LENGTH_SHORT).show()
                finish()
                return@setOnClickListener
            }

            val updated = Student(
                name = binding.etName.text?.toString()?.trim().orEmpty(),
                studentId = binding.etId.text?.toString()?.trim().orEmpty(),
                phone = binding.etPhone.text?.toString()?.trim().orEmpty(),
                address = binding.etAddress.text?.toString()?.trim().orEmpty(),
                isChecked = binding.cbChecked.isChecked
            )

            if (updated.name.isBlank() || updated.studentId.isBlank()) {
                Toast.makeText(this, "Name and ID are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val ok = Model.update(id, updated)
            if (!ok) {
                Toast.makeText(this, "Could not update (ID already exists?)", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            originalId = updated.studentId // for safety if user stays (usually finishes)
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        val id = originalId
        if (id.isNullOrBlank()) {
            Toast.makeText(this, "Missing student id", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val student = Model.getById(id)
        if (student == null) {
            Toast.makeText(this, "Student not found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        binding.ivPhoto.setImageResource(R.drawable.student_avatar)
        binding.etName.setText(student.name)
        binding.etId.setText(student.studentId)
        binding.etPhone.setText(student.phone)
        binding.etAddress.setText(student.address)
        binding.cbChecked.isChecked = student.isChecked
    }

    companion object {
        const val EXTRA_STUDENT_ID = "student_id"
    }
}