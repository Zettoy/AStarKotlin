data class City(val name: String, val distanceToBucharest: Int): Comparable<City> {
    val connections: ArrayList<City> = arrayListOf()
    var g: Int = 0
    var prev: City? = null

    override fun compareTo(other: City): Int {
        return g + distanceToBucharest - (other.g + other.distanceToBucharest)
    }
}