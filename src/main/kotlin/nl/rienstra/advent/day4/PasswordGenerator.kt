package nl.rienstra.advent.day4

object PasswordGenerator {
    fun generatePassword(start: String, end: String): List<String> {
        val passwords = mutableListOf<String>()
        var current = start
        while (current.toInt() < end.toInt()) {
            val next = (current.toInt() + 1).toString()
            if (isValid(next)) passwords.add(next)
            current = next
        }
        return passwords
    }

    fun isValid(password: String) =
            password
                    .toCharArray()
                    .mapIndexed { index, c ->
                        if (index > 0) password[index - 1].toInt() <= c.toInt()
                        else true
                    }
                    .all { it }
                    &&
                    password.toCharArray()
                            .map { char -> password.contains("$char$char") && !password.contains("$char$char$char") }
                            .any { it }
}