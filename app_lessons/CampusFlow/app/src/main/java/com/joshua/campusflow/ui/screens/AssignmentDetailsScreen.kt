package com.joshua.campusflow.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joshua.campusflow.ui.components.CampusFlowTopAppBar
import com.joshua.campusflow.ui.screens.assignments.AssignmentDetailsUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssignmentDetailsScreen(
  assignmentId: Int,
  onCompletionChange: (Boolean) -> Unit,
  uiState: AssignmentDetailsUiState,
  onBackClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Scaffold(
    modifier = modifier,
    topBar = {
      CampusFlowTopAppBar(
        title = "Assignment Details",
        onBackClick = onBackClick,

      )
    }
  ) { innerPadding ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .padding(horizontal = 16.dp)
    ) {
      when (uiState) {
        AssignmentDetailsUiState.Loading -> {
          CircularProgressIndicator()
        }

        is AssignmentDetailsUiState.Success -> {
          Text(
            text = uiState.assignment.title,
            style = MaterialTheme.typography.headlineMedium
          )

          Text(
            text = uiState.assignment.description,
            style = MaterialTheme.typography.bodyLarge
          )

          Text(
            text = "Due: ${uiState.assignment.dueDate}",
            style = MaterialTheme.typography.bodyMedium
          )

          Text(
            text = if (uiState.assignment.isCompleted) {
              "Completed"
            } else {
              "Pending"
            }
          )
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
          ) {
            Checkbox(
              checked = uiState.assignment.isCompleted,
              onCheckedChange = onCompletionChange
            )

            Text(
              text = "Mark as completed",
              style = MaterialTheme.typography.bodyMedium
            )
          }
        }

        is AssignmentDetailsUiState.Error -> {
          Text(
            text = uiState.message,
            color = MaterialTheme.colorScheme.error
          )
        }
      }
    }
  }
}