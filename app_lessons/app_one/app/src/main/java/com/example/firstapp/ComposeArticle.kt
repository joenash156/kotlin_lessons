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

@Composable
fun ComposeArticle(
  onBackClick: () -> Unit,
  modifier: Modifier = Modifier
) {

  Column(modifier) {
    // Import the image
    val image = painterResource(R.drawable.bg_compose_background)

    Image(
      painter = image,
      contentDescription = null
    )
    Text(
      text = stringResource(R.string.jetpack_compose_tutorial),
      fontSize = 22.sp,
      modifier = Modifier.padding(12.dp)
    )
    Text(
      text = stringResource(R.string.second_text),
      fontSize = 13.sp,
      modifier = Modifier.padding(end = 14.dp, start = 14.dp),
      textAlign = TextAlign.Justify
    )
    Text(
      text = stringResource(R.string.third_text),
      fontSize = 13.sp,
      modifier = Modifier.padding(top = 8.dp, end = 14.dp, start = 14.dp),
      textAlign = TextAlign.Justify
    )
    TextButton(
      onClick = onBackClick,
      modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
    ) {
      Text("Back to Main Page")
    }
  }

}