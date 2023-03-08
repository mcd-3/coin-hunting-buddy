package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.theme.cardBackground
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.theme.cardBorder
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.theme.labelColor
import com.matthew.carvalhodagenais.coinhuntingbuddy.viewmodels.HuntActivityViewModel

// Unfortunately, we need to opt in to experimental APIs for ExposedDropdownMenuBox
// We will need to wait until Compose is in a better state before disabling this
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CoinTypeHuntPanel(
    coinKeyState: MutableState<String>,
    rollsLeftState: MutableState<Int>,
    unwrapRollOnClick: () -> Unit,
    viewModel: HuntActivityViewModel
) {
    val showAlertDialog = remember { mutableStateOf(false) }
    val currentCoinType = viewModel.getCoinTypeFromString(coinKeyState.value)
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(
                start = 12.dp,
                end = 12.dp,
                bottom = 10.dp
            ),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.cardBorder),
        elevation = 4.dp,
        backgroundColor = MaterialTheme.colors.cardBackground
    ) {
        Column {
            Row {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = coinKeyState.value,
                        fontWeight = FontWeight.Bold,
                        style = TextStyle(
                            fontSize = 18.sp,
                            color = MaterialTheme.colors.labelColor
                        ),
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier.padding(start = 16.dp, top = 8.dp).align(Alignment.Start)
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "${rollsLeftState.value} roll(s) left",
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold,
                        style = TextStyle(
                            fontSize = 18.sp,
                            color = MaterialTheme.colors.labelColor
                        ),
                        modifier = Modifier.padding(end = 16.dp, top = 8.dp).align(Alignment.End)
                    )
                }
            }
            Row(modifier = Modifier.padding(top = 32.dp, bottom = 16.dp)) {
                Column(
                    modifier = Modifier
                        .weight(0.5f)
                        .height(40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = { unwrapRollOnClick() },
                        enabled = rollsLeftState.value > 0,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 32.dp, end = 18.dp),
                    ) {
                        Text(text = "Unwrap", textAlign = TextAlign.Center)
                    }
                }
                Column(
                    modifier = Modifier
                        .weight(0.5f)
                        .height(40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = { showAlertDialog.value = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 18.dp, end = 32.dp),
                    ) {
                        Text(text = "New Find", textAlign = TextAlign.Center)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            FindsPanel(viewModel = viewModel, currentCoinType = currentCoinType)

            // Finds Form
            val yearStringState = remember { mutableStateOf("") }
            val varietyStringState = remember { mutableStateOf("") }
            val mintMarkStringState = remember { mutableStateOf("") }
            val errorStringState = remember { mutableStateOf("") }
            val gradeStringState = remember { mutableStateOf("") }
            if(showAlertDialog.value){
                AlertDialog(
                    onDismissRequest = {
                        showAlertDialog.value = false
                        yearStringState.value = ""
                        varietyStringState.value = ""
                        mintMarkStringState.value = ""
                        gradeStringState.value = ""
                        errorStringState.value = ""
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            viewModel.addFindToList(
                                year = yearStringState.value,
                                variety = varietyStringState.value,
                                mintMark = mintMarkStringState.value,
                                error = errorStringState.value,
                                grade = gradeStringState.value,
                                findType = currentCoinType
                            )
                            yearStringState.value = ""
                            varietyStringState.value = ""
                            mintMarkStringState.value = ""
                            errorStringState.value = ""
                            gradeStringState.value = ""
                            showAlertDialog.value = false
                            val toast = Toast.makeText(
                                context,
                                "Added a new find!",
                                Toast.LENGTH_LONG
                            )
                            toast.show()
                        }){
                            Text(text = "Done")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            showAlertDialog.value = false
                            yearStringState.value = ""
                            varietyStringState.value = ""
                            mintMarkStringState.value = ""
                            gradeStringState.value = ""
                            errorStringState.value = ""
                        }) {
                            Text(text = "Cancel")
                        }
                    },
                    title = { Text(text = "New Find\n") },
                    text = {
                        Column {
                            Row {
                                OutlinedTextField(
                                    value = yearStringState.value,
                                    label = { Text(text = "Year") },
                                    placeholder = { Text(text = "Year") },
                                    modifier = Modifier
                                        .weight(0.5f)
                                        .padding(end = 4.dp)
                                        .align(Alignment.Top),
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Number
                                    ),
                                    onValueChange = {
                                        val maxCharLength = 4
                                        if (it.length <= maxCharLength && !it.contains("-", true)) {
                                            if (it.length > 1 && it[0] == '0') {
                                                val newStr = it.drop(1)
                                                yearStringState.value = newStr
                                            } else {
                                                if (it.contains(" ")) {
                                                    yearStringState.value = it.replace(" ", "")
                                                } else {
                                                    yearStringState.value = it
                                                }
                                            }
                                        }

                                        if (yearStringState.value.isBlank()) {
                                            yearStringState.value = "0"
                                        }
                                    }
                                )

                                var expanded by remember { mutableStateOf(false) }
                                var selectedOption by remember { mutableStateOf(viewModel.getListOfGradeCodes()[0]) }
                                gradeStringState.value = selectedOption
                                ExposedDropdownMenuBox(
                                    expanded = expanded,
                                    onExpandedChange = {
                                        expanded = !expanded
                                    },
                                    modifier = Modifier
                                        .weight(0.5f)
                                        .padding(start = 4.dp)
                                        .align(Alignment.Bottom),
                                ) {
                                    TextField(
                                        readOnly = true,
                                        value = TextFieldValue(selectedOption),
                                        onValueChange = { },
                                        trailingIcon = {
                                            ExposedDropdownMenuDefaults.TrailingIcon(
                                                expanded = expanded
                                            )
                                        },
                                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
                                        label = { Text(text = "Grade") },
                                    )
                                    ExposedDropdownMenu(
                                        expanded = expanded,
                                        onDismissRequest = {
                                            expanded = false
                                        }
                                    ) {
                                        viewModel.getListOfGradeCodes().forEach {
                                            DropdownMenuItem(
                                                onClick = {
                                                    selectedOption = it
                                                    expanded = false
                                                    gradeStringState.value = selectedOption
                                                }
                                            ) {
                                                Text(text = it)
                                            }
                                        }
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            OutlinedTextField(
                                value = mintMarkStringState.value,
                                label = { Text(text = "Mint Mark") },
                                placeholder = { Text(text = "P") },
                                onValueChange = {
                                    mintMarkStringState.value = it
                                }
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            OutlinedTextField(
                                value = varietyStringState.value,
                                label = { Text(text = "Variety") },
                                placeholder = { Text(text = "Variety of the coin") },
                                onValueChange = {
                                    varietyStringState.value = it
                                }
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            OutlinedTextField(
                                value = errorStringState.value,
                                label = { Text(text = "Error") },
                                placeholder = { Text(text = "Error on the coin") },
                                onValueChange = {
                                    errorStringState.value = it
                                }
                            )
                        }
                    }
                )
            }
        }
    }
}