package com.overdrive.cruiser.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SpotOnAlertDialog(title: String, text: String, onClick: () -> Unit, onDismissRequest: () -> Unit) {
    AlertDialog(
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)),
        backgroundColor = Color(0xFFF5F5F5),
        shape = RoundedCornerShape(12.dp),
        onDismissRequest = onDismissRequest,
        title = { Text(title, fontWeight = FontWeight.Bold) },
        text = { Text(text, color = Color.Black) },
        confirmButton = {
            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFF9784B),
                ),
            ) {
                Text("Confirm", color = Color.White)
            }
        },
        dismissButton = {
            Button(
                onClick = onDismissRequest,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFDDDDDD),
                ),
            ) {
                Text("Cancel")
            }
        }
    )
}
