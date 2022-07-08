package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.matthew.carvalhodagenais.coinhuntingbuddy.utils.TextNumberConverter

@Composable
fun CounterRow(label: String, mutVal: MutableState<TextFieldValue>) {
    val maxCharLength = 3
    val context = LocalContext.current
    Row (modifier = Modifier
        .fillMaxWidth()
        .height(60.dp)
        .padding(start = 24.dp, end = 24.dp, bottom = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = label,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(0.4f),
        )
        Row (
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.6f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            RoundCoinCounterButton(
                onClick = {
                    var number = TextNumberConverter.textFieldStringToInt(mutVal.value)
                    if (number != null && number > 0) {
                        number -= 1
                        mutVal.value = TextFieldValue(text = number.toString())
                    } else {
                        if (number == null) {
                            val toast = Toast.makeText(
                                context,
                                "Not a number.",
                                Toast.LENGTH_SHORT
                            )
                            toast.show()
                        } else {
                            val toast = Toast.makeText(
                                context,
                                "Number of rolls cannot be lower than 0.",
                                Toast.LENGTH_SHORT
                            )
                            toast.show()
                        }
                    }
                },
                isIncrement = false
            )
            BasicTextField(
                value = mutVal.value,
                singleLine = true,
                textStyle = TextStyle(
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                ),
                onValueChange = {
                    if (it.text.length <= maxCharLength) {
                        mutVal.value = it
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier
                    .heightIn(10.dp, Dp.Infinity)
                    .widthIn(10.dp, 80.dp)
                    .padding(start = 10.dp, end = 10.dp)
            )
            RoundCoinCounterButton(
                onClick = {
                    var number = TextNumberConverter.textFieldStringToInt(mutVal.value)
                    if (number != null && number < 999) {
                        number += 1
                        mutVal.value = TextFieldValue(text = number.toString())
                    } else {
                        if (number == null) {
                            val toast = Toast.makeText(
                                context,
                                "Not a number.",
                                Toast.LENGTH_SHORT
                            )
                            toast.show()
                        } else {
                            val toast = Toast.makeText(
                                context,
                                "Number of rolls cannot be higher than 999.",
                                Toast.LENGTH_SHORT
                            )
                            toast.show()
                        }
                    }
                },
                isIncrement = true
            )
        }
    }
}