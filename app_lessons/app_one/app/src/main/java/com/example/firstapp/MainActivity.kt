package com.example.firstapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.firstapp.ui.theme.FirstAppTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue


class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      FirstAppTheme {
        var currentScreen by remember { mutableStateOf("main") }

        Surface(
          modifier = Modifier.fillMaxSize().safeDrawingPadding(),
          color = MaterialTheme.colorScheme.background
        ) {

          when (currentScreen) {

            "main" -> GreetingImage(
              message = stringResource(R.string.welcome_message_to_joshua),
              from = stringResource(R.string.signature_text),
              onComposeArticleClick = {
                currentScreen = "compose_article"
              }
            )

            "compose_article" -> ComposeArticle(
              onBackClick = {
                currentScreen = "main"
              },
              onForwardClick = {
                currentScreen = "task_manager"
              }
            )

            "task_manager" -> TaskManager(
              onBackClick = {
                currentScreen = "compose_article"
              },
              onForwardClick = {
                currentScreen = "compose_quadrant"
              }
            )

            "compose_quadrant" -> ComposeQuadrant(
              onBackClick = {
                currentScreen = "task_manager"
              }
            )
          }
        }
      }
    }
  }
}

@Composable
fun GreetingText(message: String, from: String, modifier: Modifier = Modifier) {
  Column(
    verticalArrangement = Arrangement.Center,
    modifier = modifier
  ) {
    Text(
      text = message,
      fontSize = 60.sp,
      lineHeight = 100.sp,
      textAlign = TextAlign.Center
    )
    Text(
      text = from,
      fontSize = 20.sp,
      modifier = Modifier
        .padding(12.dp)
        .align(alignment = Alignment.CenterHorizontally)
    )
  }
}

@Composable
fun GreetingImage(message: String, from: String, onComposeArticleClick: () -> Unit, modifier: Modifier = Modifier) {
  // Get the image
  val image = painterResource(R.drawable.androidparty)
  Box(modifier) {
    Image(
      painter = image,
      contentDescription = null,
      contentScale = ContentScale.Crop,
      alpha = 0.5F
    )
    GreetingText(
      message = message,
      from = from,
      modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)
    )
    TextButton(
      onClick = onComposeArticleClick,
      modifier = Modifier.align(Alignment.BottomCenter)
    ) {
      Text("Go to Compose Article")
    }
  }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
  FirstAppTheme {
    GreetingImage(stringResource(R.string.welcome_message_to_joshua), from = stringResource(R.string.signature_text), onComposeArticleClick = {})
  }
}