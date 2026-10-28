package com.joshua.campusflow.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CourseAssignmentsScreen(
  courseId: Int,
  onAssignmentClick: (Int) -> Unit,
  onBackClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxSize(),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {

    Header(
      title = "Assignments",
      onBackClick = onBackClick
    )

    Column(
      modifier = modifier
        .padding(horizontal = 12.dp),
    ) {
      Text(
        text = "Course ID: $courseId",
        style = MaterialTheme.typography.bodyMedium
      )
    }

  }
}