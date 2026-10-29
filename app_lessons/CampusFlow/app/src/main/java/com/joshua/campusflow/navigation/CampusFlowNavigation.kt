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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.LocalNavAnimatedContentScope
import com.joshua.campusflow.DashboardScreen
import com.joshua.campusflow.data.repository.InMemoryAssignmentRepository
import com.joshua.campusflow.ui.components.CampusFlowBottomBar
import com.joshua.campusflow.ui.screens.AssignmentDetailsScreen
import com.joshua.campusflow.ui.screens.CourseAssignmentsScreen
import com.joshua.campusflow.ui.screens.CoursesScreen
import com.joshua.campusflow.ui.screens.ProfileScreen
import com.joshua.campusflow.ui.screens.TasksScreen
import com.joshua.campusflow.ui.screens.assignments.AssignmentDetailsViewModel
import com.joshua.campusflow.ui.screens.assignments.AssignmentsViewModel


@Composable
fun CampusFlowNavigation(
  courses: List<Course>,
  modifier: Modifier = Modifier
) {
  val backStack = rememberNavBackStack(MainTabs)
  val assignmentRepository = remember { InMemoryAssignmentRepository() }

  SharedTransitionLayout {
      NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = {
          if(backStack.size > 1) backStack.removeLastOrNull()
        },
        entryDecorators = listOf(
          rememberSaveableStateHolderNavEntryDecorator(),
          rememberViewModelStoreNavEntryDecorator()
        ),
        transitionSpec = {
          slideInHorizontally(
            initialOffsetX = { it },
            animationSpec = tween(
              durationMillis = 300,
              easing = FastOutSlowInEasing
            )
          ) togetherWith slideOutHorizontally(
            targetOffsetX = { -it },
            animationSpec = tween(
              durationMillis = 300,
              easing = FastOutSlowInEasing
            )
          )
        },
        popTransitionSpec = {
          slideInHorizontally(
            initialOffsetX = { -it },
            animationSpec = tween(
              durationMillis = 300,
              easing  = FastOutSlowInEasing
            )
          ) togetherWith slideOutHorizontally(
            targetOffsetX = { it },
            animationSpec = tween(
              durationMillis = 300,
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

//          entry<Dashboard> {
//            DashboardScreen(
//              courses,
//              sharedTransitionScope = this@SharedTransitionLayout,
//              animatedVisibilityScope = LocalNavAnimatedContentScope.current,
//              onCourseClick = {
//                backStack.add(
//                  CourseDetails(courseId = it)
//                )
//              },
//            )
//          }

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
                },
                onAssignmentsClick = {
                  backStack.add(
                    CourseAssignments(courseId = route.courseId)
                  )
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

          entry<CourseAssignments> { route ->

            val assignmentsViewModel: AssignmentsViewModel = viewModel(
              factory = AssignmentsViewModel.factory(
                repository = assignmentRepository,
                courseId = route.courseId
              )
            )
            val uiState by assignmentsViewModel.uiState.collectAsStateWithLifecycle()

            CourseAssignmentsScreen(
              courseId = route.courseId,
              uiState = uiState,
              onBackClick = {
                if(backStack.size > 1) backStack.removeLastOrNull()
              },
              onAssignmentClick = { assignmentId ->
                backStack.add(
                  AssignmentDetails(assignmentId = assignmentId)
                )
              }
            )
          }

          entry<AssignmentDetails> { route ->
            val detailsViewModel: AssignmentDetailsViewModel = viewModel(
              factory = AssignmentDetailsViewModel.factory(
                repository = assignmentRepository,
                assignmentId = route.assignmentId
              )
            )

            val uiState by detailsViewModel.uiState.collectAsStateWithLifecycle()

            AssignmentDetailsScreen(
              assignmentId = route.assignmentId,
              uiState = uiState,
              onBackClick = {
                if (backStack.size > 1) {
                  backStack.removeLastOrNull()
                }
              }
            )
          }
        }
      )
    }

}

