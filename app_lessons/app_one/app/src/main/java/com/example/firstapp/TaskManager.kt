package com.example.firstapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TaskManager(
  onBackClick: () -> Unit,
  onForwardClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier,
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    // Import the image
    val image = painterResource(R.drawable.ic_task_completed)

    Image(
      painter = image,
      contentDescription = null,
    )
    Text(
      text = stringResource(R.string.all_task_completed),
      fontWeight = Bold,
      fontSize = 20.sp,
      modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
    )
    Text(
      text = stringResource(R.string.nice_work),
      fontSize = 16.sp
    )
    TextButton(
      onClick = onBackClick,
    ) {
      Text("Back to Compose Article")
    }
    TextButton(
      onClick = onForwardClick,
    ) {
      Text("To Compose Quadrant")
    }
  }
}