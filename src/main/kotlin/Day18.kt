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

    solve("Example part 2", null) {

    }

    solve("Part 2", null) {

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