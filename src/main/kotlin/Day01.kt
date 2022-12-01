fun main() {
    val data = InputData.readLines("day01.txt")
    val elfs = mutableListOf<Int>()
    var calories = 0
    data.forEach { meal ->
        if (meal.isEmpty()) {
            elfs.add(calories)
            calories = 0
        } else {
            calories += meal.toInt()
        }
    }
    solve("Part 1", 71300) {
        elfs.max()
    }
    solve("Part 2", 209691) {
        elfs.sortByDescending { it }
        elfs.take(3).sum()
    }
}
