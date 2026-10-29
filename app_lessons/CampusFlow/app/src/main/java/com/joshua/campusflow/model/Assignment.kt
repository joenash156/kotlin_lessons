package com.joshua.campusflow.model

import java.time.LocalDate

data class Assignment(
  val id: Int,
  val courseId: Int,
  val title: String,
  val description: String,
  val dueDate: LocalDate,
  val isCompleted: Boolean = false
)