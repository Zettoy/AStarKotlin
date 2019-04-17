import java.io.File
import java.util.*
import kotlin.collections.ArrayList

fun main() {
    val input = File("src/input.txt")

    val cities: ArrayList<City> = arrayListOf()
    val pathes: ArrayList<Path> = arrayListOf()

    input.forEachLine {
        val split = it.split(" ")
        if (split.size == 2) {
            cities.add(City(name = split[0], distanceToBucharest = split[1].toInt()))

        } else if (split.size == 3) {
            var city1: City? = null
            var city2: City? = null
            for (city in cities) {
                if (city.name == split[0]) city1 = city
                if (city.name == split[1]) city2 = city
            }
            if (city1 != null && city2 != null) {
                pathes.add(Path(city1, city2, split[2].toInt()))
                pathes.add(Path(city2, city1, split[2].toInt()))
                city1.connections.add(city2)
                city2.connections.add(city1)
            }
        }
    }

    var start: City? = null
    cities.forEach { if (it.name == "Arad") start = it }

    val queue: PriorityQueue<City> = PriorityQueue()
    val visited: ArrayList<City> = arrayListOf()

    queue.add(start)
    while (queue.isNotEmpty()) {
        var curr = queue.poll()
        visited.add(curr)

        for (neighbour in curr.connections) {
            if (neighbour.name == "Bucharest") {
                val path: ArrayList<City> = arrayListOf()
                path.add(0, neighbour)

                while (curr.prev != null) {
                    path.add(0, curr)
                    curr = curr.prev
                }

                start?.let { path.add(0, it) }

                path.forEach { println(it.name) }
                queue.clear()

            } else if (visited.contains(neighbour)) {
                continue

            } else {
                pathes.forEach { if (it.city1 == curr && it.city2 == neighbour) neighbour.g += it.distance }

                neighbour.prev = curr
                queue.add(neighbour)
            }
        }

    }
}