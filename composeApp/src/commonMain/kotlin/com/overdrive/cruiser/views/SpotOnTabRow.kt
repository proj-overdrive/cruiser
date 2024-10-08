package com.overdrive.cruiser.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SpotOnTabRow(tabs: List<String>, tabContent: @Composable (Int) -> Unit) {
    var selectedTabIndex by remember { mutableStateOf(0) }

    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clip(RoundedCornerShape(8.dp)),
            backgroundColor = Color.Gray.copy(alpha = 0.2f),
            indicator = {},
            divider = {}
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            if (selectedTabIndex == index) Color.White else Color.Transparent
                        )
                        .fillMaxWidth(),
                    text = {
                        Text(
                            text = title,
                            color = if (selectedTabIndex == index) Color.Black else Color.Gray,
                            style = TextStyle(fontSize = 14.sp)
                        )
                    },
                )
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        tabContent(selectedTabIndex)
    }
}

private object NoRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = Color.Unspecified

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(0.0f,0.0f,0.0f,0.0f)
}
