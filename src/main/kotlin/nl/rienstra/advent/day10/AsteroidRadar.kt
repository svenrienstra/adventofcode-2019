package nl.rienstra.advent.day10

import java.lang.Math.toDegrees
import java.util.stream.Collectors.toList
import java.util.stream.IntStream
import kotlin.math.absoluteValue
import kotlin.math.atan2
import kotlin.math.floor
import kotlin.math.round

object AsteroidRadar {
    fun calculateBlockedCoordinates(viewPoint: Point, asteroid: Point, maxNumber: Int = 15): List<Point> {
        if (viewPoint == asteroid) {
            return emptyList()
        }

        val horizontal = asteroid.x - viewPoint.x
        val vertical = asteroid.y - viewPoint.y

        val divider = gcd(horizontal.absoluteValue, vertical.absoluteValue);

        val smallestHorizontal = horizontal / divider;
        val smallestVertical = vertical / divider;

        return IntStream
                .rangeClosed(1, maxNumber)
                .mapToObj { Point(asteroid.x + (smallestHorizontal * it), asteroid.y + (smallestVertical * it)) }
                .collect(toList())
    }


    fun calculateBestRadarLocationWithoutTranslation(input: List<String>): Pair<Point, Int> {
        val map = toAsteroidMap(input)
        val allAsteroids = findAllAsteroids(map)

        return allAsteroids.map { viewPoint ->
            val blockedCoordinates = allAsteroids
                    .map { asteroid -> Pair(asteroid, calculateBlockedCoordinates(viewPoint, asteroid, 25)) }

            val allBlockedCoordinates = blockedCoordinates.map { it.second }.flatten()
            Pair(viewPoint, (allAsteroids.size - 1) - allAsteroids.intersect(allBlockedCoordinates).size)
        }.maxBy { it.second }!!
    }

    fun calculateBestRadarLocation(input: List<String>): Pair<Point, Int> {
        val map = toAsteroidMap(input)
        val result = calculateBestRadarLocationWithoutTranslation(input)

        return Pair(result.first.translate(map), result.second)
    }

    fun vaporizeAsteroids(input: List<String>): List<Point> {
        val map = toAsteroidMap(input)
        val location = calculateBestRadarLocationWithoutTranslation(input).first
        val allAsteroids = findAllAsteroids(map).minus(location).toMutableList()

        var angle = 90.0
        val destroyedAsteroids = mutableListOf<Point>()

        do {
            val blockedCoordinates = allAsteroids
                    .map { asteroid -> Pair(asteroid, calculateBlockedCoordinates(location, asteroid, 100)) }
            val allBlockedCoordinates = blockedCoordinates.map { it.second }.flatten()
            val visibleAsteroids = allAsteroids.minus(allBlockedCoordinates)

            val sortedAsteroids = visibleAsteroids.sortedWith(PointDegreeComparator(angle, location))

            val foundAsteroid = sortedAsteroids.first()
            destroyedAsteroids.add(foundAsteroid)
            allAsteroids.remove(foundAsteroid)

            angle = location.getAngle(foundAsteroid) - 0.00001
            if (angle < 0) {
                angle += 360
            }

        } while (allAsteroids.isNotEmpty())

        return destroyedAsteroids.map { it.translate(map) }
    }

    private fun findAllAsteroids(map: Array<BooleanArray>) =
            map
                    .mapIndexed { y, booleans ->
                        booleans.mapIndexed { x, b -> if (b) Point(x, y) else null }
                                .filterNotNull()
                    }
                    .flatten()


    private fun toAsteroidMap(input: List<String>): Array<BooleanArray> =
            input
                    .reversed()
                    .map { it.map { it == '#' }.toBooleanArray() }
                    .toTypedArray()


    private fun gcd(a: Int, b: Int): Int {
        if (b == 0) return a
        return gcd(b, a % b)
    }
}

data class Point(val x: Int, val y: Int) {
    fun translate(map: Array<BooleanArray>): Point = Point(x, (map.size - 1) - y)
    fun getAngle(other: Point): Double {
        val deltaX = other.x - x
        val deltaY = other.y - y

        val theta = toDegrees(atan2(deltaY.toDouble(), deltaX.toDouble()))
        return if (theta < 0.0) {
            theta + 360.0
        } else theta
    }
}

class PointDegreeComparator(val currentAngle: Double, val viewLocation: Point) : Comparator<Point> {
    override fun compare(o1: Point, o2: Point): Int {
        val asteroidAngle1 = currentAngle - viewLocation.getAngle(o1)
        val asteroidAngle2 = currentAngle - viewLocation.getAngle(o2)

        return when {
            asteroidAngle1 >= 0 && asteroidAngle2 < 0 -> -1
            asteroidAngle1 < 0 && asteroidAngle2 >= 0 -> 1
            else -> asteroidAngle1.compareTo(asteroidAngle2)
        }
    }

}