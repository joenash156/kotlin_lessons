package com.joshua.campusflow.data.repository

import com.joshua.campusflow.model.Assignment
import java.time.LocalDate

class InMemoryAssignmentRepository : AssignmentRepository {

  private val assignments = mutableListOf(
    Assignment(
      id = 1,
      courseId = 2,
      title = "HTML and CSS Lab",
      description = "Build a responsive webpage using HTML and CSS.",
      dueDate = LocalDate.of(2026, 11, 5)
    ),

    Assignment(
      id = 2,
      courseId = 2,
      title = "JavaScript Assignment",
      description = "Implement interactive features using JavaScript.",
      dueDate = LocalDate.of(2026, 11, 12)
    ),

    Assignment(
      id = 3,
      courseId = 1,
      title = "Algorithm Analysis",
      description = "Analyze the time complexity of selected algorithms.",
      dueDate = LocalDate.of(2026, 11, 8)
    ),

    Assignment(
      id = 4,
      courseId = 3,
      title = "Classes in C++ Lab Assignment",
      description = "Explore how classes are implemented in C++",
      dueDate = LocalDate.of(2026, 11, 14)
    )
  )

  override suspend fun getAssignmentsForCourse(courseId: Int): List<Assignment> {
    return assignments.filter { assignment ->
      assignment.courseId == courseId
    }
  }
  override suspend fun getAssignmentById(assignmentId: Int): Assignment? {
    return assignments.firstOrNull { assignment ->
      assignment.id == assignmentId
    }
  }
}