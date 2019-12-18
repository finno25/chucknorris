package com.rafinno.chucknorris.commons

import java.text.ParseException
import java.text.SimpleDateFormat

object Utilities {
    fun convertDatetime(
        value: String,
        originalFormat: String,
        resultFormat: String
    ): String {
        try {
            var format = SimpleDateFormat(originalFormat)
            val date = format.parse(value)
            format = SimpleDateFormat(resultFormat)
            return format.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return originalFormat
    }
}