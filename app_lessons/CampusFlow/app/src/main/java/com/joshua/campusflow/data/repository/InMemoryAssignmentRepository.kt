package com.joshua.campusflow.data.repository

import com.joshua.campusflow.model.Assignment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
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

  private val _assignmentsFlow = MutableStateFlow(assignments.toList())
  val assignmentsFlow = _assignmentsFlow.asStateFlow()

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
  override suspend fun updateAssignmentCompletion(
    assignmentId: Int,
    isCompleted: Boolean
  ) {
    val index = assignments.indexOfFirst { assignment ->
      assignment.id == assignmentId
    }

    if (index == -1) return

    val updatedAssignment = assignments[index].copy(
      isCompleted = isCompleted
    )

    assignments[index] = updatedAssignment
    _assignmentsFlow.value = assignments.toList()
  }

  override fun observeAssignmentById(assignmentId: Int): Flow<Assignment?> {
    return assignmentsFlow.map { assignments ->
      assignments.firstOrNull { assignment ->
        assignment.id == assignmentId
      }
    }
  }
  override fun observeAssignmentsForCourse(courseId: Int): Flow<List<Assignment>> {
    return assignmentsFlow.map { assignments ->
      assignments.filter { assignment ->
        assignment.courseId == courseId
      }
    }
  }
}