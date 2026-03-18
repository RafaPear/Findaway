package pt.rafap.findaway.utils

import java.io.File
import java.time.LocalDate


fun generateUniqueTimestampedFileName(baseName: String, extension: String): String {
    val date = LocalDate.now()
    var name = "${baseName}-$date$extension"
    var count = 1
    while (File(name).exists()) {
        name = "${baseName}-$date-${count}$extension"
        count++
    }
    return name
}
