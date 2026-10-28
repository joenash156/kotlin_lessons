package com.joshua.campusflow.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@Composable
fun CoursesScreen(
  modifier: Modifier = Modifier
) {
  // TODO
  Box(
    modifier = modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = "My Courses",
      style = MaterialTheme.typography.headlineMedium
    )
  }
}

@Composable
fun TasksScreen(
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = "My Tasks",
      style = MaterialTheme.typography.headlineMedium
    )
  }
}

@Composable
fun ProfileScreen(
  modifier: Modifier = Modifier
) {
  // TODO
  Box(
    modifier = modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = "My Profile",
      style = MaterialTheme.typography.headlineMedium
    )
  }
}