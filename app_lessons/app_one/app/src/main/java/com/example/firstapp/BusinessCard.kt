package com.example.firstapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun BusinessCard(
  onBackClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = Modifier
      .background(Color(0x4DA6FADA)),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {

    Column(
      modifier = modifier
        .padding(bottom = 40.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      // import image
      val image = painterResource(R.drawable.whatsapp_image_2025_04_07_at_10_43_39_94fef948)

      Image(
        painter = image,
        contentDescription = null,
        modifier = Modifier
          .clip(RoundedCornerShape(10.dp))
      )
      Text(
        text = "Joshua Adjei",
        fontSize = 32.sp,
        modifier = Modifier
          .padding(10.dp)

      )
      Text(
        text = "Android Developer Extraordinaire",
        fontWeight = FontWeight.SemiBold
      )
    }

    Column(
      modifier,
      verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      ContactInfo(
        icon = painterResource(R.drawable.phone),
        contentDescription = "Phone number",
        info = "0 (+233) 25 7262 72"

      )
      ContactInfo(
        icon = painterResource(R.drawable.share),
        contentDescription = "Username",
        info = "@_.joenash"
      )
      ContactInfo(
        icon = painterResource(R.drawable.email),
        contentDescription = "Email",
        info = "joenash156@gmail.com"
      )
    }


    TextButton(
      onClick = onBackClick
    ) {
      Text("Back to Compose Quadrant")
    }
  }
}

@Composable
fun ContactInfo(
  modifier: Modifier = Modifier,
  icon: Painter,
  info: String,
  contentDescription: String?,
  tint: Color = LocalContentColor.current,
  iconSize: Int = 20
) {
  Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(20.dp)
  ) {
    Icon(
      painter = icon,
      contentDescription = contentDescription,
      tint = tint,
      modifier = Modifier
        .size(iconSize.dp)
    )
    Text(
      text = info
    )
  }
}