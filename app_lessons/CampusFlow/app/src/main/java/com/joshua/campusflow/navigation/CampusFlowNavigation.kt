package com.joshua.campusflow.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.joshua.campusflow.DashboardScreen
import com.joshua.campusflow.model.Course
import com.joshua.campusflow.ui.screens.CourseDetailsScreen

@Composable
fun CampusFlowNavigation(
  courses: List<Course>,
  modifier: Modifier = Modifier
) {
  // TODO 1: Create the back stack starting with the Dashboard
  val backStack = rememberNavBackStack(Dashboard)

  NavDisplay(
    modifier = modifier,
    backStack = backStack,
    onBack = {
      if(backStack.size > 1) backStack.removeLastOrNull()
    },
    // 1. FORWARD TRANSITIONS (Navigating to a new screen)
    transitionSpec = {
      slideInHorizontally(
        initialOffsetX = { it },
        animationSpec = tween(200)
      ) + fadeIn() togetherWith slideOutHorizontally(
        targetOffsetX = { -it },
        animationSpec = tween(200)
      ) + fadeOut()
    },
    // 2. BACKWARD TRANSITIONS (Popping off the stack)
    popTransitionSpec = {
      slideInHorizontally(
        initialOffsetX = { -it },
        animationSpec = tween(200)
      ) + fadeIn() togetherWith slideOutHorizontally(
        targetOffsetX = { it },
        animationSpec = tween(200)
      ) + fadeOut()
    },
    entryProvider = entryProvider {
      entry<Dashboard> {
        DashboardScreen(
          courses,
          onCourseClick = {
            backStack.add(
              CourseDetails(courseId = it)
            )
          }

        )
      }

      entry<CourseDetails> { route ->
        val selectedCourse = courses.firstOrNull {
          it.id == route.courseId
        }

        if(selectedCourse != null) {
          CourseDetailsScreen(
            course = selectedCourse,
            onBackClick = {
              if(backStack.size > 1) backStack.removeLastOrNull()
            }
          )
        } else {
            Text("Course not found!")
        }
      }
    }
  )
}

