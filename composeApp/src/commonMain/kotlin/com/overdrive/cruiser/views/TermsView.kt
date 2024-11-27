package com.overdrive.cruiser.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp

@Composable
fun TermsView(onAgree: () -> Unit, onBackClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {

        SpotOnLoginBackground(modifier = Modifier.align(Alignment.BottomCenter))

        IconButton(
            onClick = onBackClick,
            modifier = Modifier.padding(16.dp).size(24.dp).align(Alignment.TopStart),
        ) {
            Icon(
                Icons.AutoMirrored.Outlined.ArrowBack,
                contentDescription = "Back"
            )
        }

        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {

            SpotOnBranding(Modifier.align(Alignment.CenterHorizontally))

            Spacer(modifier = Modifier.height(54.dp))


            Box(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = AnnotatedString.Builder().apply {
                        append("By selecting Agree and continue below, I agree to ")
                        withStyle(style = SpanStyle(color = Color(0xFFF9784B))) {
                            append("Terms of Service")
                        }
                        append(" and ")
                        withStyle(style = SpanStyle(color = Color(0xFFF9784B))) {
                            append("Privacy Policy.")
                        }
                    }.toAnnotatedString(),
                    color = Color.Black
                )
            }

            Button(
                onClick = { onAgree() },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp)),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFF9784B),
                ),
            ) {
                Text(
                    text = "Agree and Continue",
                    color = Color.White,
                )
            }
        }
    }
}
