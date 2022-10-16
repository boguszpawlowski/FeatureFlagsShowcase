package io.github.boguszpawlowski.featureFlagsShowcase.feature

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.getViewModel

@Composable
fun FeatureScreen(
  modifier: Modifier,
  viewModel: FeatureViewModel = getViewModel(),
) {
  val viewState by viewModel.viewState.collectAsState()

  Surface(
    modifier = modifier,
  ) {
    Column {
      if (viewState.isFeature1Enabled) {
        Button(
          modifier = Modifier.fillMaxWidth(),
          onClick = { },
        ) {
          Text(text = "Go To Feature 1")
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = { },
      ) {
        Text(text = viewState.buttonName)
      }

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = "Analytics sessionChance: ${viewState.analyticsFraction * 100}%",
        style = MaterialTheme.typography.h6,
      )

      Spacer(modifier = Modifier.height(16.dp))

      LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 120.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
      ) {
        items(viewState.adsNumber.toInt()) {
          Card {
            Text(
              modifier = Modifier.padding(8.dp),
              text = "Ad no. ${it + 1}",
            )
          }
        }
      }
    }
  }
}
