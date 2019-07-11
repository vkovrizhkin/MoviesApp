package com.kovrizhkin.moviesapp.common.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateHelper {

    private const val INPUT_FORMAT_PATTERN = "yyyy-MM-dd"

    private const val DISPLAY_FORMAT_PATTERN = "d MMMM yyyy"

    private const val LANG = "ru"

    fun formatDateToDisplay(inputDate: String): String {

        if (inputDate.isEmpty()) return ""
        try {
            val inputFormat = SimpleDateFormat(INPUT_FORMAT_PATTERN, Locale(LANG))
            val outputFormat = SimpleDateFormat(DISPLAY_FORMAT_PATTERN, Locale(LANG))

            val date = inputFormat.parse(inputDate)

            return outputFormat.format(date)

        } catch (e: ParseException) {
            throw Throwable(e)
        }
    }
}