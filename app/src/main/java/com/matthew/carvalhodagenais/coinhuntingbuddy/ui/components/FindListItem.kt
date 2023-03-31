package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.matthew.carvalhodagenais.coinhuntingbuddy.R
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities.Find
import com.matthew.carvalhodagenais.coinhuntingbuddy.utils.DateToStringConverter
import com.matthew.carvalhodagenais.coinhuntingbuddy.utils.FindStringGenerator
import com.matthew.carvalhodagenais.coinhuntingbuddy.viewmodels.MainActivityViewModel

@Composable
fun FindListItem(
    find: Find,
    viewModel: MainActivityViewModel,
    onClick: () -> Unit,

) {
    val strArray = FindStringGenerator.generate(
        context = LocalContext.current,
        year = find.year,
        mintMark = find.mintMark,
        error = find.error,
        variety = find.variety,
    )

    Column(
        modifier = Modifier
            .clickable { onClick() }
            .padding(top = 4.dp, bottom = 4.dp)
    ) {
        val rowPadding = 16.dp
        Row(modifier = Modifier.padding(start = rowPadding)) {
            val grade = viewModel
                .getGradeById(find.gradeId!!)
                .observeAsState()

            Text(text = "${strArray[0]} - ${grade.value?.code}")
        }
        Row(
            modifier = Modifier
                .padding(
                    start = rowPadding,
                    bottom = 8.dp
                )
        ) {
            if (strArray[1] == stringResource(id = R.string.no_varieties_or_errors_label)) {
                Text(
                    text = strArray[1],
                    fontStyle = FontStyle.Italic,
                    fontSize = 14.sp
                )
            } else {
                Text(text = strArray[1], fontSize = 14.sp)
            }
        }
        Row(
            modifier = Modifier
                .padding(
                    start = rowPadding,
                    end = rowPadding
                )
        ) {
            val dateHunted = viewModel
                .getDateHuntedForFind(find.huntId)
                .observeAsState()
            val coinTypeName = viewModel
                .getCoinTypeNameById(find.coinTypeId)
                .observeAsState()

            Column(modifier = Modifier.weight(1f)) {
                HalfBoldLabel(
                    first = stringResource(id = R.string.date_hunted_half_label),
                    second = if (dateHunted.value == null) {
                        ""
                    } else {
                        DateToStringConverter.getString(dateHunted.value!!)
                    },
                    fontSize = 16,
                    modifier = Modifier
                )
            }
            Column(
                modifier = Modifier.weight(0.5f),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = if (coinTypeName.value == null) {
                        ""
                    } else {
                        coinTypeName.value!!
                    },
                    textAlign = TextAlign.End,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}