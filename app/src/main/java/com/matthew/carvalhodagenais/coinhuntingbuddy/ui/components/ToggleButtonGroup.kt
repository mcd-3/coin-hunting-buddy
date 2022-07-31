/* File taken and modified from https://github.com/MakeItEasyDev/Jetpack-Compose-Toggle-Button
 *
 * Copyright 2020 MakeItEasyDev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ToggleButtonGroup(
    options: Array<String>,
    isMultiSelect: Boolean,
    selectedOption: String = "",
    selectedOptionState: MutableState<String>? = null
) {
    val selectionType: SelectionType =
        if (isMultiSelect) SelectionType.MULTIPLE
        else SelectionType.SINGLE

    ToggleButton(
        options = options,
        type = selectionType,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                end = 12.dp,
                start = 12.dp,
                bottom = 8.dp
            ),
        onClick = {  },
        selectedOption = selectedOption,
        selectedOptionState = selectedOptionState
    )
}

private enum class SelectionType {
    SINGLE, MULTIPLE
}

@Composable
private fun SelectionItem(
    option: String,
    selected: Boolean,
    onClick: (option: String) -> Unit = {}
) {
    Button(
        onClick = { onClick(option) },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.background
        ),
        shape = RoundedCornerShape(20),
        elevation = ButtonDefaults.elevation(0.dp, 0.dp),
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier.padding(14.dp, 0.dp)
    ) {
        Row(
            modifier = Modifier.padding(0.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = option,
                color = if (selected) Color.Blue else Color.LightGray,
                modifier = Modifier.padding(0.dp)
            )
        }
    }
}

@Composable
private fun ToggleButton(
    options: Array<String>,
    modifier: Modifier = Modifier,
    type: SelectionType = SelectionType.SINGLE,
    onClick: (selectedOptions: Array<String>) -> Unit = {},
    selectedOption: String,
    selectedOptionState: MutableState<String>?
) {
    val state = remember { mutableStateMapOf<String, String>() }
    var selectedFlag = false

    OutlinedButton(
        onClick = {  },
        border = BorderStroke(1.dp, Color.LightGray),
        shape = RoundedCornerShape(20),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.LightGray),
        contentPadding = PaddingValues(0.dp, 0.dp),
        modifier = modifier
            .padding(0.dp)
            .height(52.dp)
    ) {
        if (options.isEmpty()) {
            return@OutlinedButton
        }

        // Auto select option if given
        if (selectedOption != "" && !selectedFlag) {
            state[selectedOption] = selectedOption
            selectedFlag = true

            if (selectedOptionState != null) {
                selectedOptionState.value = selectedOption
            }
        }

        val onItemClick: (option: String) -> Unit = { option ->
            if (type == SelectionType.SINGLE) {
                options.forEach {
                    val key = it
                    if (key == option) {
                        state[key] = option
                        if (selectedOptionState != null) {
                            selectedOptionState.value = option
                        }
                    } else {
                        state.remove(key)
                    }
                }
            } else {
                val key = option
                if (!state.contains(key)) {
                    state[key] = option
                    if (selectedOptionState != null) {
                        selectedOptionState.value = option
                    }
                } else {
                    state.remove(key)
                }
            }
            onClick(state.values.toTypedArray())
        }

        if (options.size == 1) {
            val option = options.first()

            SelectionItem(
                option = option,
                selected = state.contains(option),
                onClick = onItemClick
            )
            return@OutlinedButton
        }

        val first = options.first()
        val last = options.last()
        val middle = options.slice(1..options.size - 2)

        SelectionItem(
            option = first,
            selected = state.contains(first),
            onClick = onItemClick
        )

        Divider(modifier = Modifier
            .fillMaxHeight()
            .width(2.dp))

        middle.map { option ->
            SelectionItem(
                option = option,
                selected = state.contains(option),
                onClick = onItemClick
            )
            Divider(modifier = Modifier
                .fillMaxHeight()
                .width(2.dp))
        }
        SelectionItem(
            option = last,
            selected = state.contains(last),
            onClick = onItemClick
        )
    }
}