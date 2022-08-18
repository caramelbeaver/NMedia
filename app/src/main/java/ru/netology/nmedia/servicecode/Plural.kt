package ru.netology.nmedia.servicecode

import kotlin.math.floor

fun main() {
    print("Введите целым числом кол-во: ")
    val amount = readLine()?.toInt() ?: return
    println("Количество ${plural(amount, 'K', 'M')}")
}

fun plural(amount: Int, sufKilo: Char, sufMillion: Char): String {
    val remainderKilo = floor(amount % 1000.toDouble() / 100).toInt()
    val remainderMillion = floor(amount % 1000000.toDouble() / 100000).toInt()
    val kilo = floor(amount / 1000.toDouble()).toInt()
    val million = floor(amount / 1000000.toDouble()).toInt()
    return when (amount) {
        in 0..999 -> amount.toString()
        in 1_000..1_099 -> "$kilo$sufKilo"
        in 1_100..9_999 -> "$kilo.$remainderKilo$sufKilo"
        in 10_000..999_999 -> "$kilo$sufKilo"
        in 1_000_000..1_099_999 -> "$million$sufMillion"
        else -> "$million.$remainderMillion$sufMillion"
    }
}