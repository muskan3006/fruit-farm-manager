import model.{FruitGathererDetail, FruitPriceDetail}

import java.time.Month
import scala.Console.println
import scala.collection.immutable

object DetailsProcessor {

  import util.CustomOrdering._

  def findBestGathererBasedOnAmountPerMonth(monthlyFruitGathering: Map[Option[Month], List[FruitGathererDetail]]): Unit = {
    monthlyFruitGathering.foreach { case (month, fruitGathererDetails) =>
      val amountOfFruitCollectedByGatherer = fruitGathererDetails.groupBy(_.gatherer).map { case (gatherer, fruitGathererDetails) => (gatherer, fruitGathererDetails.map(_.amount).sum) }
      val (gatherer, amount) = amountOfFruitCollectedByGatherer.max
      println(s"In month $month, gatherer $gatherer gathered maximum amount of fruits, i.e. $amount")
    }
  }

  def findBestSpecificFruitGatherer(fruitGathererDetails: List[FruitGathererDetail]): Unit = {
    val groupByFruit = fruitGathererDetails.groupBy(_.fruit)
    groupByFruit.foreach { case (fruit, fruitGathererDetails) =>
      val amountOfSpecificFruitCollectedByGatherer = fruitGathererDetails.groupBy(_.gatherer).map { case (gatherer, fruitGathererDetails) => (gatherer, fruitGathererDetails.map(_.amount).sum) }
      val (gatherer, amount) = amountOfSpecificFruitCollectedByGatherer.max
      println(s"For fruit $fruit, gatherer $gatherer has gathered maximum amount i.e. $amount")
    }
  }

  def findBestEarningFruitOverall(fruitPriceDetails: List[FruitPriceDetail]): Unit = {
    val mostProfitableFruit = fruitPriceDetails.max
    println(s"Overall, the most profitable fruit is ${mostProfitableFruit.fruit} with price ${mostProfitableFruit.price}")
  }

  def findBestEarningFruitMonthly(monthlyFruitPricesDetails: Map[Option[Month], List[FruitPriceDetail]]): Unit = {
    monthlyFruitPricesDetails.foreach { case (month, fruitPrices) =>
      val mostProfitableFruitOfTheMonth = fruitPrices.max
      println(s"In month $month, the most profitable fruit is ${mostProfitableFruitOfTheMonth.fruit} with price ${mostProfitableFruitOfTheMonth.price}")
    }
  }

  def findLeastEarningFruitOverall(fruitPriceDetails: List[FruitPriceDetail]): Unit = {
    val mostProfitableFruit = fruitPriceDetails.min
    println(s"Overall, the least profitable fruit is ${mostProfitableFruit.fruit} with price ${mostProfitableFruit.price}")
  }

  def findLeastEarningFruitMonthly(monthlyFruitPricesDetails: Map[Option[Month], List[FruitPriceDetail]]): Unit = {

    monthlyFruitPricesDetails.foreach { case (month, fruitPrices) =>
      val mostProfitableFruitOfTheMonth = fruitPrices.min
      println(s"In month $month, the least profitable fruit is ${mostProfitableFruitOfTheMonth.fruit} with price ${mostProfitableFruitOfTheMonth.price}")
    }
  }

  def findGathererWhoContributedMostOverall(fruitPriceDetails: List[FruitPriceDetail], fruitGathererDetails: List[FruitGathererDetail]): Unit = {
    val groupByFruitGatherer = fruitGathererDetails.groupBy(_.gatherer)
    val gathererAndTheirOverallContribution = groupByFruitGatherer.map { case (gatherer, fruitGathererDetails) =>
      val totalAmountContributed = fruitGathererDetails.foldLeft(0.0F)((totalAmount, fruitGathererDetail) => {
        val fruitPriceOnThatDayOption = fruitPriceDetails.find(fruitPriceDetail => fruitPriceDetail.date == fruitGathererDetail.date && fruitPriceDetail.fruit == fruitGathererDetail.fruit).map(_.price)
        fruitPriceOnThatDayOption match {
          case Some(fruitPriceOnThatDay) => totalAmount + (fruitPriceOnThatDay * fruitGathererDetail.amount)
          case None => totalAmount
        }
      })
      (gatherer, totalAmountContributed)
    }
    val max = gathererAndTheirOverallContribution.max
    println(s"Overall, maximum contribution in terms of money is done by ${max._1} i.e. ${max._2}")
  }

  def findGathererWhoContributedMostMonthly(monthlyFruitPricesDetails: Map[Option[Month], List[FruitPriceDetail]], monthlyFruitGathering: Map[Option[Month], List[FruitGathererDetail]]): immutable.Iterable[Unit] = {
    monthlyFruitGathering.map { case (month, fruitGathererDetails) =>
      val priceDetailsOfTheMonth = monthlyFruitPricesDetails.getOrElse(month, List())
      val gathererAndMoneyContributed = fruitGathererDetails.groupBy(_.gatherer).map { case (gatherer, fruitGathererDetailsOfGatherer) =>
        val moneyEarned = fruitGathererDetailsOfGatherer.foldLeft(0.0F)((result, fruitGathererDetailOfGatherer) => {
          val priceOption = priceDetailsOfTheMonth.find(fruitPriceDetail => fruitPriceDetail.date == fruitGathererDetailOfGatherer.date && fruitPriceDetail.fruit == fruitGathererDetailOfGatherer.fruit).map(_.price)
          priceOption match {
            case Some(price) =>
              result + (price * fruitGathererDetailOfGatherer.amount)
            case None => result
          }
        })
        (gatherer, moneyEarned)
      }
      val (gatherer, moneyContribution) = gathererAndMoneyContributed.max
      println(s"In month $month, maximum contribution is done by $gatherer i.e. $moneyContribution")
    }
  }
}
