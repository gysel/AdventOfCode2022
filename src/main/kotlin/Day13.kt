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

    solve("Example", 13) {

        val correctPairs = exampleInput.split("\n\n")
            .map { pair ->
                pair.lines().map { it.tokenize() }.toPair()
            }
            .withIndex()
            .filter { (_, pair) ->
                isInOrder(pair.first, pair.second)
            }
            .map { it.index + 1 }

        // should be 1, 2, 4, and 6
        // println("In correct order: $correctPairs")

        correctPairs.sum()
    }

    val input = InputData.read("day13.txt")

    solve("Part 1", 5684) {

        val correctPairs = input.split("\n\n")
            .map { pair ->
                pair.lines().map { it.tokenize() }.toPair()
            }
            .withIndex()
            .filter { (_, pair) ->
                isInOrder(pair.first, pair.second)
            }
            .map { it.index + 1 }

        correctPairs.sum()
    }

    val comparator = Comparator<List<String>> { o1, o2 ->
        if (isInOrder(o1, o2)) -1 else 1
    }
    val dividerPackets = listOf(
        "[[2]]".tokenize(),
        "[[6]]".tokenize()
    )

    solve("Example for part 2", 140) {

        val sorted = exampleInput.lines()
            .filter { it.isNotEmpty() }
            .map { it.tokenize() }
            .plus(dividerPackets)
            .sortedWith(comparator)

        dividerPackets.map {
            sorted.indexOf(it) + 1
        }.reduce(Int::times)
    }

    solve("Part 2", 22932) {

        val sorted = input.lines()
            .filter { it.isNotEmpty() }
            .map { it.tokenize() }
            .plus(dividerPackets)
            .sortedWith(comparator)

        dividerPackets.map {
            sorted.indexOf(it) + 1
        }.reduce(Int::times)
    }
}

fun isInOrder(left: List<String>, right: List<String>): Boolean {
    var tokensLeft = left.toMutableList()
    var tokensRight = right.toMutableList()
    var indexLeft = 0
    var indexRight = 0
    var isInOrder: Boolean? = null
    while (isInOrder == null) {
        var left = tokensLeft[indexLeft]
        var right = tokensRight[indexRight]
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
            TODO("left=$left right=$right")
        }
    }

    return isInOrder
}

fun String.isNumber() = all { it.isDigit() }

fun String.tokenize(): List<String> {

    var toProcess = this
    val result = mutableListOf<String>()
    while (toProcess.isNotEmpty()) {
        val chunk = toProcess.takeWhile { it !in setOf('[', ',', ']') }
        if (chunk.isNotEmpty()) {
            result.add(chunk)
            toProcess = toProcess.drop(chunk.length)
        } else {
            when (toProcess.take(1)) {
                "[" -> result.add("[")
                "]" -> result.add("]")
                "," -> {} // do nothing
                else -> throw IllegalStateException()
            }
            toProcess = toProcess.drop(1)
        }
    }
    return result
}

//private fun Pair<Packet, Packet>.inCorrectOrder(): Boolean {
//    var index = 0
//    while (index < first.data.size && index < second.data.size) {
//        val left = first.data[index]
//        val right = second.data[index]
//        // If the left list runs out of items first, the inputs are in the right order.
//        if (left == null) {
//            return true
//        }
//        // If the right list runs out of items first, the inputs are not in the right order.
//        else if (right == null) {
//            return false
//        } else if (left.number < right.number) {
//            return true
//        } else if (left.number > right.number) {
//            return false
//        }
//        index++
//    }
//    return true
//}
//
//private fun String.parsePair(): Pair<Packet, Packet> {
//    val (firstLine, secondLine) = this.lines()
//    return Packet.parse(firstLine) to Packet.parse(secondLine)
//}
//
//data class Packet(
//    val data: List<Value>
//) {
//    companion object {
//        fun parse(line: String): Packet {
//            var toProcess = line.drop(1)
//            var depth = 1
//            val data = mutableListOf<Value>()
//            while (toProcess.isNotEmpty()) {
//                val chunk = toProcess.takeWhile { it !in setOf('[', ',', ']') }
//                if (chunk.isNotEmpty()) {
//                    data.add(Value(chunk.toInt(), depth))
//                    toProcess = toProcess.drop(chunk.length)
//                } else {
//                    when (toProcess.take(1)) {
//                        "[" -> depth++
//                        "]" -> depth--
//                        "," -> {} // do nothing
//                        else -> throw IllegalStateException()
//                    }
//                    toProcess = toProcess.drop(1)
//                }
//            }
//            return Packet(data)
//        }
//    }
//}
//
//data class Value(
//    val number: Int,
//    val depth: Int
//)
