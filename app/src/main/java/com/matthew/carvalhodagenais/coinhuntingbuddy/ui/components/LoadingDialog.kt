package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.matthew.carvalhodagenais.coinhuntingbuddy.R

@Composable
fun LoadingDialog(
    loadingState: MutableState<Boolean>
) {
    if (loadingState.value) {
        Dialog(onDismissRequest = { /* Do Nothing! */ }) {
            Surface(
                elevation = 4.dp,
                shape = RoundedCornerShape(4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 8.dp, start = 4.dp, end = 4.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(12.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.loading_dialog_label),
                        modifier = Modifier.padding(end = 12.dp)
                    )
                }
            }
        }
    }
}