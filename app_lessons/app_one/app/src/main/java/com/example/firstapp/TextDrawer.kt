package com.example.firstapp

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ExposureNeg1
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.PlusOne
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



@Composable
fun TextDrawer(
  onBackClick: () -> Unit,
  onForwardClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  var count by remember { mutableIntStateOf(0) }
  var value by remember { mutableStateOf("") }
  Column(
    modifier,
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    TextField(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 12.dp)
        .height(48.dp)
      ,
      textStyle = TextStyle(
        fontSize = 18.sp
      ),
      value = value,
      shape = RoundedCornerShape(25.dp),
      onValueChange = {
        value = it
      },
      placeholder = {
        Text(
          text = "Search for a name...",
          color = Color(0xffbfbfbf),
          fontSize = 17.sp
        )
      },
      leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color(0xffbfbfbf)) },
      trailingIcon = {
        IconButton(
          onClick = {
            value = ""
          },

          ) { }
        Icon(Icons.Default.Clear, contentDescription = null, tint = Color(0xffbfbfbf))
      },
      colors = TextFieldDefaults.colors(
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        errorIndicatorColor = Color.Transparent
      )
    )
    Spacer(
      modifier = Modifier.size(20.dp)
    )
    Text(
      text = "$count",
      modifier = Modifier,
      fontSize = 40.sp,
      fontWeight = FontWeight.Bold
    )
    Spacer(
      modifier = Modifier.size(20.dp)
    )
    Row(
      modifier,
      horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      Button(
        onClick = {
          count--
        },
        colors = ButtonDefaults.buttonColors(
          containerColor = Color(0xddd40b04),
          contentColor = Color.White
        )
      ) {
        Icon(
          imageVector = Icons.Default.ExposureNeg1,
          contentDescription = null
        )
      }
      Button(
        onClick = {
          count = 0
        },
        colors = ButtonDefaults.buttonColors(
          containerColor = Color(0xffdab502),
          contentColor = Color.White
        )
      ) {
        Icon(
          imageVector = Icons.Default.Refresh,
          contentDescription = null,
        )
        Text(
          text = "Reset",
          fontSize = 17.sp
        )
      }
      Button(
        onClick = {
          count++
        },
        colors = ButtonDefaults.buttonColors(
          containerColor = Color(0xff1ab502),
          contentColor = Color.White
        )
      ) {
        Icon(
          imageVector = Icons.Default.PlusOne,
          contentDescription = null,
        )
      }

    }
    Spacer(
      modifier = Modifier.size(20.dp)
    )

    ExpandableCard(
      title = "Lorem Ipsum",
      description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
    )
    ExpandableCard(
      title = "Sed Ut",
      description = "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem."
    )

    Spacer(modifier = Modifier.size(20.dp))
    TextButton(
      onClick = onBackClick
    ) {
      Text(text = "Back to Business Card")
    }
    TextButton(
      onClick = onForwardClick
    ) {
      Text(text = "To Lemonade App")
    }
  }
}

@Composable
fun ExpandableCard(
  modifier: Modifier = Modifier,
  title: String,
  description: String
) {
  var isExpanded by remember { mutableStateOf(false) }
  val rotationAngle by  animateFloatAsState(targetValue = if(isExpanded) 180f else 0f)

  Card(
    modifier = Modifier
      .fillMaxWidth()
      .animateContentSize(
        animationSpec = tween(
          durationMillis = 200,
          easing = LinearOutSlowInEasing
        )
      )
      .padding(horizontal = 12.dp, vertical = 2.dp),
    shape = RoundedCornerShape(8.dp),
    onClick = {
      isExpanded = !isExpanded
    }
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
          .padding(start = 18.dp)
          .height(42.dp)
      ) {
        Text(
          text = title,
          fontSize = 18.sp,
          fontWeight = FontWeight.Bold,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis,
          modifier = Modifier
            .weight(1f)
        )
        IconButton(
          onClick = {
            isExpanded = !isExpanded
          },
          modifier = Modifier
            .rotate(rotationAngle)
        ) {
          Icon(
            imageVector = Icons.Default.ArrowDropDown,
            contentDescription = null
          )
        }
      }
      if(isExpanded) {
        Row(
          horizontalArrangement = Arrangement.spacedBy(4.dp),
          modifier = Modifier
            .padding(start = 18.dp, end = 18.dp, bottom = 8.dp)
        ) {
          Icon(
            imageVector = Icons.Default.Lightbulb,
            contentDescription = null,
            modifier = Modifier
              .padding(vertical = 4.dp)
              .size(16.dp)
          )
          Text(
            text = description,
            textAlign = TextAlign.Justify,
            maxLines = 6,
            overflow = TextOverflow.Ellipsis
          )
        }
      }

    }
  }

}