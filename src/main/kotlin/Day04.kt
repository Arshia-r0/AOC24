package org.example

import java.io.File

fun day04() {
    val data = File("src/main/resources/day04.txt").readLines()
    println("part1: " + part1(data))
    println("part2: " + part2(data))
}

fun part1(data: List<String>): String {
    var count = 0
    count += getMatches(data)
    val transposed = data.map{ it.toList() }
        .transpose().map { it.joinToString("") }
    count += getMatches(transposed)
    val diagonal = getDiagonalMap(data.map { it.toList() })
        .values.map { it.joinToString("") }
    count += getMatches(diagonal)
    return count.toString()
}

fun part2(data: List<String>): String {
    return ""
}

fun getMatches(list: List<String>): Int =
    list.sumOf { Regex("XMAS|SAMX").findAll(it).count() }

fun <T> List<List<T>>.transpose(): List<List<T>> =
    (this[0].indices).map { i -> (this.indices).map { j -> this[j][i] } }

fun getDiagonalMap(list: List<List<Char>>): Map<Int, List<Char>> {
    val result = mutableMapOf<Int, List<Char>>()
    for(i in list.indices) {
        for((j, v) in list[i].withIndex()) {
            val key = i - j
            result[key] = result[key]?.plus(v) ?: listOf(v)
        }
    }
    return result
}
