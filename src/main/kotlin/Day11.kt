fun main() {
    solve(
        "Example for part 1 after 1 round", listOf(
            listOf(20, 23, 27, 26).map(Int::toLong),
            listOf(2080, 25, 167, 207, 401, 1046).map(Int::toLong),
            listOf(),
            listOf()
        )
    ) {
        val monkeys = createExampleState()
        repeat(1) {
            monkeys.forEach(Monkey::playRound)
        }
        monkeys.map { it.items.toList() }
    }
    /**
     * == After round 1 ==
     * Monkey 0 inspected items 2 times.
     * Monkey 1 inspected items 4 times.
     * Monkey 2 inspected items 3 times.
     * Monkey 3 inspected items 6 times.
     */
    solve("Example for part 2 after 1 round", listOf(2, 4, 3, 6)) {
        val monkeys = createExampleState(1)

        repeat(1) {
            monkeys.forEach(Monkey::playRound)
        }
        monkeys.map { it.inspectionCounter }
    }
    /**
     * == After round 20 ==
     * Monkey 0 inspected items 99 times.
     * Monkey 1 inspected items 97 times.
     * Monkey 2 inspected items 8 times.
     * Monkey 3 inspected items 103 times.
     */
    solve("Example for part 2 after 20 rounds", listOf(99, 97, 8, 103)) {
        val monkeys = createExampleState(1)

        repeat(20) {
            monkeys.forEach(Monkey::playRound)
        }
        monkeys.map { it.inspectionCounter }
    }
    /**
     * == After round 1000 ==
     * Monkey 0 inspected items 5204 times.
     * Monkey 1 inspected items 4792 times.
     * Monkey 2 inspected items 199 times.
     * Monkey 3 inspected items 5192 times.
     */
    solve("Example for part 2 after 1000 rounds", listOf(5204, 4792, 199, 5192)) {
        val monkeys = createExampleState(1)

        repeat(1_000) {
            monkeys.forEach(Monkey::playRound)
            //println("Calculated round $it")
        }
        monkeys.map { it.inspectionCounter }
        //calculateMonkeyBusiness(monkeys)

    }
    solve("Part 1", 100345) {
        val monkeys = createInitialState()

        repeat(20) {
            monkeys.forEach(Monkey::playRound)
        }
        calculateMonkeyBusiness(monkeys)
    }

    solve("Part 2", 28537348205) {
        val monkeys = createInitialState(1)

        repeat(10_000) {
            monkeys.forEach(Monkey::playRound)
        }
        calculateMonkeyBusiness(monkeys)
    }
}

class Monkey(
    items: List<Int>,
    val operation: (Long) -> Long,
    val test: Long,
    private val denominator: Long
) {
    fun playRound() {
        items.forEach { item ->
            val new = operation(item) / denominator
            if (new % test == 0L)
                ifTrue.items.add(new % leastCommonDenominator)
            else
                ifFalse.items.add(new % leastCommonDenominator)
            inspectionCounter++
        }
        items.clear()
    }

    val items = items.map(Int::toLong).toMutableList()
    lateinit var ifTrue: Monkey
    lateinit var ifFalse: Monkey
    var leastCommonDenominator: Long = -1
    var inspectionCounter: Int = 0

    override fun toString(): String {
        return "$inspectionCounter"
    }
}

private fun createInitialState(worryLevelDenominator: Long = 3): List<Monkey> {
    val monkey0 = Monkey(
        items = listOf(80),
        operation = { it * 5 },
        test = 2,
        worryLevelDenominator
    )
    val monkey1 = Monkey(
        items = listOf(75, 83, 74),
        operation = { it + 7 },
        test = 7,
        worryLevelDenominator
    )
    val monkey2 = Monkey(
        items = listOf(86, 67, 61, 96, 52, 63, 73),
        operation = { it + 5 },
        test = 3,
        worryLevelDenominator
    )
    val monkey3 = Monkey(
        items = listOf(85, 83, 55, 85, 57, 70, 85, 52),
        operation = { it + 8 },
        test = 17,
        worryLevelDenominator
    )
    val monkey4 = Monkey(
        items = listOf(67, 75, 91, 72, 89),
        operation = { it + 4 },
        test = 11,
        worryLevelDenominator
    )
    val monkey5 = Monkey(
        items = listOf(66, 64, 68, 92, 68, 77),
        operation = { it * 2 },
        test = 19,
        worryLevelDenominator
    )
    val monkey6 = Monkey(
        items = listOf(97, 94, 79, 88),
        operation = { it * it },
        test = 5,
        worryLevelDenominator
    )
    val monkey7 = Monkey(
        items = listOf(77, 85),
        operation = { it + 6 },
        test = 13,
        worryLevelDenominator
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
        .initializeLeastCommonDenominator()

}

private fun createExampleState(denominator: Long = 3): List<Monkey> {
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
        test = 23,
        denominator
    )
    val monkey1 = Monkey(
        items = listOf(54, 65, 75, 74),
        operation = { it + 6 },
        test = 19,
        denominator
    )
    val monkey2 = Monkey(
        items = listOf(79, 60, 97),
        operation = { it * it },
        test = 13,
        denominator
    )
    val monkey3 = Monkey(
        items = listOf(74),
        operation = { it + 3 },
        test = 17,
        denominator
    )
    monkey0.ifTrue = monkey2
    monkey0.ifFalse = monkey3
    monkey1.ifTrue = monkey2
    monkey1.ifFalse = monkey0
    monkey2.ifTrue = monkey1
    monkey2.ifFalse = monkey3
    monkey3.ifTrue = monkey0
    monkey3.ifFalse = monkey1

    return listOf(monkey0, monkey1, monkey2, monkey3).initializeLeastCommonDenominator()
}

private fun List<Monkey>.initializeLeastCommonDenominator(): List<Monkey> {
    val leastCommonDenominator = map(Monkey::test).reduce { a, b -> a * b }
    forEach { it.leastCommonDenominator = leastCommonDenominator }
    return this
}

private fun calculateMonkeyBusiness(monkeys: List<Monkey>) =
    monkeys.map { it.inspectionCounter.toLong() }.sortedDescending().take(2).reduce { a, b -> a * b }
