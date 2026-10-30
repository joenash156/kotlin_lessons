package com.joshua.campusflow.ui.screens.assignments

sealed interface AddAssignmentUiState {

  data object Idle : AddAssignmentUiState

  data object Saving : AddAssignmentUiState

  data class Success(
    val assignmentId: Int
  ) : AddAssignmentUiState

  data class Error(
    val message: String
  ) : AddAssignmentUiState
}