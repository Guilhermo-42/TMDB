package com.arctouch.codechallenge.extensions

fun Int.minutesToHoursAndMinutes(): String {
    val minutes: Int = this % 60

    var minutesInString = minutes.toString()
    if (minutesInString.length == 1) {
        minutesInString = "0$minutesInString"
    }

    if (this >= 60) {
        val hours: Int = this / 60

        return "$hours:$minutesInString hr"
    }
    return "$minutesInString min"
}