fun main() {
//    val input = InputData.read("day11.txt")
//        .split("\n\n")
//        .map { it.lines() }
//        .map { monkeyData ->
//            val startingItems = monkeyData[1].drop(18).split(", ").map{ it.toInt()}
//
//        }

    // What is the level of monkey business after 20 rounds of stuff-slinging simian shenanigans?
    //solve("Part 1", 100345) {

//        repeat(20) {
//            monkeys.forEach { monkey ->
//                monkey.round()
//            }
//        }

    //monkeys.map { it.inspectionCounter }.sortedDescending().take(2).reduce { a, b -> a * b }
    //}
    solve("Example for part 2", 5204L * 5192) {
        val monkeys = createExampleState()

        repeat(1000) {
            monkeys.forEach(Monkey::round)
        }
        calculateMonkeyBusiness(monkeys)

    }
    solve("Part 2", null) {
        val monkeys = createInitialState()

        repeat(10_000) {
            monkeys.forEach(Monkey::round)
        }
        calculateMonkeyBusiness(monkeys)
    }
    // 1199379048 is too low
    // 26969182824 is too low
    // 26748929184 is too low
}

private fun calculateMonkeyBusiness(monkeys: List<Monkey>) =
    monkeys.map { it.inspectionCounter }.sortedDescending().take(2).reduce { a, b -> a * b }

private fun createInitialState(): List<Monkey> {
    val monkey0 = Monkey(
        items = listOf(80),
        operation = { it * 5 },
        test = { it % 2 == 0L }
    )
    val monkey1 = Monkey(
        items = listOf(75, 83, 74),
        operation = { it + 7 },
        test = { it % 7 == 0L }
    )
    val monkey2 = Monkey(
        items = listOf(86, 67, 61, 96, 52, 63, 73),
        operation = { it + 5 },
        test = { it % 3 == 0L }
    )
    val monkey3 = Monkey(
        items = listOf(85, 83, 55, 85, 57, 70, 85, 52),
        operation = { it + 8 },
        test = { it % 17 == 0L }
    )
    val monkey4 = Monkey(
        items = listOf(67, 75, 91, 72, 89),
        operation = { it + 4 },
        test = { it % 11 == 0L }
    )
    val monkey5 = Monkey(
        items = listOf(66, 64, 68, 92, 68, 77),
        operation = { it * 2 },
        test = { it % 19 == 0L }
    )
    val monkey6 = Monkey(
        items = listOf(97, 94, 79, 88),
        operation = { it * it },
        test = { it % 5 == 0L }
    )
    val monkey7 = Monkey(
        items = listOf(77, 85),
        operation = { it + 6 },
        test = { it % 13 == 0L }
    )

    monkey0.ifTrue = monkey4
    monkey0.ifFalse = monkey3
    monkey1.ifTrue = monkey5
    monkey1.ifFalse = monkey6
    monkey2.ifTrue = monkey7
    monkey2.ifFalse = monkey0
    monkey3.ifTrue = monkey1
    monkey3.ifFalse = monkey5
    monkey4.ifTrue = monkey3
    monkey4.ifFalse = monkey1
    monkey5.ifTrue = monkey6
    monkey5.ifFalse = monkey2
    monkey6.ifTrue = monkey2
    monkey6.ifFalse = monkey7
    monkey7.ifTrue = monkey4
    monkey7.ifFalse = monkey0

    return listOf(monkey0, monkey1, monkey2, monkey3, monkey4, monkey5, monkey6, monkey7)
}

private fun createExampleState(): List<Monkey> {
    /**
     * Monkey 0:
     *   Starting items:
     *   Operation: new = old * 19
     *   Test: divisible by 23
     *     If true: throw to monkey 2
     *     If false: throw to monkey 3
     *
     * Monkey 1:
     *   Starting items:
     *   Operation: new = old + 6
     *   Test: divisible by 19
     *     If true: throw to monkey 2
     *     If false: throw to monkey 0
     *
     * Monkey 2:
     *   Starting items:
     *   Operation: new = old * old
     *   Test: divisible by 13
     *     If true: throw to monkey 1
     *     If false: throw to monkey 3
     *
     * Monkey 3:
     *   Starting items: 74
     *   Operation: new = old + 3
     *   Test: divisible by 17
     *     If true: throw to monkey 0
     *     If false: throw to monkey 1
     */
    val monkey0 = Monkey(
        items = listOf(79, 98),
        operation = { it * 19 },
        test = { it % 23 == 0L }
    )
    val monkey1 = Monkey(
        items = listOf(54, 65, 75, 74),
        operation = { it + 6 },
        test = { it % 19 == 0L }
    )
    val monkey2 = Monkey(
        items = listOf(79, 60, 97),
        operation = { it * it },
        test = { it % 13 == 0L }
    )
    val monkey3 = Monkey(
        items = listOf(74),
        operation = { it + 3 },
        test = { it % 17 == 0L }
    )
    monkey0.ifTrue = monkey2
    monkey0.ifFalse = monkey3
    monkey1.ifTrue = monkey2
    monkey1.ifFalse = monkey0
    monkey2.ifTrue = monkey1
    monkey2.ifFalse = monkey3
    monkey3.ifTrue = monkey0
    monkey3.ifFalse = monkey1

    return listOf(monkey0, monkey1, monkey2, monkey3)
}

class Monkey(
    items: List<Long>,
    val operation: (Long) -> Long,
    val test: (Long) -> Boolean
) {
    fun round() {
        items.forEach { item ->
            val new = operation(item) // / 3
            if (test(new))
                ifTrue.items.add(new)
            else
                ifFalse.items.add(new)
            inspectionCounter++
        }
        items.clear()
    }

    private val items = items.toMutableList()
    lateinit var ifTrue: Monkey
    lateinit var ifFalse: Monkey
    var inspectionCounter: Long = 0

    override fun toString(): String {
        return "$inspectionCounter"
    }
}