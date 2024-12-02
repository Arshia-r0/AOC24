package org.example

import java.io.File

fun day02() {
    val data = File("src/main/resources/day02.txt").readLines()
    val reportList = data.map { report ->
        report.split(" ").map(String::toInt)
    }
    println("part1:" + part1(reportList))
    println("part2:" + part2(reportList))
}

fun part1(reportList: List<List<Int>>): String {
    var safeCount = 0
    reportList.forEach { report ->
        if(isSafe(getDiffList(report))) safeCount++
    }
    return safeCount.toString()
}

fun part2(reportList: List<List<Int>>): String {
    var safeCount = 0
    reportList.forEach { report ->
        if(isSafe(getDiffList(report))) {
            safeCount++
        } else {
            if(report.indices.any {
                isSafe(getDiffList(report.filterIndexed { index, _ -> index != it }))
            }) safeCount++
        }
    }
    return safeCount.toString()
}

fun isSafe(diffList: List<Int>): Boolean =
    diffList.all{ it in 1..3 } || diffList.all{ it in -3..-1 }

fun getDiffList(reportList: List<Int>): List<Int> =
    reportList.zipWithNext { i, j -> j - i }
