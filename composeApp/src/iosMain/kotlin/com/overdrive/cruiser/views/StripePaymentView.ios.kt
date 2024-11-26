package com.overdrive.cruiser.views

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIViewController
import androidx.compose.ui.interop.UIKitViewController


@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun StripePaymentView(
    amount: Long,
    onComplete: () -> Unit,
    modifier: Modifier
) {
    val factory = remember { stripePaymentViewFactory(amount, onComplete) }

    UIKitViewController(
        factory = { factory.viewController },
        update = { },
        modifier = modifier,
    )
}

internal lateinit var stripePaymentViewFactory: (
    contentAmount: Long,
    contentOnComplete: () -> Unit
) -> StripePaymentViewFactory

interface StripePaymentViewFactory {
    val viewController: UIViewController
}
