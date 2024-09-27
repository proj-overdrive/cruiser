package com.overdrive.cruiser

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.overdrive.cruiser.models.Spot

@Composable
expect fun SpotMapView(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(all = 0.dp),
    spots: List<Spot> = emptyList(),
)