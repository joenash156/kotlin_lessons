package com.joshua.campusflow.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.DoneAll
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.joshua.campusflow.R

@Composable
fun CampusFlowBottomBar(
  modifier: Modifier = Modifier
) {
  // TODO: Implement the navigation bar
  NavigationBar(modifier) {
    NavigationBarItem(
      selected = true,
      onClick = {},
      icon = {
        Icon(
          painter = painterResource(R.drawable.ic_home),
          contentDescription = "Home"
        )
      },
      label = {
        Text(
          text = "Home"
        )
      }
    )
    NavigationBarItem(
      selected = true,
      onClick = {},
      icon = {
        Icon(
          painter = painterResource(R.drawable.ic_book_open),
          contentDescription = "Courses"
        )
      },
      label = {
        Text(
          text = "Courses"
        )
      }
    )
    NavigationBarItem(
      selected = true,
      onClick = {},
      icon = {
        Icon(
          painter = painterResource(R.drawable.ic_check_check),
          contentDescription = "Tasks"
        )
      },
      label = {
        Text(
          text = "Tasks"
        )
      }
    )
    NavigationBarItem(
      selected = true,
      onClick = {},
      icon = {
        Icon(
          painter = painterResource(R.drawable.ic_user_round),
          contentDescription = "Profile"
        )
      },
      label = {
        Text(
          text = "Profile"
        )
      }
    )
  }
}