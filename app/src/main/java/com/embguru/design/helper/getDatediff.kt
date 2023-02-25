package com.embguru.design.helper

import android.os.Environment
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class getDatediff {
    val dateFormat = SimpleDateFormat("yyyy-dd-MM HH:mm:ss")
    fun getTimeDifferenceInDay(date: String): Long {
        val date2 = dateFormat.parse(date)
        val date1 = Date()
        val diffInMillis = date1.time - date2.time
        return (diffInMillis / (1000 * 60 * 60 * 24))
    }

    fun formatDate(inputDateString: String): String {
        val inputDateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.getDefault())
        val outputDateFormat = SimpleDateFormat("dd-MMM-yyyy hh:mm a", Locale.getDefault())

        val inputDate = inputDateFormat.parse(inputDateString)
        return outputDateFormat.format(inputDate)
    }

    fun getTimeDifference(date: String): String {
        val date2 = dateFormat.parse(date)
        val date1 = Date()
        val diffInMillis = date1.time - date2.time

        val diffInSeconds = diffInMillis / 1000
        val diffInMinutes = diffInSeconds / 60
        val diffInHours = diffInMinutes / 60
        val diffInDays = diffInHours / 24
        val diffInWeek = diffInDays / 7
        val diffInMonth = diffInWeek / 4
        val diffInYear = diffInMonth / 12

        if (diffInYear > 0)
            return "$diffInYear Y"

        if (diffInMonth > 0)
            return "$diffInMonth M"

        if (diffInWeek > 0)
            return "$diffInWeek W"

        if (diffInDays > 0)
            return "$diffInDays D"

        if (diffInHours > 0)
            return "$diffInHours h"

        if (diffInMinutes > 0)
            return "$diffInMinutes m"

        return "$diffInSeconds s"
    }

    fun fileExistOrNot(categoryName: String, folderName: String): Boolean {
        val path =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path + File.separator + categoryName + "_" + folderName + ".zip"
        val file = File(path)
        return file.isFile

    }

    fun saveFileInSharePrance(){

    }
}