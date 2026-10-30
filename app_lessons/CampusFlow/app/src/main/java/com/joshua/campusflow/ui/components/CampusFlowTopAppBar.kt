package com.joshua.campusflow.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class CampusFlowAppBarStyle {
  Regular,
  Large
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampusFlowTopAppBar(
  title: String,
  onBackClick: () -> Unit,
  modifier: Modifier = Modifier,
  style: CampusFlowAppBarStyle = CampusFlowAppBarStyle.Regular,
  expandedHeight: Dp? = null,
  collapsedHeight: Dp = TopAppBarDefaults.LargeAppBarCollapsedHeight,
  scrollBehavior: TopAppBarScrollBehavior? = null,
  actions: @Composable RowScope.() -> Unit = {},
) {
  val navigationIcon: @Composable () -> Unit = {
    IconButton(onClick = onBackClick) {
      Icon(
        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
        contentDescription = "Back"
      )
    }
  }

  val appBarColors = TopAppBarDefaults.topAppBarColors(
    containerColor = MaterialTheme.colorScheme.background,
    scrolledContainerColor = MaterialTheme.colorScheme.background,
    titleContentColor = MaterialTheme.colorScheme.onBackground,
    navigationIconContentColor = MaterialTheme.colorScheme.onBackground
  )

  when (style) {
    CampusFlowAppBarStyle.Regular -> {
      TopAppBar(
        modifier = modifier
          .padding(end = 6.dp),
        title = {
          Text(
            title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold
          )
        },
        navigationIcon = navigationIcon,
        expandedHeight = expandedHeight
          ?: TopAppBarDefaults.TopAppBarExpandedHeight,
        scrollBehavior = scrollBehavior,
        colors = appBarColors,
        actions = actions
      )
    }

    CampusFlowAppBarStyle.Large -> {
      LargeTopAppBar(
        modifier = modifier,
        title = {
          Text(
            title,
            fontWeight = FontWeight.SemiBold
          )
        },
        navigationIcon = navigationIcon,
        expandedHeight = expandedHeight
          ?: TopAppBarDefaults.LargeAppBarExpandedHeight,
        collapsedHeight = collapsedHeight,
        scrollBehavior = scrollBehavior,
        colors = appBarColors,
        actions = actions
      )
    }
  }
}