package com.joshua.campusflow.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation3.runtime.NavKey
import com.joshua.campusflow.R
import com.joshua.campusflow.navigation.CoursesRoute
import com.joshua.campusflow.navigation.Dashboard
import com.joshua.campusflow.navigation.ProfileRoute
import com.joshua.campusflow.navigation.TasksRoute

@Composable
fun CampusFlowBottomBar(
  selectedRoute: NavKey,
  onTabSelected: (NavKey) -> Unit,
  modifier: Modifier = Modifier
) {
  // TODO: Implement the navigation bar
  NavigationBar(modifier) {
    NavigationBarItem(
      selected = selectedRoute == Dashboard,
      onClick = {
        onTabSelected(Dashboard)
      },
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
      },
      colors = NavigationBarItemDefaults.colors(
        selectedIconColor = MaterialTheme.colorScheme.primary,
        selectedTextColor = MaterialTheme.colorScheme.primary,
        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
        indicatorColor = Color.Transparent
      )
    )
    NavigationBarItem(
      selected = selectedRoute == CoursesRoute,
      onClick = {
        onTabSelected(CoursesRoute)
      },
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
      },
      colors = NavigationBarItemDefaults.colors(
        selectedIconColor = MaterialTheme.colorScheme.primary,
        selectedTextColor = MaterialTheme.colorScheme.primary,
        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
        indicatorColor = Color.Transparent
      )
    )
    NavigationBarItem(
      selected = selectedRoute == TasksRoute,
      onClick = {
        onTabSelected(TasksRoute)
      },
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
      },
      colors = NavigationBarItemDefaults.colors(
        selectedIconColor = MaterialTheme.colorScheme.primary,
        selectedTextColor = MaterialTheme.colorScheme.primary,
        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
        indicatorColor = Color.Transparent
      )
    )
    NavigationBarItem(
      selected = selectedRoute == ProfileRoute,
      onClick = {
        onTabSelected(ProfileRoute)
      },
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
      },
      colors = NavigationBarItemDefaults.colors(
        selectedIconColor = MaterialTheme.colorScheme.primary,
        selectedTextColor = MaterialTheme.colorScheme.primary,
        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
        indicatorColor = Color.Transparent
      )
    )
  }
}