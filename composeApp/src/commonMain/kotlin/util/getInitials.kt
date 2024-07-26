package util

/**
 * Returns the initials of a person's full name.
 *
 * getInitials("Mostafa Tabasi") -> "MT"
 * getInitials("Mostafa") -> "MO"
 * getInitials("Mostafa Middle Tabasi") -> "MT"
 */
fun getInitials(fullName: String): String {
    val names = fullName
        .split(" ")
        .filter { it.isNotBlank() }
    return when {
        names.size == 1 && names.first().length <= 1 -> {
            names.first().first().toString().uppercase()
        }

        names.size == 1 && names.first().length > 1 -> {
            val name = names.first().uppercase()
            "${name.first()}${name[1].uppercase()}"
        }

        names.size == 2 || names.size >= 3 -> {
            val firstName = names.first().uppercase()
            val lastName = names.last().uppercase()
            "${firstName.first()}${lastName.first()}"
        }

        else -> ""
    }
}