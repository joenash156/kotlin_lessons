package com.joshua.campusflow.data.repository

import com.joshua.campusflow.model.Assignment
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface AssignmentRepository {
  suspend fun getAssignmentsForCourse(courseId: Int): List<Assignment>
  suspend fun getAssignmentById(assignmentId: Int): Assignment?
  suspend fun updateAssignmentCompletion(assignmentId: Int, isCompleted: Boolean)
  fun observeAssignmentById(assignmentId: Int): Flow<Assignment?>
  fun observeAssignmentsForCourse(courseId: Int): Flow<List<Assignment>>
  suspend fun createAssignment(
    courseId: Int,
    title: String,
    description: String,
    dueDate: LocalDate
  ): Assignment
}