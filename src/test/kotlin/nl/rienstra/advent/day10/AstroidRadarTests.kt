package nl.rienstra.advent.day10

import io.kotlintest.matchers.doubles.shouldBeGreaterThan
import io.kotlintest.matchers.doubles.shouldBeLessThan
import io.kotlintest.should
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

class AstroidRadarTests : WordSpec() {

    init {
        "AstroidRadar" should
                {
                    "calculate blocked sight" {
                        AstroidRadar.calculateBlockedCoordinates(Point(0, 0), Point(3, 1), 2) shouldBe listOf(Point(6, 2), Point(9, 3))
                        AstroidRadar.calculateBlockedCoordinates(Point(0, 0), Point(3, 2), 2) shouldBe listOf(Point(6, 4), Point(9, 6))
                        AstroidRadar.calculateBlockedCoordinates(Point(0, 0), Point(3, 3), 3) shouldBe listOf(Point(4, 4), Point(5, 5), Point(6, 6))
                        AstroidRadar.calculateBlockedCoordinates(Point(0, 0), Point(2, 3), 2) shouldBe listOf(Point(4, 6), Point(6, 9))
                        AstroidRadar.calculateBlockedCoordinates(Point(0, 0), Point(2, 4), 2) shouldBe listOf(Point(3, 6), Point(4, 8))
                    }

                    "calculateBestRadarLocation" {
                        checkExample("/day10/example1", 8, Point(3, 4))
                        checkExample("/day10/example2", 33, Point(5, 8))
                        checkExample("/day10/example3", 35, Point(1, 2))
                        checkExample("/day10/example4", 41, Point(6, 3))
                        checkExample("/day10/example5", 210, Point(11, 13))
                    }

                    "calculate example" {
                        val input = this::class.java.getResourceAsStream("/day10/input")
                                .bufferedReader()
                                .readLines()

                        val best = AstroidRadar.calculateBestRadarLocation(input)
                        println(best.second)
                    }

                    "calculate angle" {
                        Point(0, 0).getAngle(Point(0, 5)) shouldBe 90.0
                        Point(0, 0).getAngle(Point(5, 0)) shouldBe 0.0
                        Point(0, 0).getAngle(Point(0, -5)) shouldBe 270.0
                        Point(0, 0).getAngle(Point(-5, 0)) shouldBe 180.0

                        Point(5, 5).getAngle(Point(5, 10)) shouldBe 90.0
                        Point(5, 5).getAngle(Point(10, 5)) shouldBe 0.0
                        Point(5, 5).getAngle(Point(5, 0)) shouldBe 270.0
                        Point(5, 5).getAngle(Point(0, 5)) shouldBe 180.0

                        Point(8, 1).getAngle(Point(2, 0)) shouldBeGreaterThan 180.0
                        Point(8, 1).getAngle(Point(2, 0)) shouldBeLessThan 270.0
                    }

                    "sort locations" {
                        val sorted = listOf(Point(1, 2), Point(2, 2), Point(3, 3)).sortedWith(PointDegreeComparator(90.0, Point(1, 1)))
                        sorted[0] shouldBe Point(1, 2)
                        sorted[1] shouldBe Point(2, 2)

                        val sorted2 = listOf(Point(11, 14), Point(4, 13), Point(1, 6), Point(5, 0)).sortedWith(PointDegreeComparator(89.9999, Point(11, 6)))
                        sorted2[0] shouldBe Point(5, 0)
                        sorted2[1] shouldBe Point(1, 6)
                        sorted2[2] shouldBe Point(4, 13)
                        sorted2[3] shouldBe Point(11, 14)
                    }

                    "vaporize asteroids examples" {
                        val input = this::class.java.getResourceAsStream("/day10/vaporizeExample")
                                .bufferedReader()
                                .readLines()

                        val output = AstroidRadar.vaporizeAsteroids(input)
                        output[0] shouldBe Point(8, 1)
                        output[1] shouldBe Point(9, 0)
                        output[2] shouldBe Point(9, 1)
                        output[3] shouldBe Point(10, 0)
                        output[4] shouldBe Point(9, 2)
                        output[5] shouldBe Point(11, 1)
                        output[6] shouldBe Point(12, 1)
                        output[7] shouldBe Point(11, 2)
                        output[8] shouldBe Point(15, 1)
                        output[9] shouldBe Point(12, 2)
                        output[10] shouldBe Point(13, 2)
                        output[11] shouldBe Point(14, 2)
                        output[12] shouldBe Point(15, 2)
                        output[13] shouldBe Point(12, 3)
                        output[14] shouldBe Point(16, 4)
                        output[15] shouldBe Point(15, 4)
                        output[16] shouldBe Point(10, 4)
                        output[17] shouldBe Point(4, 4)
                        output[18] shouldBe Point(2, 4)
                        output[19] shouldBe Point(2, 3)
                        output[20] shouldBe Point(0, 2)
                        output[21] shouldBe Point(1, 2)
                        output[22] shouldBe Point(0, 1)
                        output[23] shouldBe Point(1, 1)
                        output[24] shouldBe Point(5, 2)
                        output[25] shouldBe Point(1, 0)
                        output[26] shouldBe Point(5, 1)
                        output[27] shouldBe Point(6, 1)
                        output[28] shouldBe Point(6, 0)
                        output[29] shouldBe Point(7, 0)
                        output[30] shouldBe Point(8, 0)
                        output[31] shouldBe Point(10, 1)
                        output[32] shouldBe Point(14, 0)
                        output[33] shouldBe Point(16, 1)
                        output[34] shouldBe Point(13, 3)
                        output[35] shouldBe Point(14, 3)

                        val input2 = this::class.java.getResourceAsStream("/day10/example5")
                                .bufferedReader()
                                .readLines()
                        val output2 = AstroidRadar.vaporizeAsteroids(input2)

                        output2[0] shouldBe Point(11,12)
                        output2[1] shouldBe Point(12,1)
                        output2[2] shouldBe Point(12,2)
                        output2[9] shouldBe Point(12,8)
                        output2[19] shouldBe Point(16,0)
                        output2[49] shouldBe Point(16,9)
                        output2[99] shouldBe Point(10,16)
                        output2[198] shouldBe Point(9,6)
                        output2[199] shouldBe Point(8,2)
                        output2[200] shouldBe Point(10,9)
                        output2[298] shouldBe Point(11, 1)
                    }

                    "vaporize" {
                        val input = this::class.java.getResourceAsStream("/day10/input")
                                .bufferedReader()
                                .readLines()

                        val output = AstroidRadar.vaporizeAsteroids(input)
                        println(output[199])
                    }
                }
    }

    fun checkExample(resource: String, number: Int, location: Point) {
        val example = this::class.java.getResourceAsStream(resource)
                .bufferedReader()
                .readLines()

        val best = AstroidRadar.calculateBestRadarLocation(example)
        best.second shouldBe number
        best.first shouldBe location
    }
}
