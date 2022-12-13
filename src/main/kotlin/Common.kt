class InputData {
    companion object {
        fun readLines(filename: String): List<String> {
            return this::class.java.classLoader.getResourceAsStream(filename)
                ?.bufferedReader()
                ?.readLines()
                ?: throw IllegalStateException("Unable to read $filename!")
        }

        fun read(filename: String): String {
            return this::class.java.classLoader.getResourceAsStream(filename)
                ?.reader()
                ?.readText()
                ?: throw IllegalStateException("Unable to read $filename!")
        }
    }
}

fun <T> solve(part: String, correctSolution: T?, function: () -> T) {
    val start = System.currentTimeMillis()
    val solution = function()
    if (correctSolution != null && solution != correctSolution) {
        throw IllegalStateException("Wrong solution! Expected is $correctSolution, but result was $solution.")
    }
    println("Solution of $part is '$solution', calculation took ${System.currentTimeMillis() - start}ms")
}

data class Coordinates(val x: Int, val y: Int) {
    override fun toString(): String {
        return "x=$x y=$y"
    }
}

fun <T> List<T>.toPair(): Pair<T, T> {
    if (this.size != 2) {
        throw IllegalArgumentException("List is not of length 2!")
    }
    return Pair(this[0], this[1])
}

fun String.isNumber() = all { it.isDigit() }
