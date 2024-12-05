package org.example

import java.io.File
import kotlin.math.abs

fun day01() {
    val data = File("src/main/resources/day01.txt").readLines()
    val lists = data.map {
        it.slice(0..4).toInt()
    } to data.map {
        it.slice(8..12).toInt()
    }
    println("part1: " + part51(lists))
    println("part2: " + part2(lists))
}

fun part51(data: Pair<List<Int>, List<Int>>): String {
    var result = 0
    val lists = data.first.sorted() to data.second.sorted()
    lists.first.forEachIndexed { i, c ->
        result += abs(c - lists.second[i])
    }
    return result.toString()
}

fun part2(data: Pair<List<Int>, List<Int>>): String {
    val cache = mutableMapOf<Int, Int>()
    var result = 0
    data.first.forEach {
        if (it !in cache) {
            var count = 0
            data.second.forEach { p ->
                if (it == p) count++
            }
            cache[it] = count
        }
        result += cache[it]?.times(it) ?: 0
    }
    return result.toString()
}