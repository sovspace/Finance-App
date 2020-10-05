package com.financeapp.utils

fun Double.format(digits: Int) = "%.${digits}f".format(this)
