package com.joshua.campusflow.ui.screens.assignments

import android.util.Log
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.joshua.campusflow.ui.components.CampusFlowTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAssignmentScreen(
  onBackClick: () -> Unit,
  modifier: Modifier = Modifier
) {

  val titleState = rememberTextFieldState()
  val descriptionState = rememberTextFieldState()

  val interactionSource = remember {
    MutableInteractionSource()
  }

  val isPressed by interactionSource.collectIsPressedAsState()

  val scale by animateFloatAsState(
    targetValue = if(isPressed) 0.97f else 1f,
    animationSpec = spring(
      dampingRatio = Spring.DampingRatioMediumBouncy,
      stiffness = Spring.StiffnessMedium
    ),
    label = "Save assignment button scale"
  )

  Scaffold(
    modifier = modifier,
    topBar = {
      CampusFlowTopAppBar(
        title = "Add Assignment",
        onBackClick = onBackClick
      )
    }
  ) { innerPadding ->

    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .padding(horizontal = 16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

      // Text(titleState.text.toString())

      OutlinedTextField(
        state = titleState,
        label = {
          Text("Assignment title")
        },
        placeholder = {
          Text("e.g. HTML and CSS Lab")
        },
        lineLimits = TextFieldLineLimits.SingleLine,
        modifier = Modifier.fillMaxWidth()
      )

      OutlinedTextField(
        state = descriptionState,
        label = {
          Text("Description")
        },
        placeholder = {
          Text("Describe what needs to be done...")
        },
        lineLimits = TextFieldLineLimits.MultiLine(
          minHeightInLines = 3,
          maxHeightInLines = 6
        ),
        modifier = Modifier.fillMaxWidth()
      )

      Button(
        onClick = {
          Log.d("Output","Title: ${titleState.text} \n Description: ${descriptionState.text}")
        },
        interactionSource = interactionSource,
        modifier = Modifier
          .fillMaxWidth()
          .graphicsLayer(
            scaleX = scale,
            scaleY = scale
          )
      ) {
        Text(
          text = "Save Assignment",
          style = MaterialTheme.typography.titleMedium
        )
      }

    }
  }
}