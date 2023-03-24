package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.theme.cardBorder
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.theme.danger
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.theme.dangerSecondary
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.theme.secondaryText

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingsButton(
    topText: String,
    bottomText: String,
    onClick: () -> Unit,
    isDanger: Boolean = false,
    optionalText: String? = null
) {
    val borderColor = if (isDanger) {
        MaterialTheme.colors.danger
    } else {
        MaterialTheme.colors.cardBorder
    }

    val hasOptionalString = !optionalText.isNullOrEmpty()
    
    Column {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 12.dp,
                    end = 12.dp,
                    bottom = if (hasOptionalString) 2.dp else 10.dp
                ),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(width = 1.dp, color = borderColor),
            elevation = 0.dp,
            backgroundColor = Color.Transparent,
            onClick = onClick
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .padding(start = 12.dp, end = 12.dp, top = 8.dp, bottom = 4.dp)
                ) {
                    if (isDanger) {
                        Text(topText, fontSize = 18.sp, color = MaterialTheme.colors.danger)
                    } else {
                        Text(topText, fontSize = 18.sp)
                    }
                }

                if (isDanger) {
                    Divider(
                        modifier = Modifier
                            .padding(start = 12.dp, end = 12.dp),
                        color = MaterialTheme.colors.dangerSecondary
                    )
                } else {
                    Divider(modifier = Modifier.padding(start = 12.dp, end = 12.dp))
                }

                Row(
                    modifier = Modifier
                        .padding(start = 12.dp, end = 12.dp, top = 4.dp, bottom = 8.dp)
                ) {
                    if (isDanger) {
                        Text(bottomText, color = MaterialTheme.colors.dangerSecondary)
                    } else {
                        Text(bottomText, color = MaterialTheme.colors.secondaryText)
                    }
                }
            }
        }

        if (hasOptionalString) {
            Text(
                text = optionalText!!,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(
                    bottom = 12 .dp,
                    start = 26.dp,
                    end = 26.dp
                ),
                fontSize = 12.sp,
                color = MaterialTheme.colors.secondaryText,
                textAlign = TextAlign.Center
            )
        }
    }
}