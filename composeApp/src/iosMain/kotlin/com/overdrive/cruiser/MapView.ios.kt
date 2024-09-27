package com.overdrive.cruiser

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitViewController
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import platform.UIKit.UIViewController

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun MapView(
    modifier: Modifier,
    contentPadding: PaddingValues,
) {
    val contentPaddingState = remember { MutableStateFlow(contentPadding) }
    val factory = remember { mapWithSwiftViewFactory(contentPaddingState) }

    UIKitViewController(
        factory = { factory.viewController },
        update = {
            contentPaddingState.value = contentPadding
        },
        modifier = modifier,
    )
}

internal lateinit var mapWithSwiftViewFactory: (
    contentPaddingState: StateFlow<PaddingValues>,
) -> MapWithSwiftViewFactory

interface MapWithSwiftViewFactory {
    val viewController: UIViewController
}
