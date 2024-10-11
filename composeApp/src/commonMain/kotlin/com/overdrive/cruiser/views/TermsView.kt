package com.overdrive.cruiser.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cruiser.composeapp.generated.resources.Res
import cruiser.composeapp.generated.resources.house_image
import org.jetbrains.compose.resources.painterResource

@Composable
fun TermsView(onAgree: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Image(
            painter = painterResource(Res.drawable.house_image),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
                .align(Alignment.BottomCenter),
            contentScale = ContentScale.Crop
        )


        Column(modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {

            Spacer(modifier = Modifier.height(140.dp))

            Text(
                text = "Terms of Service",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.Start),
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(24.dp, 24.dp, 0.dp, 0.dp))
                    .background(color = Color.Black.copy(alpha = 0.3f))
                    .padding(24.dp),
            ) {
                Box (modifier = Modifier.padding(16.dp)) {
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
                        color = Color.White
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
}
