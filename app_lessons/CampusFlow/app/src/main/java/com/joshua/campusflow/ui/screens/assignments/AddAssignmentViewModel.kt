package com.joshua.campusflow.ui.screens.assignments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.joshua.campusflow.data.repository.AssignmentRepository
import java.time.LocalDate
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddAssignmentViewModel(
  private val repository: AssignmentRepository,
  private val courseId: Int
) : ViewModel() {

  private val _uiState =
    MutableStateFlow<AddAssignmentUiState>(AddAssignmentUiState.Idle)

  val uiState = _uiState.asStateFlow()

  fun saveAssignment(
    title: String,
    description: String,
    dueDate: LocalDate
  ) {

    // Prevent duplicate submissions
    if (_uiState.value is AddAssignmentUiState.Saving) return

    // Basic validation
    if (title.isBlank() || description.isBlank()) {
      _uiState.value = AddAssignmentUiState.Error(
        message = "Please complete all required fields."
      )
      return
    }

    _uiState.value = AddAssignmentUiState.Saving

    viewModelScope.launch {
      try {
        val assignment = repository.createAssignment(
          courseId = courseId,
          title = title.trim(),
          description = description.trim(),
          dueDate = dueDate
        )

        _uiState.value = AddAssignmentUiState.Success(
          assignmentId = assignment.id
        )

      } catch (exception: CancellationException) {
        throw exception

      } catch (exception: Exception) {
        _uiState.value = AddAssignmentUiState.Error(
          message = "Unable to save assignment. Please try again."
        )
      }
    }
  }

  fun onSaveSuccessHandled() {
    _uiState.value = AddAssignmentUiState.Idle
  }

  companion object {

    fun factory(
      repository: AssignmentRepository,
      courseId: Int
    ): ViewModelProvider.Factory = viewModelFactory {
      initializer {
        AddAssignmentViewModel(
          repository = repository,
          courseId = courseId
        )
      }
    }
  }
}