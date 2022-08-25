package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities.Grade
import com.matthew.carvalhodagenais.coinhuntingbuddy.dataobjects.Find
import com.matthew.carvalhodagenais.coinhuntingbuddy.utils.MoneyStringToSymbolUtil
import com.matthew.carvalhodagenais.coinhuntingbuddy.viewmodels.HuntActivityViewModel

// Unfortunately, we need to opt in to experimental APIs for ExposedDropdownMenuBox
// We will need to wait until Compose is in a better state before disabling this
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CoinTypeHuntPanel(
    coinKeyState: MutableState<String>,
    rollsLeftState: MutableState<Int>,
    unwrapRollOnClick: () -> Unit,
    listOfFinds: MutableList<Find>,
    viewModel: HuntActivityViewModel = viewModel()
) {
    val showAlertDialog = remember { mutableStateOf(false) }
    val currentCoinType = MoneyStringToSymbolUtil.stringToCoinType(coinKeyState.value)
    val context = LocalContext.current
    val gradesState: State<List<Grade>> = viewModel.getGrades().observeAsState(initial = listOf())

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(
                start = 10.dp,
                end = 10.dp,
                bottom = 10.dp
            )
            .border(1.dp, Color(0xFFCECECE)),
        elevation = 10.dp
    ) {
        Column {
            Text(
                text = coinKeyState.value,
                fontWeight = FontWeight.Bold,
                style = TextStyle(
                    fontSize = 18.sp,
                    color = Color(0xFF353535)
                ),
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.padding(start = 16.dp, top = 8.dp)
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(0.55f)
                        .height(52.dp),
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        text = rollsLeftState.value.toString(),
                        fontWeight = FontWeight.Bold,
                        style = TextStyle(
                            fontSize = 42.sp,
                            color = Color(0xFF353535)
                        ),
                        modifier = Modifier
                            .padding(start = 8.dp, top = 4.dp)
                            .weight(0.7f)
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(0.45f)
                        .height(52.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(text = " rolls left")
                }
            }
            Row(modifier = Modifier.padding(top = 32.dp)) {
                Column(
                    modifier = Modifier
                        .weight(0.5f)
                        .height(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = { unwrapRollOnClick() },
                        enabled = rollsLeftState.value > 0,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 32.dp, end = 18.dp),
                    ) {
                        Text(text = "Unwrap Roll", textAlign = TextAlign.Center)
                    }
                }
                Column(
                    modifier = Modifier
                        .weight(0.5f)
                        .height(32.dp),
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

            FindsPanel(findsList = listOfFinds, currentCoinType = currentCoinType)

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
                            val find = Find(
                                year = yearStringState.value,
                                variety = varietyStringState.value,
                                mintMark = mintMarkStringState.value,
                                error = errorStringState.value,
                                grade = gradeStringState.value,
                                findType = currentCoinType
                            )
                            listOfFinds.add(find)
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
                                var selectedOption by remember { mutableStateOf(gradesState.value[0]) }
                                gradeStringState.value = selectedOption.code
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
                                        value = TextFieldValue(selectedOption.code),
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
                                        gradesState.value.forEach {
                                            DropdownMenuItem(
                                                onClick = {
                                                    selectedOption = it
                                                    expanded = false
                                                    gradeStringState.value = selectedOption.code
                                                }
                                            ) {
                                                Text(text = it.code)
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