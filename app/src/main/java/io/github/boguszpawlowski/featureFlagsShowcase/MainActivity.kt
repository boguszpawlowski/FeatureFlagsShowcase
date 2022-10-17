package io.github.boguszpawlowski.featureFlagsShowcase

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.darkColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.boguszpawlowski.featureFlagsShowcase.control.FeatureControlScreen
import io.github.boguszpawlowski.featureFlagsShowcase.feature.FeatureScreen

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      MainScreen()
    }
  }
}

@Composable
fun MainScreen() {
  val navController = rememberNavController()

  MaterialTheme(
    colors = if (isSystemInDarkTheme()) darkColors() else lightColors()
  ) {
    Scaffold(
      topBar = {
        TopAppBar {
          Text(
            modifier = Modifier.padding(8.dp),
            text = "Feature Flags Showcase",
            style = MaterialTheme.typography.h6,
          )
        }
      },
      bottomBar = {
        BottomNavigation {
          BottomNavigationItem(
            selected = navController.currentDestination?.route == "Home",
            icon = {
              Icon(imageVector = Icons.Filled.Home, contentDescription = "Home")
            },
            onClick = { navController.navigate("Home") }
          )
          BottomNavigationItem(
            selected = navController.currentDestination?.route == "Settings",
            icon = {
              Icon(imageVector = Icons.Filled.Settings, contentDescription = "Settings")
            },
            onClick = { navController.navigate("Settings") }
          )
        }
      }
    ) { paddingValues ->
      NavHost(
        navController = navController,
        startDestination = "Home"
      ) {
        val modifier = Modifier.padding(paddingValues).fillMaxSize().padding(8.dp)
        composable("Home") {
          FeatureScreen(
            modifier = modifier,
          )
        }
        composable("Settings") {
          FeatureControlScreen(
            modifier = modifier,
          )
        }
      }
    }
  }
}
