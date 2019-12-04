package nl.rienstra.advent.day4

import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

class PasswordGeneratorTests : WordSpec({
    "PasswordGenerator" should {
        "accept valid passwords" {
            PasswordGenerator.isValid("112233") shouldBe true
            PasswordGenerator.isValid("123444") shouldBe false
            PasswordGenerator.isValid("111122") shouldBe true
            PasswordGenerator.isValid("201345") shouldBe false
        }

        "generate passwords" {
            println(PasswordGenerator.generatePasswords("171309", "643603"))
            println(PasswordGenerator.generatePasswords("171309", "643603").size)
        }
    }
})