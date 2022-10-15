package io.github.boguszpawlowski.featureFlagsShowcase

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.boguszpawlowski.featureFlagsShowcase.control.FeatureControlScreen
import io.github.boguszpawlowski.featureFlagsShowcase.control.FeatureControlViewModel
import org.koin.androidx.compose.getViewModel

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
      }
    ) { paddingValues ->

      val viewModel = getViewModel<FeatureControlViewModel>()

      val viewState by viewModel.viewState.collectAsState()

      FeatureControlScreen(
        modifier = Modifier.padding(paddingValues),
        featureConfig = viewState.featureConfig,
        isUsingLocalValues = viewState.isUsingLocalValue,
        onFeatureValueChanged = viewModel::onValueChanged,
        onUsingLocalConfigChanged = viewModel::onLocalOverrideChanged,
      )
    }
  }
}
