package com.joshua.campusflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joshua.campusflow.model.Course
import com.joshua.campusflow.ui.theme.CampusFlowTheme
import java.time.LocalTime

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      CampusFlowTheme {
        Scaffold(
          modifier = Modifier.fillMaxSize(),
        ) { innerPadding ->
          DashboardScreen(
            modifier = Modifier.padding(innerPadding)
          )
        }
      }
    }
  }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
  val hour = LocalTime.now().hour;

  val day: String = when (hour) {
    in 0..<5 -> "night"
    in 5..<12 -> "morning"
    in 12..<17 -> "afternoon"
    else -> "evening"
  }

  Text(
    text = "Good $day, $name!",
    modifier = modifier,
    fontSize = 22.sp,
    fontWeight = FontWeight.Bold,
  )
}

@Composable
fun DashboardScreen(
  modifier: Modifier = Modifier
) {

  val courses = listOf<Course>(
    Course(
      id = 1,
      name = "Algorithm Design and Analysis",
      code = "CS321",
      credits = 4,
    ),
    Course(
      id = 2,
      name = "Web Technologies",
      code = "CS411",
      credits = 4
    ),
    Course(
      id = 3,
      name = "Intermediate Computer Programming",
      code = "CS311",
      credits = 4
    ),
    Course(
      id = 4,
      name = "Hardware and Systems fundamentals",
      code = "CS330",
      credits = 2
    ),
    Course(
      id = 5,
      name = "Modern Dance Traditions of Ghana",
      code = "SOAN242",
      credits = 4
    ),
    Course(
      id = 6,
      name = "Leadership Seminar III",
      code = "SOAN242",
      credits = 2
    )
  )

  Column(
    modifier = modifier
      .fillMaxSize()
      .padding(12.dp)
  ) {
    // TODO 1: Display the greeting
    Greeting(name = "Joshua")

    Spacer(modifier = Modifier.height(8.dp))

    // TODO 2: Display the subtitle
    Text(
      text = "Here's what's happening today."
    )

    // TODO 3: Create a Card
    // card
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp),
      shape = RoundedCornerShape(8.dp),
      colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
      )
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(12.dp)
      ) {
        // TODO 4: Inside the Card, display
        Text(
          text = "Today's Overview",
          fontSize = 18.sp,
          fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
          modifier = Modifier
            .fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Overview(
            name = "Courses",
            counts = courses.size
          )
          Overview(
            name = "Tasks due",
            counts = 3
          )
        }
      }
    }

    // courses
    Column(
      modifier = Modifier
        .padding(top = 12.dp)
    ) {
      Text(
        text = "My Courses",
        fontSize = 22.sp,
        fontWeight = FontWeight.SemiBold
      )
      Spacer(modifier = Modifier.height(6.dp))
      CourseList(courses)
    }

  }
}

@Composable
fun Overview(name: String, counts: Int, modifier: Modifier = Modifier) {
  Column(modifier) {
    Text(
      text = counts.toString(),
      fontSize = 38.sp,
      fontWeight = FontWeight.SemiBold
    )
    Text(
      text = name,
      fontSize = 14.sp
    )
  }
}

@Composable
fun CourseCard(
  course: Course,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier
      .fillMaxWidth(),
    shape = RoundedCornerShape(8.dp),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.tertiaryContainer,
      contentColor = MaterialTheme.colorScheme.onTertiaryContainer
    )
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
    ) {
      Text(
        text = course.name,
        fontWeight = FontWeight.Medium,
        fontSize = 19.sp
      )
      Row(
        modifier = Modifier
          .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        Text(
          text = "Code: ${course.code}",
          fontSize = 15.sp
        )
        Text(
          text = "Credits: ${course.credits}"
        )
      }
      Text(
        text = course.major,
        fontSize = 13.sp
      )
    }
  }
}

@Composable
fun CourseList(
  courses: List<Course>,
  modifier: Modifier = Modifier
) {
  LazyColumn(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    items(
      items = courses,
      key = { course -> course.id }
    ) { course ->
      CourseCard(course)
    }
  }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
  CampusFlowTheme {
    Greeting("Android")
  }
}