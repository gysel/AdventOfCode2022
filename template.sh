DAY=$1

cat << EOF > src/main/kotlin/Day$1.kt
fun main() {
    val input = InputData.readLines("day${DAY}.txt")
    solve("Part 1", null) {

    }
    solve("Part 2", null) {

    }
}
EOF

touch "src/main/resources/day${DAY}.txt"
git add "src/main"
