import java.util.*

fun main() {
    val input = InputData.readLines("day07.txt")
    val pwd = Stack<String>()
    val folders = mutableMapOf<String, Folder>()
    folders["/"] = Folder()
    input.forEach { line ->
        when {
            line.startsWith("$ cd ") -> {
                when (line.drop("$ cd ".length)) {
                    "/" -> pwd.clear()
                    ".." -> pwd.pop()
                    else -> {
                        val newFolder = Folder()
                        folders[pwd.assemble()]!!.add(newFolder)
                        pwd.add(line.drop("$ cd ".length))
                        folders[pwd.assemble()] = newFolder
                    }
                }
            }

            line == "$ ls" -> {
                // do we care about this?
                // spoiler: no
            }

            line.startsWith("dir ") -> {
                // do we care about this?
                // spoiler: no
            }

            else -> { // assume file
                val (size, _) = line.split(" ")
                folders[pwd.assemble()]!!.add(size.toLong())
            }
        }
    }
    val folderSizes = folders
        .map { (_, folder) ->
            folder.calculateDeepSize()
        }
    solve("Part 1", 1642503) {
        folderSizes.filter { it <= 100000 }.sum()
    }
    solve("Part 2", 6999588) {
        val diskSize = 70000000
        val diskSpaceUsed = folders["/"]!!.calculateDeepSize()
        val diskSpaceNeeded = 30000000 - (diskSize - diskSpaceUsed)
        folderSizes
            .filter { it >= diskSpaceNeeded }
            .min()
    }
}

class Folder {
    private val subFolders: MutableList<Folder> = mutableListOf()

    /**
     * Only this folder, no subfolders
     */
    private var size: Long = 0

    fun calculateDeepSize(): Long = size + subFolders.sumOf { it.calculateDeepSize() }

    fun add(fileSize: Long) {
        size += fileSize
    }

    fun add(folder: Folder) {
        subFolders.add(folder)
    }
}

private fun Stack<String>.assemble() = "/" + joinToString("/")