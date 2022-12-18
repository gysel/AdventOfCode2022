#!/usr/bin/env bash

DAY=$1
source .env

cat << EOF > src/main/kotlin/Day$1.kt
fun main() {
    val exampleInput = """
    """.trimIndent().lines()
    val input = InputData.readLines("day${DAY}.txt")
    solve("Example part 1", null) {

    }

    solve("Part 1", null) {

    }

    solve("Example part 2", null) {

    }

    solve("Part 2", null) {

    }
}
EOF

curl -s -H "cookie: session=${AOC_SESSION}" "https://adventofcode.com/2022/day/${DAY}/input" \
  > "src/main/resources/day${DAY}.txt"

git add "src/main"
