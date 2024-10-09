package com.overdrive.cruiser.models

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UserViewModel {
    private val _rating = MutableStateFlow(3.0)
    val rating: StateFlow<Double> = _rating
}