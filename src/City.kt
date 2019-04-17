data class City(val name: String, val distanceToBucharest: Int): Comparable<City> {
    val connections: ArrayList<City> = arrayListOf()

    var g: Int = 0
    var h: Int = distanceToBucharest

    var prev: City? = null

    fun equals(city: City): Boolean {
        if (city.name == name) return true
        return false
    }

    override fun compareTo(other: City): Int {
        return g + h - other.g - other.h
    }
}