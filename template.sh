DAY=$1

cat << EOF > src/main/kotlin/$1.kt
fun main() {
    val input = InputData.readLines("day${DAY}.txt")
    solve("Part 1", null) {

    }
    solve("Part 2", null) {

    }
}
EOF

touch "src/main/resources/${DAY}.txt"