package model

import java.time.LocalDate

case class FruitGathererDetail(gatherer: String, date: Option[LocalDate], fruit: String, amount: Float)
