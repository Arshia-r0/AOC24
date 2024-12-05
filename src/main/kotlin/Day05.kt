package org.example

import java.io.File

fun day05() {
    val data = File("src/main/resources/day05.txt").readLines()
    val rules = data.slice(0..1175)
        .map { it.slice(0..1) to it.slice(3..4) }
    val updates = data.slice(1177..1364)
        .map { it.split(",") }
    val (correct, incorrect) = seperateUpdates(rules, updates)
    println("part1: " + correct.addMiddles())
    println("part2: " + calculateCorrectOrder(rules, incorrect))
}

fun seperateUpdates(rules: List<Pair<String, String>>, updates: List<List<String>>): Pair<List<List<String>>, List<List<String>>> =
    updates.partition { update ->
        update.forEachIndexed { i, v ->
            for(j in i+1..update.indices.last) {
                if(v to update[j] !in rules) return@partition false
            }
        }
        true
    }

fun List<List<String>>.addMiddles(): String =
    this.sumOf { it[it.size/2].toInt() }.toString()

fun calculateCorrectOrder(rules: List<Pair<String, String>>, updates: List<List<String>>): String =
    updates.sumOf { update ->
        update.forEachIndexed { i, v ->
            var state = 0
            update.forEachIndexed inner@ { j, c ->
                if(i == j) return@inner
                if(v to c in rules) state++
                else state--
            }
            if(state == 0) return@sumOf v.toInt()
        }
        0
    }.toString()
