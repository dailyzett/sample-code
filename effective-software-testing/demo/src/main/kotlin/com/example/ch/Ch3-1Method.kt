package com.example.ch

fun leftPad(str: String?, size: Int, padStr: String?): String? {

    if (str == null) {
        return null
    }

    var mutablePadStr = padStr

    if (mutablePadStr.isNullOrEmpty()) {
        mutablePadStr = " "
    }

    val padLen = mutablePadStr.length
    val strLen = str.length
    val pads = size - strLen

    if (pads <= 0) {
        return str
    }

    if (pads == padLen) {
        return mutablePadStr + str
    } else if (pads < padLen) {
        return mutablePadStr.substring(0, pads) + str
    } else {
        val padding = CharArray(pads)
        val padChars = mutablePadStr.toCharArray()

        for (i in 0 until pads) {
            padding[i] = padChars[i % padLen]
        }

        return String(padding).plus(str)
    }
}