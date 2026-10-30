package com.joshua.campusflow.ui.screens

import android.R.attr.onClick
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseAssignmentsScreen(
  courseId: Int,
  uiState: AssignmentsUiState,
  onAssignmentClick: (Int) -> Unit,
  onBackClick: () -> Unit,
  modifier: Modifier = Modifier,
  onAddAssignmentClick: () -> Unit

) {

  var expanded by rememberSaveable { mutableStateOf(false) }

  Scaffold(
    modifier = modifier,
    floatingActionButton = {
      Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        AnimatedVisibility(visible = expanded) {
          Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(12.dp)
          ) {
            SmallFloatingActionButton(
              onClick = {
                expanded = false
                // Add Assignment action
                onAddAssignmentClick()
              },
              modifier = Modifier
                .padding(end = 5.dp),
              shape = RoundedCornerShape(20.dp)
            ) {
              Icon(
                imageVector = Icons.AutoMirrored.Filled.Assignment,
                contentDescription = "Add Assignment"
              )
            }

            SmallFloatingActionButton(
              onClick = {
                expanded = false
                // Add Course action
              },
              modifier = Modifier
                .padding(end = 5.dp),
              shape = RoundedCornerShape(20.dp)
            ) {
              Icon(
                imageVector = Icons.Default.Book,
                contentDescription = "Add Course"
              )
            }

            SmallFloatingActionButton(
              onClick = {
                expanded = false
                // Add Task action
              },
              modifier = Modifier
                .padding(end = 5.dp),
              shape = RoundedCornerShape(20.dp)
            ) {
              Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Add Task"
              )
            }
          }
        }

        FloatingActionButton(
          onClick = {
            expanded = !expanded
          },
          shape = RoundedCornerShape(30.dp)
        ) {
          Icon(
            imageVector = if (expanded) {
              Icons.Default.Close
            } else {
              Icons.Default.Add
            },
            contentDescription = if (expanded) {
              "Close menu"
            } else {
              "Open menu"
            }
          )
        }
      }
    },
    topBar = {
     CampusFlowTopAppBar(
       title = "Assignments",
       onBackClick = onBackClick,
       actions = {
         IconButton(
           onClick = onAddAssignmentClick
         ) {
           Icon(
             imageVector = Icons.Default.Add,
             contentDescription = "Add assignment",
             modifier = Modifier
               .size(26.dp)
           )
         }
       }
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