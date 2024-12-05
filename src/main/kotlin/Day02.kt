package org.example

import java.io.File

fun day02() {
    val data = File("src/main/resources/day02.txt").readLines()
    val reportList = data.map { report ->
        report.split(" ").map(String::toInt)
    }
    println("part1:" + part51(reportList))
    println("part2:" + part2(reportList))
}

fun part51(reportList: List<List<Int>>): String =
    reportList.count { isSafe(getDiffList(it)) }.toString()

fun part2(reportList: List<List<Int>>): String =
    reportList.count { report ->
        report.indices.any { r ->
            isSafe(getDiffList(report.filterIndexed { index, _ -> index != r}))
        }
    }.toString()

fun isSafe(diffList: List<Int>): Boolean =
    diffList.all{ it in 1..3 } || diffList.all{ it in -3..-1 }

fun getDiffList(reportList: List<Int>): List<Int> =
    reportList.zipWithNext { i, j -> j - i }
