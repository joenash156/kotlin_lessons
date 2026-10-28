package com.joshua.campusflow.ui.screens

import android.R.attr.onClick
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.automirrored.filled.Grading
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.West
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joshua.campusflow.Greeting
import com.joshua.campusflow.model.Course
import com.joshua.campusflow.ui.theme.CampusFlowTheme

@Composable
fun CourseDetailsScreen(
  course: Course,
  sharedTransitionScope: SharedTransitionScope,
  animatedVisibilityScope: AnimatedVisibilityScope,
  onBackClick: () -> Unit,
  onAssignmentsClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxSize()
  ) {
    // header
    Header(
      title = "Course Details",
      onBackClick = onBackClick
    )
    Column(
      modifier = Modifier
        .padding(horizontal = 12.dp)
    ) {
      Text(
        text = course.code
      )
      Text(
        text = course.name,
        style = MaterialTheme.typography.titleMedium
      )
//      with(sharedTransitionScope) {
//        Text(
//          text = course.name,
//          style = MaterialTheme.typography.titleMedium,
//          modifier = Modifier.sharedElement(
//            sharedContentState = rememberSharedContentState(
//              key = "course-title-${course.id}"
//            ),
//            animatedVisibilityScope = animatedVisibilityScope
//          )
//        )
//      }
      Text(
        text = course.major
      )

      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 12.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
          containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        ),
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween,
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
        ) {
          Overview(
            name = "Credits",
            counts = course.credits
          )
          Overview(
            name = "Course ID",
            counts = course.id
          )
        }
      }

      Column(
        modifier = Modifier
          .clip(RoundedCornerShape(16.dp))
      ) {
        LinkRow(
          title = "Assignments",
          icon = Icons.AutoMirrored.Filled.Assignment,
          onLinkClick = onAssignmentsClick
        )
        LinkRow(
          title = "Grades",
          icon = Icons.AutoMirrored.Filled.Grading,
          onLinkClick = {}
        )
      }

    }
  }
}


@Composable
fun LinkRow(
  title: String,
  icon: ImageVector,
  onLinkClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  ListItem(
    modifier = modifier
      .clickable {
        onLinkClick()
      },
    headlineContent = {
      Text(
        text = title
      )
    },
    leadingContent = {
      Icon(
        imageVector = icon,
        contentDescription = null
      )
    },
    trailingContent = {
      Icon(
        imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
        contentDescription = "Navigate",
        modifier = Modifier
          .size(16.dp)
      )
    }
  )
}

@Composable
fun Overview(
  name: String,
  counts: Int,
  modifier: Modifier = Modifier
) {
  Column(
    modifier
  ) {
    Text(
      text = name,
      fontSize = 14.sp
    )
    Text(
      text = counts.toString(),
      fontSize = 38.sp,
      fontWeight = FontWeight.SemiBold
    )
  }
}

@Composable
fun Header(
  title: String,
  onBackClick: () -> Unit
) {
  Row(
    modifier = Modifier
      .fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(16.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    IconButton(
      onClick = onBackClick,
    ) {
      Icon(
        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
        contentDescription = "Navigate back",
        tint = MaterialTheme.colorScheme.onSurface
      )
    }
    Text(
      text = title,
      style = MaterialTheme.typography.titleLarge,
      fontWeight = FontWeight.SemiBold
    )
  }
}

//@Preview(showBackground = true)
//@Composable
//fun CourseDetailsPreview() {
//  CampusFlowTheme {
//    CourseDetailsScreen(
//      course = Course(
//        id = 1,
//        name = "Web Technologies",
//        code = "CS411",
//        credits = 4
//      ),
//      onBackClick = {}
//    )
//  }
//}