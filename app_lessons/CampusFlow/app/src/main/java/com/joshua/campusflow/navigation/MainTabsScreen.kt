package com.joshua.campusflow.navigation

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import com.joshua.campusflow.DashboardScreen
import com.joshua.campusflow.model.Course
import com.joshua.campusflow.ui.components.CampusFlowBottomBar
import com.joshua.campusflow.ui.screens.CoursesScreen
import com.joshua.campusflow.ui.screens.ProfileScreen
import com.joshua.campusflow.ui.screens.TasksScreen

@Composable
fun MainTabsScreen(
  courses: List<Course>,
  onCourseClick: (Int) -> Unit,
  sharedTransitionScope: SharedTransitionScope,
  animatedVisibilityScope: AnimatedVisibilityScope,
  modifier: Modifier = Modifier
) {

  var selectedTab by remember { mutableStateOf<NavKey>(Dashboard) }

  Scaffold(
    modifier = modifier,
    bottomBar = {
      CampusFlowBottomBar(
        selectedRoute = selectedTab,
        onTabSelected = {
          selectedTab = it
        }
      )
    }
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding),
      contentAlignment = Alignment.Center
    ) {
      when (selectedTab) {
        Dashboard ->  DashboardScreen(
          courses = courses,
          onCourseClick = onCourseClick,
          sharedTransitionScope = sharedTransitionScope,
          animatedVisibilityScope = animatedVisibilityScope
        )
        CoursesRoute -> CoursesScreen()
        TasksRoute -> TasksScreen()
        ProfileRoute -> ProfileScreen()
        else -> Text("Unknown destination")
      }
    }
  }
}