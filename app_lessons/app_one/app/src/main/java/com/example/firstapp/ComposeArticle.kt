package com.example.firstapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.LazyColumn

@Composable
fun ComposeArticle(
  onBackClick: () -> Unit,
  onForwardClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  LazyColumn(
    modifier = modifier.fillMaxSize()
  ) {
    // 1. Put the image in its own item block
    item {
      val image = painterResource(R.drawable.bg_compose_background)
      Image(
        painter = image,
        contentDescription = null
      )
    }

    // 2. Put the text elements in their own blocks (or group them)
    item {
      Text(
        text = stringResource(R.string.jetpack_compose_tutorial),
        fontSize = 24.sp,
        modifier = Modifier.padding(16.dp)
      )
    }

    item {
      Text(
        text = stringResource(R.string.second_text),
        modifier = Modifier.padding(horizontal = 16.dp),
        textAlign = TextAlign.Justify
      )
    }

    item {
      Text(
        text = stringResource(R.string.third_text),
        modifier = Modifier.padding(16.dp),
        textAlign = TextAlign.Justify
      )
    }

    // 3. Group the buttons in a single item block and use a standard Column to center them
    item {
      Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        TextButton(onClick = onBackClick) {
          Text(text = "Back to Main Page")
        }
        TextButton(onClick = onForwardClick) {
          Text(text = "To Task Manager")
        }
      }
    }
  }
}