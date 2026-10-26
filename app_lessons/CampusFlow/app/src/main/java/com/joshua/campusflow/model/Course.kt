package com.joshua.campusflow.model

data class Course(
  val id: Int,
  val name: String,
  val code: String,
  val credits: Int,
  val major: String = "Computer Science"
)