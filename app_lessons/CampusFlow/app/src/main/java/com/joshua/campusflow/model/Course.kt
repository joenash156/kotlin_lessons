package com.joshua.campusflow.model

import androidx.annotation.DrawableRes

data class Course(
  val id: Int,
  val name: String,
  val code: String,
  val credits: Int,
  val major: String = "Computer Science",
  @DrawableRes val image: Int? = null
)