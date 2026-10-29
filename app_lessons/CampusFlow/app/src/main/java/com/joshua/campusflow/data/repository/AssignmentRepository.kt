package com.joshua.campusflow.data.repository

import com.joshua.campusflow.model.Assignment

interface AssignmentRepository {
  suspend fun getAssignmentsForCourse(courseId: Int): List<Assignment>
  suspend fun getAssignmentById(assignmentId: Int): Assignment?
}