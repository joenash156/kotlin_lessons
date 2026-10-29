package com.joshua.campusflow.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay

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

@Serializable
data object MainTabs : NavKey

@Serializable
data class CourseAssignments(
  val courseId: Int
) : NavKey

@Serializable
data class AssignmentDetails(
  val assignmentId: Int
) : NavKey