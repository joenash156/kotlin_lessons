package com.example.firstapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ComposeQuadrant(
  onBackClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = Modifier.fillMaxHeight()
  ) {

    Row(
      modifier = Modifier
        .weight(1f)
        .fillMaxWidth()
        .background(Color.Cyan)
    ) {
      Text("Row 1")
    }

    Row(
      modifier = Modifier
        .weight(1f)
        .fillMaxWidth()
        .background(Color.Yellow)
    ) {
      Text("Row 2")
    }

//    TextButton(
//      modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
//      onClick = onBackClick
//    ) {
//      Text("Back to Task Manager")
//    }
  }
}