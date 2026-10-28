package com.joshua.campusflow.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.joshua.campusflow.DashboardScreen

@Serializable
data object Dashboard: NavKey

@Serializable
data class CourseDetails(
  val courseId: Int
): NavKey

@Serializable
data object CoursesRoute: NavKey

@Serializable
data object TasksRoute: NavKey

@Serializable
data object ProfileRoute: NavKey