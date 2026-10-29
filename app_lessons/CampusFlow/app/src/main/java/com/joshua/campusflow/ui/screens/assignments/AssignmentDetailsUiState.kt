package com.joshua.campusflow.ui.screens.assignments

import com.joshua.campusflow.model.Assignment

sealed interface AssignmentDetailsUiState {

  data object Loading : AssignmentDetailsUiState

  data class Success(val assignment: Assignment) : AssignmentDetailsUiState

  data class Error(val message: String) : AssignmentDetailsUiState
}