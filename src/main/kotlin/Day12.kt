import java.util.LinkedList

fun main() {
    solve("Example", 31) {
        val input = """
            Sabqponm
            abcryxxl
            accszExk
            acctuvwj
            abdefghi
            """.trimIndent()
        val (start, target, map) = input.parseMap()
        breadFirstSearch(start, target, map)
    }

    solve("Part 1", 350) {
        val (start, target, map) = InputData.read("day12.txt").parseMap()
        breadFirstSearch(start, target, map)
    }

    solve("Part 2", 349) {
        val input = InputData.read("day12.txt")
        val (_, target, map) = input.parseMap()
        val possibleStartingPositions = map.values
            .filter { it.height == 1 }

        possibleStartingPositions.minOf { start ->
            map.values.forEach(Square::reset)
            breadFirstSearch(start, target, map)
        }
    }
}

private fun String.parseMap(): Triple<Square, Square, Map<Coordinates, Square>> {
    var start: Square? = null
    var target: Square? = null
    val squares = lines().flatMapIndexed { y, line ->
        line.mapIndexed { x, char ->
            val square = Square(Coordinates(x, y), char.toHeight())
            if (char == 'S')
                start = square
            else if (char == 'E')
                target = square
            square
        }
    }
    return Triple(start!!, target!!, squares.associateBy { it.coordinates })
}

private fun Char.toHeight(): Int {
    return when (this) {
        'S' -> 1
        'E' -> 26
        else -> code - 96
    }
}

class Square(val coordinates: Coordinates, val height: Int) {
    var steps: Int? = null
    override fun toString(): String {
        return "$coordinates height=$height steps=$steps"
    }

    fun edges() = with(coordinates) {
        listOf(
            copy(x = x + 1, y = y),
            copy(x = x, y = y + 1),
            copy(x = x - 1, y = y),
            copy(x = x, y = y - 1)
        )
    }

    fun reset() {
        steps = null
    }
}


private fun breadFirstSearch(start: Square, target: Square, map: Map<Coordinates, Square>): Int {
    val queue = LinkedList<Square>()
    queue.add(start)
    start.steps = 0
    while (queue.isNotEmpty() && target.steps == null) {
        val position = queue.removeFirst()!!
        position.edges()
            .mapNotNull { edge -> map[edge] }
            .filter { it.height <= position.height + 1 }
            .filter { it.steps == null }
            .forEach { move ->
                move.steps = position.steps!! + 1
                queue.add(move)
            }
    }
    return target.steps ?: Integer.MAX_VALUE
}
