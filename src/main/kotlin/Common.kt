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

fun solve(part: String, correctSolution: Int?, function: () -> Int) {
    val solution = function()
    if (correctSolution != null && solution != correctSolution) {
        throw IllegalStateException("Wrong solution!")
    }
    println("Solution of $part is $solution")
}
