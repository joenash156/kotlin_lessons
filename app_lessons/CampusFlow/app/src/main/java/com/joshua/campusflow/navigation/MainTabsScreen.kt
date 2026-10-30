package com.joshua.campusflow.navigation

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.joshua.campusflow.DashboardScreen
import com.joshua.campusflow.model.Course
import com.joshua.campusflow.ui.components.CampusFlowBottomBar
import com.joshua.campusflow.ui.components.LocalBottomBarPadding
import com.joshua.campusflow.ui.screens.CoursesScreen
import com.joshua.campusflow.ui.screens.ProfileScreen
import com.joshua.campusflow.ui.screens.TasksScreen
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource

@Composable
fun MainTabsScreen(
  courses: List<Course>,
  onCourseClick: (Int) -> Unit,
  sharedTransitionScope: SharedTransitionScope,
  animatedVisibilityScope: AnimatedVisibilityScope,
  modifier: Modifier = Modifier
) {

  var selectedTab by rememberSaveable { mutableStateOf("home") }
  val stateHolder = rememberSaveableStateHolder()

  // Shared between the content (what gets blurred) and the bar (where the blur shows).
  val hazeState = remember { HazeState() }

  Scaffold(
    modifier = modifier,
    containerColor = MaterialTheme.colorScheme.background,
    bottomBar = {
      CampusFlowBottomBar(
        selectedRoute = when (selectedTab) {
          "courses" -> CoursesRoute
          "tasks" -> TasksRoute
          "profile" -> ProfileRoute
          else -> Dashboard
        },
        onTabSelected = { route ->
          selectedTab = when (route) {
            CoursesRoute -> "courses"
            TasksRoute -> "tasks"
            ProfileRoute -> "profile"
            else -> "home"
          }
        },
        hazeState = hazeState,
      )
    }
  ) { innerPadding ->
    // The content now runs UNDER the bar (only top padding is applied),
    // so it can be seen through the glass. Screens read LocalBottomBarPadding
    // to add space at the end of their lists.
    CompositionLocalProvider(LocalBottomBarPadding provides innerPadding.calculateBottomPadding()) {
      Box(
        modifier = Modifier
          .fillMaxSize()
          .hazeSource(state = hazeState)
          .background(MaterialTheme.colorScheme.background)
          .padding(top = innerPadding.calculateTopPadding()),
        contentAlignment = Alignment.Center
      ) {
        stateHolder.SaveableStateProvider(selectedTab) {
          when (selectedTab) {
            "home" -> DashboardScreen(
              courses = courses,
              onCourseClick = onCourseClick,
              sharedTransitionScope = sharedTransitionScope,
              animatedVisibilityScope = animatedVisibilityScope
            )
            "courses" -> CoursesScreen()
            "tasks" -> TasksScreen()
            "profile" -> ProfileScreen()
            else -> Text("Unknown destination")
          }
        }
      }
    }
  }
}