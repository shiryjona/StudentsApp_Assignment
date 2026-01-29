package com.projects.studentsapp.model


object Model {
    private val students: MutableList<Student> = mutableListOf()

    init {
        // Seed with some demo data so the list isn't empty on first run.
        students += Student("Shalom", "2999292992", "05434534534", "Rishon LeZion", true)
        students += Student("Noa", "123456789", "0521112233", "Tel Aviv", false)
        students += Student("Amit", "987654321", "0502223344", "Haifa", true)
        students += Student("Dana", "555666777", "0539998888", "Jerusalem", false)
    }

    fun getAll(): List<Student> = students

    fun getById(id: String): Student? = students.firstOrNull { it.studentId == id }

    fun add(student: Student): Boolean {
        if (student.name.isBlank() || student.studentId.isBlank()) return false
        if (students.any { it.studentId == student.studentId }) return false
        students.add(student)
        return true
    }

    fun update(originalId: String, updated: Student): Boolean {
        val index = students.indexOfFirst { it.studentId == originalId }
        if (index == -1) return false

        // If ID changed, make sure it doesn't collide with another student.
        val idChanged = originalId != updated.studentId
        if (idChanged && students.any { it.studentId == updated.studentId }) return false

        students[index] = updated
        return true
    }

    fun deleteById(id: String): Boolean {
        return students.removeIf { it.studentId == id }
    }

    fun toggleChecked(id: String): Boolean {
        val s = getById(id) ?: return false
        s.isChecked = !s.isChecked
        return true
    }
}