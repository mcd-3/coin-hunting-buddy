package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.matthew.carvalhodagenais.coinhuntingbuddy.R

@Composable
fun InfoAlertDialog(openState: MutableState<Boolean>) {
    if (openState.value) {
        AlertDialog(
            onDismissRequest = {
                openState.value = false
            },
            title = {
                Text(text = stringResource(id = R.string.info_prompt_title))
            },
            text = {
                val bullet = stringResource(id = R.string.small_bullet)
                val messages = listOf(
                    stringResource(id = R.string.info_1_label),
                    stringResource(id = R.string.info_2_label),
                    stringResource(id = R.string.info_3_label),
                    stringResource(id = R.string.info_4_label),
                    stringResource(id = R.string.info_5_label),
                    stringResource(id = R.string.info_6_label),
                )

                Text(
                    buildAnnotatedString {
                        messages.forEach {
                            withStyle(
                                style = ParagraphStyle(
                                    textIndent = TextIndent(restLine = 16.sp)
                                )
                            ) {
                                append(bullet)
                                append("\t\t")
                                append(it)
                                append("\n")
                            }
                        }
                    }
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openState.value = false
                    }
                ) {
                    Text(stringResource(id = R.string.ok_prompt))
                }
            },
        )
    }
}