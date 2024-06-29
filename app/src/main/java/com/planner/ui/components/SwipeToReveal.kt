package com.planner.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable


//@OptIn(ExperimentalWearMaterialApi::class)
//@Composable
//fun SwipeToRevealItem(content: @Composable () -> Unit) {
//    val swipeableState = rememberSwipeableState(initialValue = 0)
//    val size = with(LocalDensity.current) { 100.dp.toPx() }
//    val anchors = mapOf(0f to 0, -size to 1)  // 0 is the original position, -size is the revealed position
//
//    val context = LocalContext.current
//
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(80.dp)
//            .swipeable(
//                state = swipeableState,
//                anchors = anchors,
//                thresholds = { _, _ -> FractionalThreshold(0.9f) },
//                orientation = Orientation.Horizontal
//            )
//    ) {
//        // Reveal area behind the item
//        Box(
//            modifier = Modifier
//                .fillMaxHeight()
//                .width(200.dp)
//                .align(Alignment.CenterEnd)
//                .background(Color.White)
//        ) {
//            Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
//                Icon(imageVector = Icons.Default.Favorite, contentDescription = null, modifier = Modifier
//                    .size(36.dp)
//                    .clickable(onClick = {
//                        Toast
//                            .makeText(context, "User added to favorites", Toast.LENGTH_SHORT)
//                            .show()
//                    }),tint = Color.Green)
//                Spacer(modifier = Modifier.width(24.dp))
//                Icon(imageVector = Icons.Default.Email, contentDescription = null, modifier = Modifier
//                    .size(36.dp)
//                    .clickable(onClick = {
//                        Toast
//                            .makeText(context, "Message sent to user", Toast.LENGTH_SHORT)
//                            .show()
//                    }),tint = Color.DarkGray)
//                Spacer(modifier = Modifier.width(24.dp))
//                Icon(imageVector = Icons.Outlined.Delete, contentDescription = null, modifier = Modifier
//                    .size(36.dp)
//                    .clickable(onClick = {
//                        Toast
//                            .makeText(context, "User deleted", Toast.LENGTH_SHORT)
//                            .show()
//                    }),tint = Color.Red)
//            }
//        }
//
//        // Main content which moves with swipe
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .offset(x = swipeableState.offset.value.dp)
//                .background(Color.LightGray)
//        ) {
//            content()
//        }
//    }
//}