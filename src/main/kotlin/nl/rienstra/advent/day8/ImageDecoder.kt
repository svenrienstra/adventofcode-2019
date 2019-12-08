package nl.rienstra.advent.day8

import java.util.stream.Collectors.toList
import java.util.stream.IntStream

object ImageDecoder {

    fun print(width: Int, height: Int, input: IntArray) {
        val layers = decode(width, height, input)

        for (rowNum in layers[0].rows.indices) {
            for (index in layers[0].rows[rowNum].indices) {
                val value = layers.first { it.getValue(rowNum, index) != 2 }.getValue(rowNum, index)

                if (value == 0) print(" ") else print("*")
            }
            println()
        }
    }

    fun decode(width: Int, height: Int, input: IntArray): List<Layer> {
        val numberOfLayers = (input.size / width) / height
        return IntStream
                .range(0, numberOfLayers)
                .mapToObj { layer ->
                    Layer(IntStream
                            .range(0, height)
                            .mapToObj {
                                val start = (layerLength(width, height) * layer) + (width * it)
                                input.copyOfRange(start, start + width)
                            }
                            .collect(toList()))
                }
                .collect(toList())
    }

    private fun layerLength(width: Int, height: Int) = width * height
}

data class Layer(val rows: List<IntArray>) {
    fun countNumber(number: Int) = rows
            .stream()
            .mapToInt { layer -> layer.count { it == number } }
            .sum()

    fun getValue(rowNum: Int, index: Int) = rows[rowNum][index]
}