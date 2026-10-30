package com.joshua.campusflow.data.repository

import com.joshua.campusflow.model.Assignment
import kotlinx.coroutines.flow.Flow

interface AssignmentRepository {
  suspend fun getAssignmentsForCourse(courseId: Int): List<Assignment>
  suspend fun getAssignmentById(assignmentId: Int): Assignment?
  suspend fun updateAssignmentCompletion(assignmentId: Int, isCompleted: Boolean)
  fun observeAssignmentById(assignmentId: Int): Flow<Assignment?>
  fun observeAssignmentsForCourse(courseId: Int): Flow<List<Assignment>>
}