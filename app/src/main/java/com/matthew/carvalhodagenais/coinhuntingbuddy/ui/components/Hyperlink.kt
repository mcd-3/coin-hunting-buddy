package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

@Composable
fun Hyperlink(
    text: String,
    tag: String,
    link: String,
    style: TextStyle?,
) {
    val annotatedString = buildAnnotatedString {
        pushStringAnnotation(
            tag = tag,
            annotation = link,
        )
        withStyle(style = SpanStyle(color = MaterialTheme.colors.primary)) {
            append(text)
        }
        pop()
    }

    val uriHandler = LocalUriHandler.current

    ClickableText(
        text = annotatedString,
        onClick = { offset ->
            annotatedString.getStringAnnotations(
                tag = tag,
                start = offset,
                end = offset
            ).firstOrNull()?.let {
                uriHandler.openUri(it.item)
            }
        },
        style = style ?: TextStyle()
    )
}