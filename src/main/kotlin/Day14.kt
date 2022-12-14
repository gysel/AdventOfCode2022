private const val ROCK = '#'
private const val AIR = '.'
private const val SAND = 'o'

fun main() {
    val exampleInput = """
        498,4 -> 498,6 -> 496,6
        503,4 -> 502,4 -> 502,9 -> 494,9
    """.trimIndent().lines()
    val input = InputData.readLines("day14.txt")

    solve("Example part 1", 24) {
        val rocks: List<List<Coordinates>> = parseInput(exampleInput)
        val map = createMap(rocks)
        val maxY = map.maxOf { it.key.y }
        map.print()
        map.simulateSand { it.y > maxY }
    }

    solve("Part 1", 692) {
        val rocks: List<List<Coordinates>> = parseInput(input)
        val map = createMap(rocks)
        val maxY = map.maxOf { it.key.y }
        map.print()
        map.simulateSand { it.y > maxY }
    }
    solve("Part 2", 31706) {
        val rocks: List<List<Coordinates>> = parseInput(input)
        val map = createMap(rocks)
        map.print()
        map.simulateSand { it.x == 500 && it.y == 0 }
    }
}

private fun parseInput(exampleInput: List<String>): List<List<Coordinates>> {
    return exampleInput.map { line ->
        line.split(" -> ").map { position ->
            val (x, y) = position.split(",").map { it.toInt() }
            Coordinates(x, y)
        }
    }
}

private fun createMap(rocks: List<List<Coordinates>>): MutableMap<Coordinates, Char> {
    val map = mutableMapOf<Coordinates, Char>()

    rocks.forEach { path ->
        path.windowed(2).forEach { (from, to) ->
            if (from.x == to.x) { // vertical movement
                createRange(from.y, to.y).forEach { y ->
                    map[Coordinates(from.x, y)] = ROCK
                }
            } else if (from.y == to.y) { // horizontal movement
                createRange(from.x, to.x).forEach { x ->
                    map[Coordinates(x, from.y)] = ROCK
                }
            } else throw IllegalStateException()
        }
    }

    return map
}

private fun MutableMap<Coordinates, Char>.simulateSand(simulationStop: (Coordinates) -> Boolean): Int {
    var sandPlaced = 0
    var sandOverflow = false
    val floorLevel = maxOf { it.key.y } + 2
    while (!sandOverflow) {
        var sandPosition = Coordinates(500, 0)
        var sandMoving = true
        while (sandMoving) {
            val nextPositions = listOf(sandPosition.down(), sandPosition.down().left(), sandPosition.down().right())
            val nextPosition = nextPositions.find { readAt(it) == AIR }
            if (nextPosition == null || nextPosition.y == floorLevel) {
                put(sandPosition, SAND)
                sandPlaced++
                //print()
                sandMoving = false
            } else {
                sandPosition = nextPosition
            }

            if (simulationStop(sandPosition)) {
                print()
                sandMoving = false
                sandOverflow = true
            }
        }
    }
    return sandPlaced
}

private fun <V> Map<Coordinates, V>.print() {
    val minX = minOf { it.key.x } - 1
    val minY = minOf { it.key.y } - 1
    val maxX = maxOf { it.key.x } + 1
    val maxY = maxOf { it.key.y } + 1
    (minY..maxY).forEach { y ->
        val line = (minX..maxX).map { x ->
            readAt(Coordinates(x, y))
        }.joinToString("")
        println(line)
    }
    println("\n\n")
}

private fun <V> Map<Coordinates, V>.readAt(coordinates: Coordinates) = get(coordinates) ?: AIR

private fun createRange(from: Int, to: Int) = if (from < to) from..to else to..from

// (x,y) is top left
private fun Coordinates.left() = copy(x = x - 1)
private fun Coordinates.right() = copy(x = x + 1)
private fun Coordinates.down() = copy(y = y + 1)