package com.joshua.campusflow.ui.screens.assignments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.joshua.campusflow.data.repository.AssignmentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

class AssignmentDetailsViewModel(
  private val repository: AssignmentRepository,
  private val assignmentId: Int
) : ViewModel() {

  private val _uiState = MutableStateFlow<AssignmentDetailsUiState>(
    AssignmentDetailsUiState.Loading
  )

  val uiState: StateFlow<AssignmentDetailsUiState> = _uiState

  init {
    loadAssignment()
  }

  private fun loadAssignment() {
    viewModelScope.launch {

      try {
        val assignment = repository.getAssignmentById(assignmentId)

        if (assignment != null) {
          // Success
          _uiState.value = AssignmentDetailsUiState.Success(
            assignment = assignment
          )
        } else {
          // Error
          _uiState.value = AssignmentDetailsUiState.Error(
            message = "No assignment found"
          )
        }

      } catch (exception: CancellationException) {
        throw exception
      } catch (exception: Exception) {
        _uiState.value = AssignmentDetailsUiState.Error(
          message = "Unable to load assignment."
        )
      }

    }
  }

  companion object {

    fun factory(
      repository: AssignmentRepository,
      assignmentId: Int
    ): ViewModelProvider.Factory = viewModelFactory {

      initializer {
        AssignmentDetailsViewModel(
          repository = repository,
          assignmentId = assignmentId
        )
      }

    }
  }
}