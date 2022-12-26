package com.yayarh.pvccalculator

object Extensions {

    fun String?.toIntOrZero(): Int = this?.toIntOrNull() ?: 0


}