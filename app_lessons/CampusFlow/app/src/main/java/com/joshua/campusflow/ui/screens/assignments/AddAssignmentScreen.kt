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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.joshua.campusflow.ui.components.CampusFlowTopAppBar
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAssignmentScreen(
  uiState: AddAssignmentUiState,
  onSaveAssignment: (String, String, LocalDate) -> Unit,
  onBackClick: () -> Unit,
  onAssignmentSaved: () -> Unit,
  modifier: Modifier = Modifier
) {

  val titleState = rememberTextFieldState()
  val descriptionState = rememberTextFieldState()
  var hasAttemptedSave by rememberSaveable { mutableStateOf(false) }

  val titleFocusRequester = remember { FocusRequester() }
  val descriptionFocusRequester = remember { FocusRequester() }

  val titleHasError = hasAttemptedSave && titleState.text.isBlank()
  val descriptionHasError = hasAttemptedSave && descriptionState.text.isBlank()

  var showDatePicker by rememberSaveable { mutableStateOf(false) }
  var selectedDueDateMillis by rememberSaveable { mutableStateOf<Long?>(null) }
  val selectedDueDate: LocalDate? = selectedDueDateMillis?.let { millis ->
      Instant.ofEpochMilli(millis)
        .atZone(ZoneOffset.UTC)
        .toLocalDate()
    }
  val dateFormatter = remember { DateTimeFormatter.ofPattern("d MMMM yyyy") }
  val dueDateHasError = hasAttemptedSave && selectedDueDate == null


  val interactionSource = remember { MutableInteractionSource() }
  val isPressed by interactionSource.collectIsPressedAsState()

  val isSaving = uiState is AddAssignmentUiState.Saving

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
          .focusRequester(titleFocusRequester),
        isError = titleHasError,
        supportingText = {
          if (titleHasError) {
            Text("Assignment title is required")
          }
        },
        keyboardOptions = KeyboardOptions(
          imeAction = ImeAction.Next
        )
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
          .focusRequester(descriptionFocusRequester),
        isError = descriptionHasError,
        supportingText = {
          if (descriptionHasError) {
            Text("Description is required")
          }
        },
        keyboardOptions = KeyboardOptions(
          imeAction = ImeAction.Done
        )
      )

      OutlinedButton(
        onClick = {
          showDatePicker = true
        },
        modifier = Modifier.fillMaxWidth()
      ) {
        Text(
          text = selectedDueDate?.format(dateFormatter)
            ?: "Select Due Date"
        )
      }
      if (dueDateHasError) {
        Text(
          text = "Please select a due date",
          color = MaterialTheme.colorScheme.error,
          style = MaterialTheme.typography.bodySmall
        )
      }

      Button(
        onClick = {
          hasAttemptedSave = true

          val title = titleState.text.toString().trim()
          val description = descriptionState.text.toString().trim()

          when {
            title.isBlank() -> {
              titleFocusRequester.requestFocus()
            }
            description.isBlank() -> {
              descriptionFocusRequester.requestFocus()
            }
            selectedDueDate == null -> {
              showDatePicker = true
            }
            else -> {
              onSaveAssignment(
                title,
                description,
                selectedDueDate
              )
            }
          }
        },
        interactionSource = interactionSource,
        enabled = !isSaving,
        modifier = Modifier
          .fillMaxWidth()
          .graphicsLayer(
            scaleX = scale,
            scaleY = scale
          )
      ) {
        Text(
          text = if (isSaving) "Saving..." else "Save Assignment",
          style = MaterialTheme.typography.titleMedium
        )
      }

      if (uiState is AddAssignmentUiState.Error) {
        Text(
          text = uiState.message,
          color = MaterialTheme.colorScheme.error,
          style = MaterialTheme.typography.bodyMedium
        )
      }

      LaunchedEffect(uiState) {
        if (uiState is AddAssignmentUiState.Success) {
          onAssignmentSaved()
        }
      }


    }
  }

  if (showDatePicker) {
    val datePickerState = rememberDatePickerState(
      initialSelectedDateMillis = selectedDueDateMillis
    )

    DatePickerDialog(
      onDismissRequest = {
        showDatePicker = false
      },
      confirmButton = {
        TextButton(
          onClick = {
            selectedDueDateMillis = datePickerState.selectedDateMillis
            showDatePicker = false
          }
        ) {
          Text("OK")
        }
      },
      dismissButton = {
        TextButton(
          onClick = {
            showDatePicker = false
          }
        ) {
          Text("Cancel")
        }
      }
    ) {
      DatePicker(
        state = datePickerState
      )
    }
  }
}