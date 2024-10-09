package com.overdrive.cruiser.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cruiser.composeapp.generated.resources.Res
import cruiser.composeapp.generated.resources.star_filled
import cruiser.composeapp.generated.resources.star_outline
import org.jetbrains.compose.resources.vectorResource

@Composable
fun RatingStarView(roundedRating: Int) {
    for (i in 0 until roundedRating) {
        Image(
            modifier = Modifier.size(20.dp),
            imageVector = vectorResource(Res.drawable.star_filled),
            contentDescription = null
        )
    }

    for (i in roundedRating until 5) {
        Image(
            modifier = Modifier.size(20.dp),
            imageVector = vectorResource(Res.drawable.star_outline),
            contentDescription = null
        )
    }
}