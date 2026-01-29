package com.projects.studentsapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.projects.studentsapp.model.Student

class StudentsAdapter(
    private val onRowClicked: (studentId: String) -> Unit,
    private val onCheckedToggled: (studentId: String) -> Unit
) : RecyclerView.Adapter<StudentsAdapter.StudentVH>() {

    private val items: MutableList<Student> = mutableListOf()

    fun submit(newItems: List<Student>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentVH {
        val binding = ItemStudentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StudentVH(binding)
    }

    override fun onBindViewHolder(holder: StudentVH, position: Int) = holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    inner class StudentVH(private val binding: ItemStudentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(student: Student) {
            binding.ivPhoto.setImageResource(R.drawable.student_avatar)
            binding.tvName.text = student.name
            binding.tvId.text = student.studentId

            // Avoid triggering listener when recycling.
            binding.cbChecked.setOnCheckedChangeListener(null)
            binding.cbChecked.isChecked = student.isChecked
            binding.cbChecked.setOnCheckedChangeListener { _, _ ->
                onCheckedToggled(student.studentId)
            }

            binding.root.setOnClickListener {
                onRowClicked(student.studentId)
            }
        }
    }
}