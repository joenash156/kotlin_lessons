package com.joshua.campusflow.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseAssignmentsScreen(
  courseId: Int,
  onAssignmentClick: (Int) -> Unit,
  onBackClick: () -> Unit,
  modifier: Modifier = Modifier
) {

  Scaffold(
    modifier = modifier,
   topBar = {
     TopAppBar(
       title = {
         Text(
           text = "Assignments",
           style = MaterialTheme.typography.titleLarge,
           fontWeight = FontWeight.SemiBold
         )
       },
       navigationIcon = {
         IconButton(onClick = onBackClick) {
           Icon(
             imageVector = Icons.AutoMirrored.Filled.ArrowBack,
             contentDescription = "Back"
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

      Text(
        text = "Course ID: $courseId",
        style = MaterialTheme.typography.bodyMedium
      )

    }
  }
}