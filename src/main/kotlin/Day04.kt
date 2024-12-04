package org.example

import java.io.File

fun day04() {
    val data = File("src/main/resources/day04.txt").readLines()
    println("part1: " + part1(data))
    println("part2: " + part2(data))
}

fun part1(data: List<String>): String {
    var count = 0
    val transposed = data.map{ it.toList() }
        .transpose().map { it.joinToString("") }
    val diagonal = getAllDiagonalsMap(data.map { it.toList() })
    count += getMatches(data)
    count += getMatches(transposed)
    count += getMatches(diagonal.first)
    count += getMatches(diagonal.second)
    return count.toString()
}

fun part2(data: List<String>): String {
    return ""
}

fun getMatches(list: List<String>): Int =
    list.sumOf { Regex("SAMX").findAll(it).count() } + list.sumOf { Regex("XMAS").findAll(it).count() }

fun <T> List<List<T>>.transpose(): List<List<T>> =
    (this[0].indices).map { i -> (this.indices).map { j -> this[j][i] } }

fun getAllDiagonalsMap(list: List<List<Char>>): Pair<List<String>, List<String>> {
    val row = list.size
    val col = list[0].size
    val fdiag = mutableMapOf<Int, List<Char>>()
    val bdiag = mutableMapOf<Int, List<Char>>()
    for(i in 0..<row) {
        for(j in 0..<col) {
            fdiag[i+j] = fdiag[i+j]?.let { it + list[i][j] } ?: listOf(list[i][j])
            bdiag[i-j] = bdiag[i-j]?.let { it + list[i][j] } ?: listOf(list[i][j])
        }
    }
    return fdiag.values.map { it.joinToString("") } to bdiag.values.map { it.joinToString("") }
}
