package nl.rienstra.advent.day3

import java.util.stream.Collectors
import java.util.stream.IntStream
import kotlin.math.absoluteValue

object WireLocator {

    fun calculateLowestDistance(input1: List<String>, input2: List<String>) =
            calculateIntersections(input1, input2)
                    .stream()
                    .mapToInt { point -> point.x.absoluteValue + point.y.absoluteValue }
                    .min().asInt

    fun calculateShortestPath(input1: List<String>, input2: List<String>): Int {
        val pointList1 = toPointsList(input1)
        val pointList2 = toPointsList(input2)

        return calculateIntersections(input1, input2).stream()
                .mapToInt { intersection -> pointList1.indexOf(intersection) + pointList2.indexOf(intersection) }
                .min().asInt
    }

    fun calculateIntersections(input1: List<String>, input2: List<String>) = toPointsList(input1).intersect(toPointsList(input2)).minusElement(Point(0, 0))

    private fun toPoints(command: String, initialX: Int, initialY: Int): List<Point> {
        val number = command.substring(1).toInt()
        return when (command.first()) {
            'R' -> IntStream.rangeClosed(1, number).mapToObj { iteration -> Point(initialX, initialY + iteration) }.collect(Collectors.toList())
            'L' -> IntStream.rangeClosed(1, number).mapToObj { iteration -> Point(initialX, initialY - iteration) }.collect(Collectors.toList())
            'U' -> IntStream.rangeClosed(1, number).mapToObj { iteration -> Point(initialX + iteration, initialY) }.collect(Collectors.toList())
            'D' -> IntStream.rangeClosed(1, number).mapToObj { iteration -> Point(initialX - iteration, initialY) }.collect(Collectors.toList())
            else -> emptyList()
        }
    }

    private tailrec fun toPointsList(input: List<String>, points: List<Point> = listOf(Point(0, 0))): List<Point> =
            when (input.isEmpty()) {
                true -> points
                false -> toPointsList(input.subList(1, input.size),
                        points.plus(toPoints(input.first(), points.last().x, points.last().y)))
            }

    data class Point(val x: Int, val y: Int)
}