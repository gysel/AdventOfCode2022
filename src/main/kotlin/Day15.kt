import kotlin.math.absoluteValue

fun main() {
    val exampleData = """
            Sensor at x=2, y=18: closest beacon is at x=-2, y=15
            Sensor at x=9, y=16: closest beacon is at x=10, y=16
            Sensor at x=13, y=2: closest beacon is at x=15, y=3
            Sensor at x=12, y=14: closest beacon is at x=10, y=16
            Sensor at x=10, y=20: closest beacon is at x=10, y=16
            Sensor at x=14, y=17: closest beacon is at x=10, y=16
            Sensor at x=8, y=7: closest beacon is at x=2, y=10
            Sensor at x=2, y=0: closest beacon is at x=2, y=10
            Sensor at x=0, y=11: closest beacon is at x=2, y=10
            Sensor at x=20, y=14: closest beacon is at x=25, y=17
            Sensor at x=17, y=20: closest beacon is at x=21, y=22
            Sensor at x=16, y=7: closest beacon is at x=15, y=3
            Sensor at x=14, y=3: closest beacon is at x=15, y=3
            Sensor at x=20, y=1: closest beacon is at x=15, y=3
        """.trimIndent().lines()
    val input = InputData.readLines("day15.txt")

    solve("Example 1", 26) {
        val data = parseInput(exampleData)
        data.countOccupied(10)
    }

    solve("Part 1", 4560025) {
        val data = parseInput(input)
        data.countOccupied(2_000_000)
    }

    solve("Example 2", 56000011) {
        val data = parseInput(exampleData)
        val min = 0
        val max = 20

        findUnrangedCoordinates(data, min, max)
    }

    // be careful here, this will run for hours...
    solve("Part 2", null) {
        val data = parseInput(input).sortedByDescending { it.radius }
        val min = 0
        val max = 4_000_000

        findUnrangedCoordinates(data, min, max)
    }
}

private fun findUnrangedCoordinates(data: List<Record>, min: Int, max: Int): Long {
    val beacons = data.map { it.beacon }.toSet()
    val start = System.currentTimeMillis()
    val r = (min..max)
        .asSequence()
        .map { y ->
            if (y % 100 == 0) {
                val percent = (y.toFloat() / max)
                val time = System.currentTimeMillis() - start
                println(String.format("y=%s %.4f%% in %dms", y, percent * 100, time))

            }
            var x = min
            var freeX: Int? = null
            while (x <= max && freeX == null) {
                val point = Coordinates(x, y)
                if (point in beacons) {
                    x++
                } else {
                    val sensor = data.find { it.isInRange(point) }
                    if (sensor == null) {
                        freeX = x
                    } else {
                        // move until out of range of sensor
                        do {
                            x++
                        } while (sensor.isInRange(Coordinates(x, y)))
                    }
                }
            }
            if (freeX != null) {
                Coordinates(freeX, y)
            } else null
        }
        .filter { it != null }
        .first()
    return r!!.tuningFrequency()
}

private class Record(
    val sensor: Coordinates,
    val beacon: Coordinates
) {
    val radius = sensor manhattanDistanceTo beacon

    fun isInRange(point: Coordinates) = (point manhattanDistanceTo sensor) <= radius
}

private fun parseInput(input: List<String>): List<Record> {
    val data = input.map { line ->
        line
            .drop("Sensor at x=".length)
            .split(", y=", ": closest beacon is at x=")
            .map(String::toInt)
            .let { (sensorX, sensorY, beaconX, beaconY) ->
                Record(Coordinates(sensorX, sensorY), Coordinates(beaconX, beaconY))
            }
    }
    return data
}

infix fun Coordinates.manhattanDistanceTo(other: Coordinates): Int {
    return (x - other.x).absoluteValue + (y - other.y).absoluteValue
}

private fun List<Record>.countOccupied(y: Int): Int {
    val maxRadius = maxOf { it.radius }
    val minX = minOf { it.sensor.x } - maxRadius
    val maxX = maxOf { it.sensor.x } + maxRadius
    val beacons = map { it.beacon }.toSet()
    return (minX..maxX).count { x ->
        val point = Coordinates(x, y)
        point !in beacons && any { it.isInRange(point) }
    }
}

fun Coordinates.tuningFrequency() = 4_000_000L * x + y