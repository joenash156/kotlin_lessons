package com.example.firstapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ComposeQuadrant(
  onBackClick: () -> Unit,
  onForwardClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = Modifier.fillMaxWidth()
  ) {
    Row(
      modifier = Modifier
        .weight(1f)
    ) {
      QuadrantInfo(
        title = "Text composable",
        description = "Displays text and follows the recommended Material Design guidelines.",
        backgroundColor = Color(0xFFEADDFF),
        modifier = Modifier.weight(1f)
      )
      QuadrantInfo(
        title = "Image composable",
        description = "Creates a composable that lays out and draws a given Painter class object.",
        backgroundColor = Color(0xFFD0BCFF),
        modifier = Modifier.weight(1f)
      )
    }

    Row(
      modifier = Modifier
        .weight(1f)
        .fillMaxWidth()
        .background(Color.Yellow)
    ) {
      QuadrantInfo(
        title = "Row composable",
        description = "A layout composable that places its children in a horizontal sequence.",
        backgroundColor = Color(0xFFB69DF8),
        modifier = Modifier
          .weight(1f)
      )
      QuadrantInfo(
        title = "Column composable",
        description = "A layout composable that places its children in a vertical sequence.",
        backgroundColor = Color(0xFFF6EDFF),
        modifier = Modifier
          .weight(1f)
      )

    }

    Row(
      horizontalArrangement = Arrangement.SpaceBetween,
      modifier = Modifier
        .fillMaxWidth()
        .padding(end = 12.dp, start = 12.dp)
    ) {
      TextButton(
        // modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
        onClick = onBackClick
      ) {
        Text("Back to Task Manager")
      }

      TextButton(
        onClick = onForwardClick
      ) {
        Text("To Business Card")
      }
    }

  }
}

@Composable
fun QuadrantInfo(
  modifier: Modifier = Modifier,
  backgroundColor: Color,
  title: String,
  description: String
) {
  Column(
    modifier = modifier
      .background(backgroundColor)
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = title,
      fontWeight = FontWeight.Bold,
      modifier = Modifier.padding(bottom = 16.dp)
    )
    Text(
      text = description,
      textAlign = TextAlign.Justify
    )
  }
}