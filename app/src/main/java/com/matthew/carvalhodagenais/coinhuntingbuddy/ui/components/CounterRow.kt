package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.theme.labelColor
import com.matthew.carvalhodagenais.coinhuntingbuddy.utils.TextNumberConverter

@Composable
fun CounterRow(label: String, mutVal: MutableState<TextFieldValue>) {
    val maxCharLength = 3
    val context = LocalContext.current
    Row (modifier = Modifier
        .fillMaxWidth()
        .height(40.dp)
        .padding(start = 24.dp, end = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Row {
            Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.Start) {
                Text(
                    text = label,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.weight(0.4f).padding(top = 6.dp),
                )
            }
            Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
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
                            color = MaterialTheme.colors.labelColor
                        ),
                        onValueChange = {
                            if (it.text.length <= maxCharLength) {
                                if (it.text.contains("-", true)) {
                                    val toast = Toast.makeText(
                                        context,
                                        "Negative rolls are not allowed.",
                                        Toast.LENGTH_SHORT
                                    )
                                    mutVal.value = TextFieldValue(
                                        text = "0",
                                        selection = TextRange(1)
                                    )
                                    toast.show()
                                } else {
                                    if (it.text.length > 1 && it.text[0] == '0') {
                                        val newStr = it.text.drop(1)
                                        mutVal.value = TextFieldValue(
                                            text = newStr,
                                            selection = TextRange(newStr.length)
                                        )
                                    } else {
                                        if (it.text.contains(" ")) {
                                            mutVal.value = TextFieldValue(
                                                text = it.text.replace(" ", ""),
                                                selection = TextRange(it.text.length)
                                            )
                                        } else {
                                            mutVal.value = it
                                        }
                                    }
                                }
                            }

                            if (mutVal.value.text.isBlank()) {
                                mutVal.value = TextFieldValue(
                                    text = "0",
                                    selection = TextRange(1)
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        modifier = Modifier
                            .heightIn(10.dp, Dp.Infinity)
                            .widthIn(10.dp, 80.dp)
                            .padding(start = 10.dp, end = 10.dp, top = 4.dp)
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
    }
}