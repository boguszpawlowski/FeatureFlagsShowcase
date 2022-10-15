package io.github.boguszpawlowski.featureFlagsShowcase.control

import android.content.SharedPreferences
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import io.github.boguszpawlowski.featureFlags.FeatureFlag
import io.github.boguszpawlowski.featureFlags.FeatureFlagType
import io.github.boguszpawlowski.featureFlags.config.FeatureConfig
import io.github.boguszpawlowski.featureFlags.firebase.Marker
import io.github.boguszpawlowski.featureFlags.provider.FeatureFlagProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun FeatureControlScreen(
  featureConfig: FeatureConfig,
  onFeatureValueChanged: (FeatureFlag<Any>, Any) -> Unit,
  modifier: Modifier = Modifier,
) {
  Surface(
    modifier = modifier
      .fillMaxSize()
      .padding(16.dp),
  ) {
    LazyColumn(
      verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      featureConfig.featureFlags.forEach { (featureFlag, value) ->
        item(featureFlag.key) {
          FeatureWrapper(name = featureFlag.name) {
            when (featureFlag.type) {
              FeatureFlagType.FloatingPoint -> FloatingPointFeature(
                featureFlag = featureFlag as FeatureFlag<Double>,
                value = value as Double,
                onValueChanged = onFeatureValueChanged,
              )
              FeatureFlagType.Logical -> LogicalFeature(
                featureFlag = featureFlag as FeatureFlag<Boolean>,
                value = value as Boolean,
                onValueChanged = onFeatureValueChanged,
              )
              FeatureFlagType.Numeric -> NumericFeature(
                featureFlag = featureFlag as FeatureFlag<Long>,
                value = value as Long,
                onValueChanged = onFeatureValueChanged,
              )
              FeatureFlagType.Text -> TextFeature(
                featureFlag = featureFlag as FeatureFlag<String>,
                value = value as String,
                onValueChanged = onFeatureValueChanged,
              )
            }
          }
        }
      }
    }
  }
}

@Composable
fun FeatureWrapper(
  name: String,
  content: @Composable BoxScope.() -> Unit,
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Box {
      content()
    }
    Text(
      text = name,
      style = MaterialTheme.typography.body1,
    )
  }
}

@Composable
fun LogicalFeature(
  featureFlag: FeatureFlag<Boolean>,
  value: Boolean,
  onValueChanged: (FeatureFlag<Boolean>, Boolean) -> Unit,
) {
  Switch(
    checked = value,
    onCheckedChange = { onValueChanged(featureFlag, it) },
  )
}

@Composable
fun TextFeature(
  featureFlag: FeatureFlag<String>,
  value: String,
  onValueChanged: (FeatureFlag<String>, String) -> Unit,
) {
  val (currentText, onTextChanged) = remember(value) { mutableStateOf(value) }

  val actions = remember {
    KeyboardActions {
      onValueChanged(featureFlag, currentText)
    }
  }

  TextField(
    value = currentText,
    onValueChange = onTextChanged,
    keyboardActions = actions,
  )
}

@Composable
fun FloatingPointFeature(
  featureFlag: FeatureFlag<Double>,
  value: Double,
  onValueChanged: (FeatureFlag<Double>, Double) -> Unit,
) {
  val (currentText, onTextChanged) = remember(value) { mutableStateOf(value.toString()) }

  val actions = remember {
    KeyboardActions {
      onValueChanged(featureFlag, currentText.toDouble())
    }
  }

  TextField(
    value = currentText,
    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
    onValueChange = onTextChanged,
    keyboardActions = actions,
  )
}

@Composable
fun NumericFeature(
  featureFlag: FeatureFlag<Long>,
  value: Long,
  onValueChanged: (FeatureFlag<Long>, Long) -> Unit,
) {
  val (currentText, onTextChanged) = remember(value) { mutableStateOf(value.toString()) }

  val actions = remember {
    KeyboardActions {
      onValueChanged(featureFlag, currentText.toLong())
    }
  }

  TextField(
    value = value.toString(),
    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
    onValueChange = onTextChanged,
    keyboardActions = actions,
  )
}

@Preview
@Composable
fun LogicalFeaturePreview() {
  MaterialTheme {
    LogicalFeature(
      featureFlag = FeatureFlag.IsFeature1Enabled,
      value = true,
      onValueChanged = { _, _ -> },
    )
  }
}

@Preview
@Composable
fun TextFeaturePreview() {
  MaterialTheme {
    TextFeature(
      featureFlag = FeatureFlag.ButtonName,
      value = "Button Name",
      onValueChanged = { _, _ -> },
    )
  }
}

@Preview
@Composable
fun FloatingPointFeaturePreview() {
  MaterialTheme {
    FloatingPointFeature(
      featureFlag = FeatureFlag.AnalyticSessionFraction,
      value = 0.5,
      onValueChanged = { _, _ -> },
    )
  }
}

@Preview
@Composable
fun NumericFeaturePreview() {
  MaterialTheme {
    NumericFeature(
      featureFlag = FeatureFlag.AdsNumber,
      value = 10,
      onValueChanged = { _, _ -> },
    )
  }
}

class FeatureControlViewModel(
  private val featureFlagProvider: FeatureFlagProvider,
  private val saveFeatureFlagValue: SaveFeatureFlagValue,
) : ViewModel() {

  private val _featureConfig = MutableStateFlow(featureFlagProvider.get())
  val featureConfig: StateFlow<FeatureConfig> = _featureConfig

  init {
    fetchFeatureConfig()
  }

  fun <T : Any> onValueChanged(featureFlag: FeatureFlag<T>, newValue: T) {
    saveFeatureFlagValue(featureFlag, newValue)
    fetchFeatureConfig()
  }

  private fun fetchFeatureConfig() {
    _featureConfig.value = featureFlagProvider.get()
  }
}

class SaveFeatureFlagValue(
  private val sharedPreferences: SharedPreferences,
) {
  operator fun invoke(flag: FeatureFlag<*>, value: Any) {
    val editor = sharedPreferences.edit()
    when (flag.type) {
      FeatureFlagType.FloatingPoint -> {
        editor.putFloat(flag.key, (value as Double).toFloat())
      }
      FeatureFlagType.Logical -> {
        editor.putBoolean(flag.key, value as Boolean)
      }
      FeatureFlagType.Numeric -> {
        editor.putLong(flag.key, value as Long)
      }
      FeatureFlagType.Text -> {
        editor.putString(flag.key, value as String)
      }
    }
    editor.commit()
  }
}

class SaveLocalConfigOverride(
  private val sharedPreferences: SharedPreferences,
) {
  operator fun invoke(flag: FeatureFlag<*>, value: Any) {
    val editor = sharedPreferences.edit()
    when (flag.type) {
      FeatureFlagType.FloatingPoint -> {
        editor.putFloat(flag.key, (value as Double).toFloat())
      }
      FeatureFlagType.Logical -> {
        editor.putBoolean(flag.key, value as Boolean)
      }
      FeatureFlagType.Numeric -> {
        editor.putLong(flag.key, value as Long)
      }
      FeatureFlagType.Text -> {
        editor.putString(flag.key, value as String)
      }
    }
    editor.commit()
  }
}
