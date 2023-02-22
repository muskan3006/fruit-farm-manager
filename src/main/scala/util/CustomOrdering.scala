package util

import model.FruitPriceDetail

object CustomOrdering {
  implicit val maxAmountOrdering: Ordering[(String, Float)] = (x: (String, Float), y: (String, Float)) => if (x._2 > y._2) 1 else if (x._2 < y._2) -1 else 0
  implicit val fruitPricesOrdering: Ordering[FruitPriceDetail] = (x: FruitPriceDetail, y: FruitPriceDetail) => if (x.price > y.price) 1 else if (x.price < y.price) -1 else 0
}
