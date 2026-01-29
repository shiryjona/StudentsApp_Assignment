package com.projects.studentsapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.projects.studentsapp.databinding.ActivityStudentsListBinding
import com.projects.studentsapp.model.Model

class StudentsListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStudentsListBinding
    private lateinit var adapter: StudentsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Optional: toolbar is purely visual (title is set in XML).

        adapter = StudentsAdapter(
            onRowClicked = { studentId ->
                startActivity(Intent(this, StudentDetailsActivity::class.java).apply {
                    putExtra(StudentDetailsActivity.EXTRA_STUDENT_ID, studentId)
                })
            },
            onCheckedToggled = { studentId ->
                Model.toggleChecked(studentId)
                adapter.submit(Model.getAll())
            }
        )

        binding.recyclerStudents.layoutManager = LinearLayoutManager(this)
        binding.recyclerStudents.adapter = adapter

        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this, NewStudentActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.submit(Model.getAll())
    }
}