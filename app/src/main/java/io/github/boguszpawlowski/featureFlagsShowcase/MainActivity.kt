package io.github.boguszpawlowski.featureFlagsShowcase

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.spoton.featureflags.FeatureFlag.AdsNumber
import com.spoton.featureflags.FeatureFlag.AnalyticSessionFraction
import com.spoton.featureflags.FeatureFlag.ButtonName
import com.spoton.featureflags.FeatureFlag.IsFeature1Enabled
import com.spoton.featureflags.config.FeatureConfig
import io.github.boguszpawlowski.featureFlagsShowcase.control.FeatureControlScreen

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
          Text(text = "Feature Flags Showcase")
        }
      }
    ) { paddingValues ->
      FeatureControlScreen(
        modifier = Modifier.padding(paddingValues),
        featureConfig = FeatureConfig(
          IsFeature1Enabled to true,
          ButtonName to "Button Name",
          AnalyticSessionFraction to 1.0,
          AdsNumber to 2L,
        ),
        onFeatureValueChanged = { _, _ -> }
      )
    }
  }
}
