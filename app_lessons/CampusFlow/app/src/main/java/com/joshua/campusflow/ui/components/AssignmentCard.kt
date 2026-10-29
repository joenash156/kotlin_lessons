package com.joshua.campusflow.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joshua.campusflow.model.Assignment

@Composable
fun AssignmentCard(
  assignment: Assignment,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    onClick = onClick,
    modifier = modifier.fillMaxWidth()
  ) {
    Column(
      modifier = Modifier.padding(12.dp),
      verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      Text(
        text = assignment.title,
        style = MaterialTheme.typography.titleMedium
      )

      Text(
        text = assignment.description,
        style = MaterialTheme.typography.bodyMedium
      )

      Text(
        text = "Due: ${assignment.dueDate}",
        style = MaterialTheme.typography.bodySmall
      )

      val statusText = if (assignment.isCompleted) "Completed" else "Pending"
      Text(
        text = "Status: $statusText",
        style = MaterialTheme.typography.labelMedium
      )
    }
  }
}
