package com.overdrive.cruiser.views

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Displays a button to pay with Stripe.
 *
 * @param onComplete Callback to be invoked when the payment is completed successfully.
 */
@Composable
expect fun StripePaymentView(
    amount: Long,
    onComplete: () -> Unit,
    modifier: Modifier
)
