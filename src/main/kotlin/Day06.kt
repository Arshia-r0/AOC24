package org.example

import java.io.File

fun day06() {
    val data = File("src/main/resources/day06.txt").readLines()
    val map = data.map { it.toMutableList() }.toMutableList()
    val mapDimensions = map.indices.last to map[0].indices.last
    var guardPosition: Pair<Int, Int> = 0 to 0
    for (i in 0..mapDimensions.first) {
        for (j in 0..mapDimensions.second) {
            if (map[i][j] == '^') guardPosition = i to j
            else continue
        }
    }
    println("part1: " + part1(map, guardPosition, mapDimensions))
    println("part2: " + part2(map, guardPosition, mapDimensions))
}

fun part1(
    initialMap: List<List<Char>>,
    initialGuardPosition: Pair<Int, Int>,
    mapDimensions: Pair<Int, Int>
): String {
    var guardDirection: Direction = Direction.Up
    val map = initialMap.map { it.toMutableList() }.toMutableList()
    var guardPosition = initialGuardPosition
    var counter = 0
    while (!guardPosition.isLeaving(guardDirection, mapDimensions)) {
        if (map.get(guardPosition) != 'X') {
            map[guardPosition.first][guardPosition.second] = 'X'
            counter++
        }
        var nextPosition = guardPosition.nextPosition(guardDirection)
        while (initialMap.get(nextPosition) == '#') {
            guardDirection = guardDirection.changeDirection()
            nextPosition = guardPosition.nextPosition(guardDirection)
        }
        guardPosition = nextPosition
    }
    counter++
    return counter.toString()
}

fun List<List<Char>>.get(position: Pair<Int, Int>): Char =
    this[position.first][position.second]

fun Pair<Int, Int>.nextPosition(dir: Direction): Pair<Int, Int> =
    when (dir) {
        Direction.Up -> first - 1 to second
        Direction.Down -> first + 1 to second
        Direction.Left -> first to second - 1
        Direction.Right -> first to second + 1
    }

fun Pair<Int, Int>.isLeaving(dir: Direction, mapDimensions: Pair<Int, Int>): Boolean =
    when (dir) {
        Direction.Up -> first == 0
        Direction.Down -> first == mapDimensions.first
        Direction.Left -> second == 0
        Direction.Right -> second == mapDimensions.second
    }

enum class Direction {
    Up, Right, Down, Left
}

fun Direction.changeDirection(): Direction =
    Direction.entries[(ordinal + 1) % 4]

fun part2(
    initialMap: MutableList<MutableList<Char>>,
    initialGuardPosition: Pair<Int, Int>,
    mapDimensions: Pair<Int, Int>
): String {
    var counter = 0
    for (i in 0..mapDimensions.first) {
        loop@for (j in 0..mapDimensions.second) {
            if (initialMap[i][j] != '.') continue@loop
            var guardPosition = initialGuardPosition
            var guardDirection = Direction.Up
            val map = initialMap.map { it.toMutableList() }.toMutableList()
            var shouldContinue = true
            map[i][j] = '#'
            while(shouldContinue) {
                var nextPosition = guardPosition.nextPosition(guardDirection)
                while(map.get(nextPosition) == '#') {
                    guardDirection = guardDirection.changeDirection()
                    nextPosition = guardPosition.nextPosition(guardDirection)
                }
                if(guardDirection == Direction.Right || guardDirection == Direction.Left) {
                    map[guardPosition.first][guardPosition.second] = if(map.get(guardPosition) == '.') '-' else '+'
                }
                if(guardDirection == Direction.Up || guardDirection == Direction.Down) {
                    map[guardPosition.first][guardPosition.second] = if(map.get(guardPosition) == '.') '|' else '+'
                }
                if(map.get(nextPosition) == '^' || map.get(nextPosition) == '+')
                    shouldContinue = false
                guardPosition = nextPosition
                if(guardPosition.isLeaving(guardDirection, mapDimensions))
                    continue@loop
            }
            counter++
        }
    }
    return counter.toString()
}
