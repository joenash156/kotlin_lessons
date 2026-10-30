package com.joshua.campusflow.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation3.runtime.NavKey
import com.joshua.campusflow.R
import com.joshua.campusflow.navigation.CoursesRoute
import com.joshua.campusflow.navigation.Dashboard
import com.joshua.campusflow.navigation.ProfileRoute
import com.joshua.campusflow.navigation.TasksRoute
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import kotlinx.coroutines.launch

/**
 * How much space the floating bar takes at the bottom of the screen.
 * Screens add this as bottom padding to their scrolling list so the last
 * item can scroll fully above the bar.
 */
val LocalBottomBarPadding = compositionLocalOf { 0.dp }

// ---------------------------------------------------------------------------
// Tab definitions
// ---------------------------------------------------------------------------

/** The little "personality" animation each icon plays when its tab is selected. */
private enum class IconMotion {
  Bounce, // Home: hops up and lands with a spring
  Flip,   // Courses: the book swings open
  Draw,   // Tasks: the checkmarks are drawn in left to right
  Wiggle, // Profile: a friendly head-tilt
}

private data class BottomTab(
  val route: NavKey,
  val label: String,
  @DrawableRes val icon: Int,
  @DrawableRes val selectedIcon: Int,
  val motion: IconMotion,
)

private val BottomTabs = listOf(
  BottomTab(Dashboard, "Home", R.drawable.ic_home, R.drawable.heroicons_home_fill, IconMotion.Bounce),
  BottomTab(CoursesRoute, "Courses", R.drawable.ic_book_open, R.drawable.heroicons_book_open_fill, IconMotion.Flip),
  BottomTab(TasksRoute, "Tasks", R.drawable.ic_check_check, R.drawable.ic_check_check, IconMotion.Draw),
  BottomTab(ProfileRoute, "Profile", R.drawable.heroicons_user, R.drawable.heroicons_user_fill, IconMotion.Wiggle),
)

private val BarHeight: Dp = 64.dp
private val BarInset: Dp = 4.dp

// ---------------------------------------------------------------------------
// The bar
// ---------------------------------------------------------------------------

/**
 * Telegram-style floating glass navigation bar.
 *
 * @param hazeState the same HazeState that the screen content uses in
 *   Modifier.hazeSource(...). Pass null to get a solid translucent bar without blur.
 */
@Composable
fun CampusFlowBottomBar(
  selectedRoute: NavKey,
  onTabSelected: (NavKey) -> Unit,
  modifier: Modifier = Modifier,
  hazeState: HazeState? = null,
) {
  val colors = MaterialTheme.colorScheme
  val isDark = colors.background.luminance() < 0.5f
  val haptics = LocalHapticFeedback.current
  val selectedIndex = BottomTabs.indexOfFirst { it.route == selectedRoute }.coerceAtLeast(0)

  val pill = RoundedCornerShape(percent = 50)
  val rim = if (isDark) Color.White else Color.Black

  Box(
    modifier = modifier
      .fillMaxWidth()
      .navigationBarsPadding()
      .padding(horizontal = 24.dp, vertical = 10.dp),
  ) {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(BarHeight)
        .clip(pill)
        .glass(
          hazeState = hazeState,
          sourceBackground = colors.background,
          tint = colors.surface,
        )
        // Thin light rim along the top edge — sells the "glass" look.
        .border(
          width = 1.dp,
          brush = Brush.verticalGradient(
            listOf(
              rim.copy(alpha = if (isDark) 0.16f else 0.10f),
              rim.copy(alpha = 0.03f),
            )
          ),
          shape = pill,
        )
        .padding(BarInset),
    ) {
      LiquidIndicator(
        selectedIndex = selectedIndex,
        itemCount = BottomTabs.size,
        fill = colors.primary.copy(alpha = if (isDark) 0.20f else 0.14f),
        outline = colors.primary.copy(alpha = 0.18f),
      )

      Row(
        modifier = Modifier
          .fillMaxSize()
          .selectableGroup(),
      ) {
        BottomTabs.forEachIndexed { index, tab ->
          BarTab(
            tab = tab,
            selected = index == selectedIndex,
            activeColor = colors.primary,
            inactiveColor = colors.onSurfaceVariant,
            onClick = {
              if (index != selectedIndex) {
                haptics.performHapticFeedback(HapticFeedbackType.TextHandleMove)
              }
              onTabSelected(tab.route)
            },
          )
        }
      }
    }
  }
}

/** Frosted-glass background. Real blur when a HazeState is supplied, translucent tint otherwise. */
private fun Modifier.glass(
  hazeState: HazeState?,
  sourceBackground: Color,
  tint: Color,
): Modifier =
  if (hazeState == null) {
    this.background(tint.copy(alpha = 0.94f))
  } else {
    this.hazeEffect(state = hazeState) {
      backgroundColor = sourceBackground
      tints = listOf(HazeTint(tint.copy(alpha = 0.55f)))
      blurRadius = 24.dp
      noiseFactor = 0.04f
      // Used on devices where blur isn't available.
      fallbackTint = HazeTint(tint.copy(alpha = 0.94f))
    }
  }

// ---------------------------------------------------------------------------
// Sliding "liquid" indicator
// ---------------------------------------------------------------------------

/**
 * The pill behind the selected tab.
 *
 * Its left and right edges are animated separately: the edge in the direction
 * of travel moves fast, the other edge follows slowly. So the pill stretches
 * toward the new tab, then snaps back to its normal size when it arrives,
 * like a drop of liquid. It also gets slightly thinner while stretched.
 */
@Composable
private fun LiquidIndicator(
  selectedIndex: Int,
  itemCount: Int,
  fill: Color,
  outline: Color,
) {
  // Edges are stored in "tab units": tab 2 spans from 2f to 3f.
  val start = remember { Animatable(selectedIndex.toFloat()) }
  val end = remember { Animatable(selectedIndex + 1f) }

  LaunchedEffect(selectedIndex) {
    val target = selectedIndex.toFloat()
    val movingRight = target > start.value
    val leading = spring<Float>(dampingRatio = 0.78f, stiffness = 520f)
    val trailing = spring<Float>(dampingRatio = 0.86f, stiffness = 210f)
    launch { start.animateTo(target, if (movingRight) trailing else leading) }
    launch { end.animateTo(target + 1f, if (movingRight) leading else trailing) }
  }

  Box(
    modifier = Modifier
      .fillMaxSize()
      .drawBehind {
        val slot = size.width / itemCount
        val stretch = ((end.value - start.value) - 1f).coerceIn(0f, 3f)
        val squash = (stretch * 0.10f).coerceAtMost(0.18f)

        val h = size.height * (1f - squash)
        val top = (size.height - h) / 2f
        val left = start.value * slot
        val width = (end.value - start.value) * slot
        val radius = CornerRadius(h / 2f)

        drawRoundRect(fill, Offset(left, top), Size(width, h), radius)
        drawRoundRect(
          color = outline,
          topLeft = Offset(left, top),
          size = Size(width, h),
          cornerRadius = radius,
          style = Stroke(width = 1.dp.toPx()),
        )
      },
  )
}

// ---------------------------------------------------------------------------
// One tab
// ---------------------------------------------------------------------------

@Composable
private fun RowScope.BarTab(
  tab: BottomTab,
  selected: Boolean,
  activeColor: Color,
  inactiveColor: Color,
  onClick: () -> Unit,
) {
  val interaction = remember { MutableInteractionSource() }
  val pressed by interaction.collectIsPressedAsState()

  // Shrinks a little while your finger is down, springs back on release.
  val pressScale by animateFloatAsState(
    targetValue = if (pressed) 0.86f else 1f,
    animationSpec = spring(dampingRatio = 0.55f, stiffness = Spring.StiffnessMediumLow),
    label = "pressScale",
  )
  val selection by animateFloatAsState(
    targetValue = if (selected) 1f else 0f,
    animationSpec = tween(240, easing = FastOutSlowInEasing),
    label = "selection",
  )
  val contentColor by animateColorAsState(
    targetValue = if (selected) activeColor else inactiveColor,
    animationSpec = tween(240),
    label = "contentColor",
  )

  Column(
    modifier = Modifier
      .weight(1f)
      .fillMaxHeight()
      .selectable(
        selected = selected,
        interactionSource = interaction,
        indication = null, // no ripple — the indicator is the feedback
        role = Role.Tab,
        onClick = onClick,
      )
      .graphicsLayer {
        scaleX = pressScale
        scaleY = pressScale
      },
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
  ) {
    TabIcon(tab = tab, selected = selected, selection = selection, tint = contentColor)
    Spacer(Modifier.height(2.dp))
    Text(
      text = tab.label,
      color = contentColor,
      style = MaterialTheme.typography.labelSmall,
      fontSize = 11.sp,
      fontWeight = FontWeight.SemiBold,
      maxLines = 1,
    )
  }
}

// ---------------------------------------------------------------------------
// Animated icon
// ---------------------------------------------------------------------------

@Composable
private fun TabIcon(
  tab: BottomTab,
  selected: Boolean,
  selection: Float,
  tint: Color,
) {
  val pop = remember { Animatable(1f) }     // scale
  val lift = remember { Animatable(0f) }    // vertical offset, in dp
  val tilt = remember { Animatable(0f) }    // rotation, degrees
  val flip = remember { Animatable(0f) }    // 3D Y rotation, degrees
  val reveal = remember { Animatable(1f) }  // 0 = hidden, 1 = fully drawn
  var hasAppeared by remember { mutableStateOf(false) }

  LaunchedEffect(selected) {
    // Don't play animations when the screen first opens.
    if (!hasAppeared) {
      hasAppeared = true
      return@LaunchedEffect
    }

    if (!selected) {
      // If the user switched away mid-animation, settle everything back to rest.
      launch { pop.animateTo(1f, spring(stiffness = Spring.StiffnessMedium)) }
      launch { lift.animateTo(0f, spring(stiffness = Spring.StiffnessMedium)) }
      launch { tilt.animateTo(0f, spring(stiffness = Spring.StiffnessMedium)) }
      launch { flip.animateTo(0f, spring(stiffness = Spring.StiffnessMedium)) }
      reveal.snapTo(1f)
      return@LaunchedEffect
    }

    // Shared "pop": squeeze down, then overshoot back to full size.
    launch {
      pop.snapTo(0.7f)
      pop.animateTo(1f, spring(dampingRatio = 0.4f, stiffness = 420f))
    }

    when (tab.motion) {
      IconMotion.Bounce -> launch {
        lift.animateTo(-6f, tween(140, easing = FastOutSlowInEasing))
        lift.animateTo(0f, spring(dampingRatio = 0.32f, stiffness = 520f))
      }

      IconMotion.Flip -> launch {
        flip.snapTo(-90f)
        flip.animateTo(0f, spring(dampingRatio = 0.5f, stiffness = 260f))
      }

      IconMotion.Draw -> launch {
        reveal.snapTo(0f)
        reveal.animateTo(1f, tween(380, delayMillis = 40, easing = FastOutSlowInEasing))
      }

      IconMotion.Wiggle -> launch {
        tilt.animateTo(
          targetValue = 0f,
          animationSpec = keyframes<Float> {
            durationMillis = 560
            -14f at 90
            11f at 200
            -6f at 310
            3f at 420
          },
        )
      }
    }
  }

  Box(
    modifier = Modifier
      .size(28.dp)
      .graphicsLayer {
        scaleX = pop.value
        scaleY = pop.value
        translationY = lift.value.dp.toPx()
        rotationZ = tilt.value
        rotationY = flip.value
        cameraDistance = 10f * density
      }
      .drawWithContent {
        clipRect(right = size.width * reveal.value) {
          this@drawWithContent.drawContent()
        }
      },
    contentAlignment = Alignment.Center,
  ) {
    if (tab.icon == tab.selectedIcon) {
      Icon(
        painter = painterResource(tab.icon),
        contentDescription = null, // the label below is read by TalkBack
        tint = tint,
        modifier = Modifier.size(24.dp),
      )
    } else {
      // Outline fades out while the filled icon fades in.
      Icon(
        painter = painterResource(tab.icon),
        contentDescription = null,
        tint = tint,
        modifier = Modifier
          .size(24.dp)
          .graphicsLayer { alpha = 1f - selection },
      )
      Icon(
        painter = painterResource(tab.selectedIcon),
        contentDescription = null,
        tint = tint,
        modifier = Modifier
          .size(24.dp)
          .graphicsLayer { alpha = selection },
      )
    }
  }
}