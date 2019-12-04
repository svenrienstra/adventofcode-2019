package nl.rienstra.advent.day4

object PasswordGenerator {

    tailrec fun generatePasswords(current: String, end: String, passwords: List<String> = emptyList()): List<String> =
            when (current.toInt() < end.toInt()) {
                true -> generatePasswords(next(current), end,
                        if (isValid(next(current))) passwords.plus(next(current)) else passwords)
                false -> passwords
            }

    private fun next(current: String) = (current.toInt() + 1).toString()

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