@file:OptIn(ExperimentalMaterialApi::class)

package io.github.boguszpawlowski.featureFlagsShowcase.hyperion

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.darkColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.lightColors
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import com.google.auto.service.AutoService
import com.willowtreeapps.hyperion.plugin.v1.Plugin
import com.willowtreeapps.hyperion.plugin.v1.PluginModule
import io.github.boguszpawlowski.featureFlagsShowcase.control.FeatureControlScreen
import io.github.boguszpawlowski.featureFlagsShowcase.feature.FeatureScreen

@AutoService(Plugin::class)
class FeatureConfigPlugin : Plugin() {
  override fun createPluginModule() = FeatureConfigModule()
}

class FeatureConfigModule : PluginModule() {
  override fun createPluginView(layoutInflater: LayoutInflater, parent: ViewGroup): View? =
    ComposeView(parent.context).apply {
      setContent {
        Surface(
          modifier = Modifier
            .fillMaxWidth()
            .height(131.dp),
          onClick = {
            parent.context.startActivity(
              Intent(parent.context, FeatureConfigActivity::class.java),
            )
          },
        ) {
          Row(
            modifier = Modifier
              .fillMaxSize()
              .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
          ) {
            Icon(imageVector = Icons.Filled.Settings, contentDescription = "Feature Settings")
            Text(text = "Set feature flag values")
          }
        }
      }
    }
}

class FeatureConfigActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      MaterialTheme(
        colors = if (isSystemInDarkTheme()) darkColors() else lightColors()
      ) {
        FeatureControlScreen(
          modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        )
      }
    }
  }
}

