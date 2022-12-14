@file:OptIn(ExperimentalComposeUiApi::class)

package io.github.boguszpawlowski.featureFlagsShowcase.control

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.boguszpawlowski.featureFlags.FeatureFlag
import io.github.boguszpawlowski.featureFlags.FeatureFlagType
import org.koin.androidx.compose.getViewModel

@Composable
fun FeatureControlScreen(
  modifier: Modifier = Modifier,
  viewModel: FeatureControlViewModel = getViewModel(),
) {
  val viewState by viewModel.viewState.collectAsState()
  val featureConfig = viewState.featureConfig
  val isUsingLocalValues = viewState.isUsingLocalValue

  Surface(
    modifier = modifier,
  ) {
    LazyColumn(
      verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      item {
        LocalConfigOverrideControl(
          value = isUsingLocalValues,
          onValueChanged = viewModel::onLocalOverrideChanged,
        )
      }
      featureConfig.featureFlags.forEach { (featureFlag, value) ->
        item(featureFlag.key) {
          FeatureWrapper {
            when (featureFlag.type) {
              FeatureFlagType.FloatingPoint -> FloatingPointFeature(
                featureFlag = featureFlag as FeatureFlag<Float>,
                value = value as Float,
                onValueChanged = viewModel::onValueChanged,
                isUsingLocalValues = isUsingLocalValues,
              )
              FeatureFlagType.Logical -> LogicalFeature(
                featureFlag = featureFlag as FeatureFlag<Boolean>,
                value = value as Boolean,
                onValueChanged = viewModel::onValueChanged,
                isUsingLocalValues = isUsingLocalValues,
              )
              FeatureFlagType.Numeric -> NumericFeature(
                featureFlag = featureFlag as FeatureFlag<Long>,
                value = value as Long,
                onValueChanged = viewModel::onValueChanged,
                isUsingLocalValues = isUsingLocalValues,
              )
              FeatureFlagType.Text -> TextFeature(
                featureFlag = featureFlag as FeatureFlag<String>,
                value = value as String,
                onValueChanged = viewModel::onValueChanged,
                isUsingLocalValues = isUsingLocalValues,
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
  content: @Composable RowScope.() -> Unit,
) {
  Card(
    modifier = Modifier.fillMaxWidth(),
  ) {
    Row(
      modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically,
    ) {
      content()
    }
  }
}

@Composable
fun LocalConfigOverrideControl(
  value: Boolean,
  onValueChanged: (Boolean) -> Unit,
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Switch(
      checked = value,
      onCheckedChange = onValueChanged,
    )
    Text(
      text = "Use local Feature Config",
      style = MaterialTheme.typography.body1,
    )
  }
}

@Composable
fun LogicalFeature(
  featureFlag: FeatureFlag<Boolean>,
  value: Boolean,
  onValueChanged: (FeatureFlag<Boolean>, Boolean) -> Unit,
  isUsingLocalValues: Boolean,
) {
  Switch(
    checked = value,
    enabled = isUsingLocalValues,
    onCheckedChange = { onValueChanged(featureFlag, it) },
  )
  Spacer(modifier = Modifier.width(8.dp))
  Text(
    text = featureFlag.name,
    style = MaterialTheme.typography.body1,
  )
}

@Composable
fun TextFeature(
  featureFlag: FeatureFlag<String>,
  value: String,
  onValueChanged: (FeatureFlag<String>, String) -> Unit,
  isUsingLocalValues: Boolean,
) {
  val (currentText, onTextChanged) = remember(value) { mutableStateOf(value) }
  val keyboardController = LocalSoftwareKeyboardController.current

  val actions = KeyboardActions {
    keyboardController?.hide()
    onValueChanged(featureFlag, currentText)
  }

  TextField(
    value = currentText,
    modifier = Modifier.fillMaxWidth(),
    enabled = isUsingLocalValues,
    onValueChange = onTextChanged,
    label = {
      Text(text = featureFlag.name)
    },
    keyboardOptions = KeyboardOptions.Default.copy(
      imeAction = ImeAction.Done,
    ),
    keyboardActions = actions,
  )
}

@Composable
fun FloatingPointFeature(
  featureFlag: FeatureFlag<Float>,
  value: Float,
  onValueChanged: (FeatureFlag<Float>, Float) -> Unit,
  isUsingLocalValues: Boolean,
) {
  val (currentText, onTextChanged) = remember(value) { mutableStateOf(value.toString()) }
  val keyboardController = LocalSoftwareKeyboardController.current

  val actions = KeyboardActions {
    keyboardController?.hide()
    currentText.toFloatOrNull()?.let {
      onValueChanged(featureFlag, it)
    }
  }

  TextField(
    value = currentText,
    modifier = Modifier.fillMaxWidth(),
    enabled = isUsingLocalValues,
    label = {
      Text(text = featureFlag.name)
    },
    keyboardOptions = KeyboardOptions.Default.copy(
      keyboardType = KeyboardType.Decimal,
      imeAction = ImeAction.Done,
    ),
    onValueChange = onTextChanged,
    keyboardActions = actions,
  )
}

@Composable
fun NumericFeature(
  featureFlag: FeatureFlag<Long>,
  value: Long,
  onValueChanged: (FeatureFlag<Long>, Long) -> Unit,
  isUsingLocalValues: Boolean,
) {
  val (currentText, onTextChanged) = remember(value) { mutableStateOf(value.toString()) }
  val keyboardController = LocalSoftwareKeyboardController.current

  val actions = KeyboardActions {
    keyboardController?.hide()
    currentText.toLongOrNull()?.let {
      onValueChanged(featureFlag, it)
    }
  }

  TextField(
    value = currentText,
    modifier = Modifier.fillMaxWidth(),
    enabled = isUsingLocalValues,
    label = {
      Text(text = featureFlag.name)
    },
    keyboardOptions = KeyboardOptions.Default.copy(
      keyboardType = KeyboardType.Number,
      imeAction = ImeAction.Done,
    ),
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
      isUsingLocalValues = true,
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
      isUsingLocalValues = true,
    )
  }
}

@Preview
@Composable
fun FloatingPointFeaturePreview() {
  MaterialTheme {
    FloatingPointFeature(
      featureFlag = FeatureFlag.AnalyticSessionFraction,
      value = 0.5f,
      onValueChanged = { _, _ -> },
      isUsingLocalValues = true,
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
      isUsingLocalValues = true,
    )
  }
}
