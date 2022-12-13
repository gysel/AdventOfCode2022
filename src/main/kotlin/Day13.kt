fun main() {
    val exampleInput = """
            [1,1,3,1,1]
            [1,1,5,1,1]
            
            [[1],[2,3,4]]
            [[1],4]
            
            [9]
            [[8,7,6]]
            
            [[4,4],4,4]
            [[4,4],4,4,4]
            
            [7,7,7,7]
            [7,7,7]
            
            []
            [3]
            
            [[[]]]
            [[]]
            
            [1,[2,[3,[4,[5,6,7]]]],8,9]
            [1,[2,[3,[4,[5,6,0]]]],8,9]
            """.trimIndent()
    val input = InputData.read("day13.txt")

    solve("Example", 13) {
        findInOrderPairs(exampleInput)
    }

    solve("Part 1", 5684) {
        findInOrderPairs(input)
    }

    solve("Example for part 2", 140) {
        findPositionOfDividerPackets(exampleInput)
    }

    solve("Part 2", 22932) {
        findPositionOfDividerPackets(input)
    }
}

private fun findInOrderPairs(exampleInput: String) = parseInput(exampleInput)
    .asSequence()
    .chunked(2)
    .map { it.toPair() }
    .withIndex()
    .filter { (_, pair) ->
        isInOrder(pair.first, pair.second)
    }
    .sumOf { it.index + 1 }

private fun findPositionOfDividerPackets(input: String): Int {
    val dividerPackets = listOf(
        "[[2]]".tokenize(),
        "[[6]]".tokenize()
    )
    val comparator = Comparator<List<String>> { o1, o2 ->
        if (isInOrder(o1, o2)) -1 else 1
    }
    val sorted = parseInput(input)
        .plus(dividerPackets)
        .sortedWith(comparator)
    return dividerPackets.map {
        sorted.indexOf(it) + 1
    }.reduce(Int::times)
}

private fun parseInput(exampleInput: String) = exampleInput.lines()
    .filter { it.isNotEmpty() }
    .map { it.tokenize() }

fun isInOrder(packetLeft: List<String>, packetRight: List<String>): Boolean {
    val tokensLeft = packetLeft.toMutableList()
    val tokensRight = packetRight.toMutableList()
    var indexLeft = 0
    var indexRight = 0
    var isInOrder: Boolean? = null
    while (isInOrder == null) {
        val left = tokensLeft[indexLeft]
        val right = tokensRight[indexRight]
        if (left == "[" && right == "[" || left == "]" && right == "]") {
            indexLeft++
            indexRight++
        } else if (left == "[" && right.isNumber()) {
            tokensRight[indexRight] = "["
            tokensRight.addAll(indexRight + 1, listOf(right, "]"))
        } else if (left.isNumber() && right == "[") {
            tokensLeft[indexLeft] = "["
            tokensLeft.addAll(indexLeft + 1, listOf(left, "]"))
        } else if (left.isNumber() && right.isNumber()) {
            if (left.toInt() < right.toInt())
                isInOrder = true
            else if (left.toInt() > right.toInt())
                isInOrder = false
            indexLeft++
            indexRight++
        } else if (left.isNumber() && right == "]") {
            isInOrder = false
        } else if (left == "]" && right.isNumber()) {
            isInOrder = true
        } else if (left == "[" && right == "]") {
            isInOrder = false
        } else if (left == "]" && right == "[") {
            isInOrder = true
        } else {
            throw IllegalStateException("left=$left right=$right")
        }
    }
    return isInOrder
}

fun String.tokenize(): List<String> {
    var toProcess = this
    val result = mutableListOf<String>()
    while (toProcess.isNotEmpty()) {
        val chunk = toProcess.takeWhile { it !in setOf('[', ',', ']') }
        toProcess = if (chunk.isNotEmpty()) {
            result.add(chunk)
            toProcess.drop(chunk.length)
        } else {
            when (toProcess.take(1)) {
                "[" -> result.add("[")
                "]" -> result.add("]")
                "," -> {} // do nothing
                else -> throw IllegalStateException()
            }
            toProcess.drop(1)
        }
    }
    return result
}
