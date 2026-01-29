package com.projects.studentsapp.model

data class Student(
    var name: String,
    var studentId: String,
    var phone: String,
    var address: String,
    var isChecked: Boolean = false
)