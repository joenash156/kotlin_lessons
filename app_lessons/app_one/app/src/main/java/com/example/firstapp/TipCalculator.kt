package com.example.firstapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import java.text.NumberFormat

@Composable
fun TipCalculator(
  onBackClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = Modifier
      .padding(horizontal = 40.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    Text(
      text = stringResource(R.string.calculate_tip),
      modifier = Modifier
        .padding(bottom = 16.dp, top = 40.dp)
        .align(alignment = Alignment.Start)
    )
    EditNumberField(
      modifier = Modifier
        .padding(bottom = 32.dp)
        .fillMaxWidth()
    )
    Text(
      text = stringResource(R.string.tip_amount, "$0.00"),
      style = MaterialTheme.typography.displaySmall
    )
    Spacer(modifier = Modifier.height(150.dp))
    TextButton(
      onClick = onBackClick
    ) {
      Text(
        text = "Back to Lemonade App"
      )
    }
  }
}

@Composable
fun EditNumberField(
  modifier: Modifier = Modifier
) {
  var amountInput by remember { mutableStateOf("") }

  TextField(
    value = amountInput,
    onValueChange = { amountInput = it },
    modifier = modifier
  )
}

/**
 * Calculates the tip based on the user input and format the tip amount
 * according to the local currency.
 * Example would be "$10.00".
 */
private fun calculateTip(amount: Double, tipPercent: Double = 15.0): String {
  val tip = tipPercent / 100 * amount
  return NumberFormat.getCurrencyInstance().format(tip)
}