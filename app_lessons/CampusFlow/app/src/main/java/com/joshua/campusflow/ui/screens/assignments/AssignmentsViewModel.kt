package com.joshua.campusflow.ui.screens.assignments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joshua.campusflow.data.repository.AssignmentRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory


class AssignmentsViewModel(
  private val repository: AssignmentRepository,
  private val courseId: Int
) : ViewModel() {

  private val _uiState = MutableStateFlow<AssignmentsUiState>(
    AssignmentsUiState.Loading
  )

  val uiState: StateFlow<AssignmentsUiState> = _uiState

  init {
    loadAssignments()
  }

  private fun loadAssignments() {
    viewModelScope.launch {
      _uiState.value = AssignmentsUiState.Loading

      try {
        repository.observeAssignmentsForCourse(courseId)
          .collect { assignments ->

            _uiState.value = AssignmentsUiState.Success(
              assignments = assignments
            )

          }

      } catch (exception: CancellationException) {
        throw exception
      } catch (exception: Exception) {
        _uiState.value = AssignmentsUiState.Error(
          "Unable to load assignments."
        )
      }
    }
  }

  companion object {

    fun factory(
      repository: AssignmentRepository,
      courseId: Int
    ): ViewModelProvider.Factory = viewModelFactory {

      initializer {
        AssignmentsViewModel(
          repository = repository,
          courseId = courseId
        )
      }
    }
  }
}