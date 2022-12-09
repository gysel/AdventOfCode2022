import kotlin.math.absoluteValue

fun main() {
    val motions: List<Pair<String, Int>> = InputData
        .readLines("day09.txt")
        .map { line ->
            val (direction, steps) = line.split(" ")
            direction to steps.toInt()
        }
    solve("Part 1", 5735) {
        moveRope(2, motions)
    }

    solve("Part 2", 2478) {
        moveRope(10, motions)
    }
}

private fun moveRope(
    ropeSize: Int,
    motions: List<Pair<String, Int>>
): Int {
    val rope: MutableList<Coordinates> = (1..ropeSize)
        .map { Coordinates(1, 1) }
        .toMutableList()
    val trail = mutableSetOf(Coordinates(1, 1))

    motions.forEach { (direction, steps) ->
        repeat(steps) {
            val head = rope.first()
            val newHead = when (direction) {
                "U" -> head.up()
                "R" -> head.right()
                "D" -> head.down()
                "L" -> head.left()
                else -> throw IllegalStateException()
            }
            rope[0] = newHead
            (1 until ropeSize).forEach { index ->
                val front = rope[index - 1]
                val back = rope[index]

                val x = (front.x - back.x)
                val y = (front.y - back.y)
                if (x.absoluteValue > 1 || y.absoluteValue > 1) {
                    val newBack = when {
                        x == 2 && y == 0 -> back.right()
                        x == -2 && y == 0 -> back.left()
                        x == 0 && y == 2 -> back.up()
                        x == 0 && y == -2 -> back.down()
                        x > 0 && y > 0 -> back.up().right()
                        x > 0 && y < 0 -> back.down().right()
                        x < 0 && y < 0 -> back.down().left()
                        x < 0 && y > 0 -> back.up().left()
                        else -> throw IllegalStateException()
                    }
                    rope[index] = newBack
                }
            }
            trail.add(rope.last())
        }
    }
    return trail.size
}

private fun Coordinates.left() = copy(x = x - 1)
private fun Coordinates.down() = copy(y = y - 1)
private fun Coordinates.right() = copy(x = x + 1)
private fun Coordinates.up() = copy(y = y + 1)
