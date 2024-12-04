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
    count += getMatches(diagonal)
    return count.toString()
}

fun getMatches(list: List<String>): Int =
    list.sumOf { Regex("SAMX").findAll(it).count() } + list.sumOf { Regex("XMAS").findAll(it).count() }

fun <T> List<List<T>>.transpose(): List<List<T>> =
    (this[0].indices).map { i -> (this.indices).map { j -> this[j][i] } }

fun getAllDiagonalsMap(list: List<List<Char>>): List<String> {
    val fdiag = mutableMapOf<Int, List<Char>>()
    val bdiag = mutableMapOf<Int, List<Char>>()
    for(i in list.indices) {
        for(j in list[0].indices) {
            fdiag[i+j] = fdiag[i+j]?.let { it + list[i][j] } ?: listOf(list[i][j])
            bdiag[i-j] = bdiag[i-j]?.let { it + list[i][j] } ?: listOf(list[i][j])
        }
    }
    return fdiag.values.map { it.joinToString("") } + bdiag.values.map { it.joinToString("") }
}

fun part2(data: List<String>): String {
    var c = 0
    for(i in data.indices) {
        for (j in data[0].indices) {
            if(i == 0 || j == 0 || i == data.lastIndex || j == data[0].lastIndex  || data[i][j] != 'A') continue
            if(checkXmas(data, i, j)) c++
        }
    }
    return c.toString()
}

fun checkXmas(data: List<String>, i: Int, j: Int): Boolean {
    if(data[i-1][j-1] == 'X' || data[i-1][j-1] == 'A' || data[i-1][j+1] == 'X' || data[i-1][j+1] == 'A' || data[i+1][j-1] == 'X' || data[i+1][j-1] == 'A' || data[i+1][j+1] == 'X' || data[i+1][j+1] == 'A') return false
    if(data[i-1][j-1] == data[i+1][j+1] || data[i-1][j+1] == data[i+1][j-1]) return false
    return true
}
