package com.projects.studentsapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.projects.studentsapp.R
import com.projects.studentsapp.databinding.ActivityStudentDetailsBinding
import com.projects.studentsapp.model.Model

class StudentDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStudentDetailsBinding
    private var studentId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener { finish() }

        studentId = intent.getStringExtra(EXTRA_STUDENT_ID)

        binding.btnEdit.setOnClickListener {
            val id = studentId ?: return@setOnClickListener
            startActivity(Intent(this, EditStudentActivity::class.java).apply {
                putExtra(EditStudentActivity.EXTRA_STUDENT_ID, id)
            })
        }
    }

    override fun onResume() {
        super.onResume()
        val id = studentId
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
        binding.tvName.text = student.name
        binding.tvId.text = student.studentId
        binding.tvPhone.text = student.phone
        binding.tvAddress.text = student.address
        binding.cbChecked.isChecked = student.isChecked

        // Details screen - checkbox reflects status, but isn't interactive per spec.
        binding.cbChecked.isEnabled = false
    }

    companion object {
        const val EXTRA_STUDENT_ID = "student_id"
    }
}