package com.crgarridos.timber2html.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

internal fun Date.format(pattern: String): String {
    return SimpleDateFormat(pattern, Locale.ENGLISH).format(this)
}