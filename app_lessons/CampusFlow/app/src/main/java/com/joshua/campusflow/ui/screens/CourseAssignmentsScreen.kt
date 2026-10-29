package com.joshua.campusflow.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.joshua.campusflow.ui.components.AssignmentCard
import com.joshua.campusflow.ui.components.CampusFlowTopAppBar
import com.joshua.campusflow.ui.screens.assignments.AssignmentsUiState
import androidx.compose.foundation.lazy.items

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseAssignmentsScreen(
  courseId: Int,
  uiState: AssignmentsUiState,
  onAssignmentClick: (Int) -> Unit,
  onBackClick: () -> Unit,
  modifier: Modifier = Modifier
) {

  Scaffold(
    modifier = modifier,
   topBar = {
     CampusFlowTopAppBar(
       title = "Assignments",
       onBackClick = onBackClick,
     )
   }
  ) { innerPadding ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .padding(horizontal = 12.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

      when (uiState) {

        AssignmentsUiState.Loading -> {
          CircularProgressIndicator()
        }

        is AssignmentsUiState.Success -> {
          if (uiState.assignments.isEmpty()) {
            Text("No assignments for this course yet.")
          } else {
            LazyColumn(
              modifier = Modifier.weight(1f),
              verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
              items(
                items = uiState.assignments,
                key = { assignment -> assignment.id }
              ) { assignment ->
                AssignmentCard(
                  assignment = assignment,
                  onClick = {
                    onAssignmentClick(assignment.id)
                  }
                )
              }
            }
          }
        }

        is AssignmentsUiState.Error -> {
          Text(
            text = uiState.message,
            color = MaterialTheme.colorScheme.error
          )
        }
      }

    }
  }
}