package org.example

import java.io.File

fun day03() {
    val data = File("src/main/resources/day03.txt").readText()
    println("part1: " + part51(data))
    println("part2: " + part2(data))
}

const val mulPattern = "mul\\([0-9]{1,3},[0-9]{1,3}\\)"
const val doPattern = "do\\(\\)|don't\\(\\)"

fun part51(data: String): String {
    return getMatchesAsList(mulPattern, data)
        .sumOf { multiplyElements(it) }
        .toString()
}

fun part2(data: String): String {
    var canAdd = true
    return getMatchesAsList("$mulPattern|$doPattern", data)
        .sumOf {
            print(canAdd.toString() + "\n" + it + "\n")
            if(it.startsWith("mul") && canAdd) return@sumOf multiplyElements(it)
            when {
                it.startsWith("don't") -> canAdd = false
                it.startsWith("do") -> canAdd = true
            }
            0
        }.toString()
}

fun getMatchesAsList(pattern: String, data: String): List<String> {
    return buildList {
        Regex(pattern)
            .findAll(data)
            .forEach {
                add(it.value)
            }
    }
}

fun multiplyElements(list: String): Int {
    return list.slice(4..<list.lastIndex)
        .split(",")
        .map(String::toInt)
        .multiply() ?: 0
}

fun List<Int>.multiply(): Int? {
    if(this.isEmpty()) return null
    var result = 1
    this.forEach { result *= it }
    return result
}
