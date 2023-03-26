package com.kursatkumsuz.ui.components.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.kursatkumsuz.ui.components.BottomNavItem


@ExperimentalAnimationApi
@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()

    BottomNavigation(
        modifier = modifier,
        backgroundColor = Color.Black,
        elevation = 10.dp
    ) {
        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(
                selected = selected,
                selectedContentColor = Color.Gray,
                unselectedContentColor = Color.DarkGray,
                onClick = { onItemClick(item) },
                icon = {
                    AnimatedVisibility(visible = selected) {
                        Row(
                            modifier = modifier
                                .background(MaterialTheme.colors.primary),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                modifier = Modifier.clip(CircleShape)
                                    .background(Color(0x9A293646))
                            ) {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.name,
                                    modifier = Modifier.padding(10.dp)
                                )
                            }
                        }
                    }
                    AnimatedVisibility(
                        visible = !selected
                    ) {
                        Icon(imageVector = item.icon, contentDescription = item.name)
                    }
                })
        }
    }
}
