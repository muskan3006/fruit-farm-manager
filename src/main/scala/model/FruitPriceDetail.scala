package model

import java.time.LocalDate


case class FruitPriceDetail(fruit: String, date: Option[LocalDate], price: Float)
