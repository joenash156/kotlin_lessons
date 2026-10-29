package com.joshua.campusflow.ui.screens.assignments

import com.joshua.campusflow.model.Assignment

sealed interface AssignmentsUiState {

  data object Loading : AssignmentsUiState

  data class Success(
    val assignments: List<Assignment>
  ) : AssignmentsUiState

  data class Error(
    val message: String
  ) : AssignmentsUiState
}