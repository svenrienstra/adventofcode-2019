package nl.rienstra.advent.day11

import kotlinx.coroutines.channels.Channel

class PaintingRobot(val input: Channel<Long>, val output: Channel<Long>) {

    private var direction = Direction.UP
    private var currentLocation = Point(20, 20)
    private val colours = mutableMapOf<Point, Long>()
    private var stop: Boolean = false

    suspend fun paint() {
        while (!stop) {
            println("Robot waiting for input")
            val paintIndicator = input.receive()
            val directionIndicator = input.receive()

            println("Robot received $paintIndicator, $directionIndicator")

            colours[currentLocation] = paintIndicator

            currentLocation = when (direction) {
                Direction.UP -> if (directionIndicator == 1L) Point(currentLocation.x + 1, currentLocation.y) else Point(currentLocation.x - 1, currentLocation.y)
                Direction.LEFT -> if (directionIndicator == 1L) Point(currentLocation.x, currentLocation.y + 1) else Point(currentLocation.x, currentLocation.y - 1)
                Direction.DOWN -> if (directionIndicator == 1L) Point(currentLocation.x - 1, currentLocation.y) else Point(currentLocation.x + 1, currentLocation.y)
                Direction.RIGHT -> if (directionIndicator == 1L) Point(currentLocation.x, currentLocation.y - 1) else Point(currentLocation.x, currentLocation.y + 1)
            }

            direction = when (direction) {
                Direction.UP -> if (directionIndicator == 1L) Direction.RIGHT else Direction.LEFT
                Direction.LEFT -> if (directionIndicator == 1L) Direction.UP else Direction.DOWN
                Direction.DOWN -> if (directionIndicator == 1L) Direction.LEFT else Direction.RIGHT
                Direction.RIGHT -> if (directionIndicator == 1L) Direction.DOWN else Direction.UP
            }

            println("Moving to $currentLocation, now in direction $direction")

            output.send(colours.getOrDefault(currentLocation, 0))
        }
    }

    fun count(): Int = colours.size
    fun stop() { stop = true }
    fun print() {
        val maxY = colours.maxBy { it.key.y }!!.key.y
        val minY = colours.minBy { it.key.y }!!.key.y
        val maxX = colours.maxBy { it.key.x }!!.key.x
        val minX = colours.minBy { it.key.x }!!.key.x

        for (y in minY..maxY) {
            for (x in minX..maxX) {
                if (colours.getOrDefault(Point(x,y), 0) == 0L) print(" ") else print("#")
            }
            println()
        }
    }

    enum class Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    data class Point(val x: Int, val y: Int)
}