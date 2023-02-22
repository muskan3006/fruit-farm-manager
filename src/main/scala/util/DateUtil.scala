package util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import scala.util.Try

object DateUtil {
  def parseLocalDate(timeStamp: String): Option[LocalDate] = {
    val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    Try(LocalDate.parse(timeStamp, pattern)).toOption
  }
}
