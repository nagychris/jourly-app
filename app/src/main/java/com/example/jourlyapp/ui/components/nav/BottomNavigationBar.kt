package com.example.jourlyapp.ui.components.nav

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.jourlyapp.ui.theme.JourlyTheme
import com.example.jourlyapp.ui.theme.Typography

@Composable
fun BottomNavigationBar(navController: NavController) {
    val navigationItems = listOf(
        NavigationItem.Challenges,
        NavigationItem.Journal,
        NavigationItem.Report,
        NavigationItem.Profile
    )
    BottomAppBar(
        cutoutShape = CircleShape,
        backgroundColor = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp)),
        elevation = 0.dp,
        contentColor = Color.White
    ) {
        BottomNavigation(
            backgroundColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.background,
            modifier = Modifier.fillMaxSize(),
            elevation = 0.dp
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            navigationItems.forEachIndexed { index, navItem ->
                if (index == 2) {
                    // add an empty space for FAB
                    BottomNavigationItem(
                        selected = false,
                        onClick = {},
                        icon = {},
                        enabled = false
                    )
                }

                val selected = currentDestination?.hierarchy?.any { it.route == navItem.route } == true
                BottomNavigationItem(
                    icon = {
                        Icon(
                            painterResource(id = navItem.iconResourceId),
                            contentDescription = stringResource(id = navItem.titleResourceId),
                            tint = if (selected) Color.White else MaterialTheme.colorScheme.onPrimary
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(id = navItem.titleResourceId),
                            color = Color.White,
                            fontSize = Typography.labelSmall.fontSize
                        )
                    },
                    selectedContentColor = MaterialTheme.colorScheme.background,
                    unselectedContentColor = MaterialTheme.colorScheme.onPrimary,
                    alwaysShowLabel = false,
                    selected = currentDestination?.hierarchy?.any { it.route == navItem.route } == true,
                    onClick = {
                        navController.navigate(navItem.route) {
                            // Code and comments from: https://developer.android.com/jetpack/compose/navigation#bottom-nav
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    JourlyTheme {
        BottomNavigationBar(rememberNavController())
    }
}
