package util

object StringUtils {
  def trimFirstAndLastCharacter(inputString: String): String = inputString.slice(1, inputString.length - 1)
}
