import java.util.LinkedList

fun main() {
    val exampleInput = """
        2,2,2
        1,2,2
        3,2,2
        2,1,2
        2,3,2
        2,2,1
        2,2,3
        2,2,4
        2,2,6
        1,2,5
        3,2,5
        2,1,5
        2,3,5
    """.trimIndent().lines()
    val input = InputData.readLines("day18.txt")

    solve("Example part 1", 64) {
        exampleInput.parseInput()
            .calculateSurfaceArea()
    }

    solve("Part 1", 3346) {
        input.parseInput()
            .calculateSurfaceArea()
    }

    solve("Example part 2", 58) {
        exampleInput.parseInput()
            .calculateWaterSurfaceArea()
    }

    solve("Part 2", 1980) {
        input.parseInput()
            .calculateWaterSurfaceArea()
    }
}

data class Coordinates3D(
    val x: Int,
    val y: Int,
    val z: Int
) {
    fun neighbours() = listOf(
        copy(x = x + 1),
        copy(x = x - 1),
        copy(y = y + 1),
        copy(y = y - 1),
        copy(z = z + 1),
        copy(z = z - 1)
    )

    infix fun within(range: IntRange): Boolean {
        return x in range && y in range && z in range
    }

    fun all() = listOf(x, y, z)

}


private fun List<String>.parseInput(): Set<Coordinates3D> {
    return map { line ->
        val (x, y, z) = line.split(",").map(String::toInt)
        Coordinates3D(x, y, z)
    }.toSet()
}

private fun Set<Coordinates3D>.calculateSurfaceArea() = sumOf { it ->
    it.neighbours().count { !contains(it) }
}

private fun Set<Coordinates3D>.calculateWaterSurfaceArea(): Int {
    val lava = this
    val start = lava.maxBy { it.x }.let { it.copy(x = it.x + 1) }
    val water = mutableSetOf(start)
    val queue = LinkedList<Coordinates3D>()
    queue.add(start)
    val range = lava.minOf { it.all().min() } - 1..lava.maxOf { it.all().max() } + 1
    do {
        queue
            .removeFirst()
            .neighbours()
            .filter { !lava.contains(it) }
            .filter { it within range }
            .forEach { confirmedWater ->
                if (water.add(confirmedWater)) {
                    // item was not already known as water, add to queue to process later
                    queue.add(confirmedWater)
                    // println("Add $confirmedWater to queue")
                }
            }
    } while (!queue.isEmpty())
    return lava.sumOf { it.neighbours().count(water::contains) }
}
