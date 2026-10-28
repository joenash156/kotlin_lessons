package com.joshua.campusflow.navigation

import androidx.compose.animation.core.FastOutSlowInEasing
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
import com.joshua.campusflow.model.Course
import com.joshua.campusflow.ui.screens.CourseDetailsScreen
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.navigation3.ui.LocalNavAnimatedContentScope
import com.joshua.campusflow.DashboardScreen
import com.joshua.campusflow.ui.components.CampusFlowBottomBar
import com.joshua.campusflow.ui.screens.CoursesScreen
import com.joshua.campusflow.ui.screens.ProfileScreen
import com.joshua.campusflow.ui.screens.TasksScreen


@Composable
fun CampusFlowNavigation(
  courses: List<Course>,
  modifier: Modifier = Modifier
) {
  // TODO 1: Create the back stack starting with the Dashboard
  val backStack = rememberNavBackStack(MainTabs)

  SharedTransitionLayout {
      NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = {
          if(backStack.size > 1) backStack.removeLastOrNull()
        },
        transitionSpec = {
          slideInHorizontally(
            initialOffsetX = { it },
            animationSpec = tween(
              durationMillis = 250,
              easing = FastOutSlowInEasing
            )
          ) togetherWith slideOutHorizontally(
            targetOffsetX = { -it },
            animationSpec = tween(
              durationMillis = 250,
              easing = FastOutSlowInEasing
            )
          )
        },
        popTransitionSpec = {
          slideInHorizontally(
            initialOffsetX = { -it },
            animationSpec = tween(
              durationMillis = 250,
              easing  = FastOutSlowInEasing
            )
          ) togetherWith slideOutHorizontally(
            targetOffsetX = { it },
            animationSpec = tween(
              durationMillis = 250,
              easing = FastOutSlowInEasing
            )
          )
        },
        entryProvider = entryProvider {
          entry<MainTabs> {
            MainTabsScreen(
              courses = courses,
              sharedTransitionScope = this@SharedTransitionLayout,
              animatedVisibilityScope = LocalNavAnimatedContentScope.current,
              onCourseClick = { courseId ->
                backStack.add(
                  CourseDetails(courseId = courseId)
                )
              }
            )
          }

          entry<Dashboard> {
            DashboardScreen(
              courses,
              sharedTransitionScope = this@SharedTransitionLayout,
              animatedVisibilityScope = LocalNavAnimatedContentScope.current,
              onCourseClick = {
                backStack.add(
                  CourseDetails(courseId = it)
                )
              },
            )
          }

          entry<CourseDetails> { route ->
            val selectedCourse = courses.firstOrNull {
              it.id == route.courseId
            }

            if(selectedCourse != null) {
              CourseDetailsScreen(
                course = selectedCourse,
                sharedTransitionScope = this@SharedTransitionLayout,
                animatedVisibilityScope = LocalNavAnimatedContentScope.current,
                onBackClick = {
                  if(backStack.size > 1) backStack.removeLastOrNull()
                }
              )
            } else {
              Text("Course not found!")
            }
          }

          entry<CoursesRoute> {
            CoursesScreen()
          }

          entry<TasksRoute> {
            TasksScreen()
          }

          entry<ProfileRoute> {
            ProfileScreen()
          }
        }
      )
    }

}

