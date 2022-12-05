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
    println("Solution of $part is $solution, calculation took ${System.currentTimeMillis() - start}ms")
}
