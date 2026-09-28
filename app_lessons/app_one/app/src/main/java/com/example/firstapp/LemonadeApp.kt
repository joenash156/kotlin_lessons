package com.example.firstapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LemonadeApp(
  onBackClick: () -> Unit,
  modifier: Modifier = Modifier,
) {

  var currentStep by remember { mutableIntStateOf(1) }
  var squeezeCount by remember { mutableIntStateOf(0) }

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Text(
            text = "Lemonade",
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp
          )
        },
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = MaterialTheme.colorScheme.primaryContainer,
          titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
      )
    }
  ) { innerPadding ->
    Surface(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .background(MaterialTheme.colorScheme.tertiaryContainer),
      color = MaterialTheme.colorScheme.background
    ) {

      when(currentStep) {
        1 -> {
          LemonTextAndImage(
            textLabelId = R.string.lemon_select,
            currtImageId = R.drawable.lemon_tree,
            textLabelDescriptionId = R.string.lemon_tree_content_description,
            onImageclick = {
              currentStep = 2
              squeezeCount = (2..4).random()
            }
          )
        }
        2 -> {
          LemonTextAndImage(
            textLabelId = R.string.lemon_squeeze,
            currtImageId = R.drawable.lemon_squeeze,
            textLabelDescriptionId = R.string.lemon_content_description,
            onImageclick = {
              squeezeCount--
              if(squeezeCount == 0) currentStep = 3
            }
          )
        }
        3 -> {
          LemonTextAndImage(
            textLabelId = R.string.lemon_drink,
            currtImageId = R.drawable.lemon_drink,
            textLabelDescriptionId = R.string.lemon_drink_description,
            onImageclick = {
              currentStep = 4
            }
          )
        }
        4 -> {
          LemonTextAndImage(
            textLabelId = R.string.lemon_empty_glass,
            currtImageId = R.drawable.lemon_restart,
            textLabelDescriptionId = R.string.empty_glass_content_description,
            onImageclick = {
              currentStep = 1
            }
          )
        }
      }

//      Column(horizontalAlignment = Alignment.CenterHorizontally) {
//        Text(text = "Current Step: $currentStep")
//        Text(text = "Squeeze Count: $squeezeCount")
//      }

      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(bottom = 10.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom
      ) {
        TextButton(
        onClick = onBackClick
      ) {
        Text(
          text = "Back to Text Drawer"
        )
      }
      }


    }
  }

}

@Composable
fun LemonTextAndImage(
  modifier: Modifier = Modifier,
  textLabelId: Int,
  textLabelDescriptionId: Int,
  currtImageId: Int,
  onImageclick: () -> Unit
) {
  Box(modifier = modifier) {
    Column(
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier
        .fillMaxSize(),
    ) {
      Button(
        onClick = onImageclick,
        shape = RoundedCornerShape(dimensionResource(R.dimen.button_corner_radius)),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
      ) {
        Image(
          painter = painterResource(currtImageId),
          contentDescription = stringResource(textLabelDescriptionId),
          modifier = Modifier
            .width(dimensionResource(R.dimen.button_image_width))
            .height(dimensionResource(R.dimen.button_image_height))
            .padding(dimensionResource(R.dimen.button_interior_padding))
        )
      }
      Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_vertical)))
      Text(
        text = stringResource(textLabelId),
        style = MaterialTheme.typography.bodyLarge
      )
    }
  }
}