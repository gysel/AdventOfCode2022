fun main() {
    val input = InputData.readLines("day08.txt")
    val gridSize = 99
    val allCoordinates: List<Coordinates> = (1..gridSize)
        .flatMap { x ->
            (1..gridSize).map { y -> Coordinates(x, y) }
        }

    // 1:1 is top left, 99:99 is bottom right
    val grid: Map<Coordinates, Int> = input
        .flatMapIndexed { index, line ->
            val y = index + 1
            (1..gridSize).map { x ->
                Coordinates(x, y) to line[x - 1].toString().toInt()
            }
        }
        .toMap()

    solve("Part 1", 1713) {
        val visibleTrees = allCoordinates.count { coordinates ->
            isEdge(coordinates, gridSize) || isVisible(coordinates, grid, gridSize)
        }
        visibleTrees
    }
    solve("Part 2", 268464) {
        allCoordinates.maxOf { coordinates ->
            calculateScenicScore(coordinates, grid, gridSize)
        }
    }
}

fun isVisible(coordinates: Coordinates, grid: Map<Coordinates, Int>, gridSize: Int): Boolean {
    val tree = grid[coordinates]!!
    val visibleFromNorth = (coordinates.y - 1 downTo 1).all {
        grid[coordinates.copy(y = it)]!! < tree
    }
    val visibleFromEast = (coordinates.x + 1..gridSize).all {
        grid[coordinates.copy(x = it)]!! < tree
    }
    val visibleFromSouth = (coordinates.y + 1..gridSize).all {
        grid[coordinates.copy(y = it)]!! < tree
    }
    val visibleFromWest = (coordinates.x - 1 downTo 1).all {
        grid[coordinates.copy(x = it)]!! < tree
    }
    return visibleFromNorth || visibleFromEast || visibleFromSouth || visibleFromWest
}

private fun isEdge(coordinates: Coordinates, gridSize: Int) =
    coordinates.x == 1
            || coordinates.x == gridSize
            || coordinates.y == 1
            || coordinates.y == gridSize

fun calculateScenicScore(coordinates: Coordinates, grid: Map<Coordinates, Int>, gridSize: Int): Long {
    val tree = grid[coordinates]!!
    if (isEdge(coordinates, gridSize)) return 0

    val distanceNorth = (coordinates.y - 1 downTo 1)
        .map { y -> coordinates.copy(y = y) }
        .calculateViewingDistance(grid, tree)
    val distanceEast = (coordinates.x + 1..gridSize)
        .map { x -> coordinates.copy(x = x) }
        .calculateViewingDistance(grid, tree)
    val distanceSouth = (coordinates.y + 1..gridSize)
        .map { y -> coordinates.copy(y = y) }
        .calculateViewingDistance(grid, tree)
    val distanceWest = (coordinates.x - 1 downTo 1)
        .map { x -> coordinates.copy(x = x) }
        .calculateViewingDistance(grid, tree)

    // start with '1L' to force the multiplication to be a Long
    return 1L * distanceNorth * distanceEast * distanceSouth * distanceWest
}

private fun List<Coordinates>.calculateViewingDistance(grid: Map<Coordinates, Int>, treeHeight: Int): Int {
    var distance = 0
    forEach { coordinates ->
        distance++
        if (grid[coordinates]!! >= treeHeight)
            return distance
    }
    return distance
}
