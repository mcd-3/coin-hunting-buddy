package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@Composable
fun ConfirmCancelAlertDialog(
    title: String,
    body: String,
    confirmLabel: String,
    cancelLabel: String,
    toggledState: MutableState<Boolean>,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { toggledState.value = false },
        confirmButton = {
            TextButton(onClick = onConfirm){
                Text(text = confirmLabel)
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text(text = cancelLabel)
            }
        },
        title = { Text(text = title) },
        text = { Text(text = body) }
    )
}