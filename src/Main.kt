import java.io.File
import java.util.*
import kotlin.collections.ArrayList

fun main() {
    val input = File("src/input.txt")

    val cities: ArrayList<City> = arrayListOf()
    val paths: ArrayList<Path> = arrayListOf()

    input.forEachLine { line ->
        val split = line.split(" ")
        when {
            split.size == 2 -> cities.add(City(name = split[0], distanceToBucharest = split[1].toInt()))

            split.size == 3 -> {
                var city1: City? = null
                var city2: City? = null
                for (city in cities) {
                    if (city.name == split[0]) city1 = city
                    if (city.name == split[1]) city2 = city
                }
                if (city1 != null && city2 != null) {
                    paths.add(Path(city1 = city1, city2 = city2, distance = split[2].toInt()))
                    paths.add(Path(city1 = city2, city2 = city1, distance = split[2].toInt()))
                    city1.connections.add(city2)
                    city2.connections.add(city1)
                }
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

        curr.connections.forEach neighbours@{ neighbour ->
            when {
                neighbour.name == "Bucharest" -> {
                    val path: ArrayList<City> = arrayListOf()
                    path.add(index = 0, element = neighbour)

                    while (curr.prev != null) {
                        path.add(index = 0, element = curr)
                        curr = curr.prev
                    }

                    start?.let { path.add(index = 0, element = it) }

                    path.forEach { println(it.name) }
                    queue.clear()

                }

                visited.contains(neighbour) -> return@neighbours

                else -> {
                    paths.forEach { if (it.city1 == curr && it.city2 == neighbour) neighbour.g += it.distance }

                    neighbour.prev = curr
                    queue.add(neighbour)
                }
            }
        }
    }
}