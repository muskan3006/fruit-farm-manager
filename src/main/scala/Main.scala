import DetailsProcessor._
import model.{FruitGathererDetail, FruitPriceDetail}
import util.DateUtil.parseLocalDate
import util.StringUtils.trimFirstAndLastCharacter

import java.time.Month

object Main extends App {
  private val bufferedSourcePrices = io.Source.fromFile("src/main/resources/prices.csv")
  private val bufferedSourceFruitGatherer = io.Source.fromFile("src/main/resources/harvest.csv")
  private val pricesDetailsIterable = for (line <- bufferedSourcePrices.getLines) yield line.split(",")
  private val fruitGathererIterable = for (line <- bufferedSourceFruitGatherer.getLines) yield line.split(",")

  val fruitPriceDetails: List[FruitPriceDetail] = pricesDetailsIterable.toList.tail.map {
    case Array(fruit, dateString, price) =>
      FruitPriceDetail(trimFirstAndLastCharacter(fruit), parseLocalDate(trimFirstAndLastCharacter(dateString)), trimFirstAndLastCharacter(price).toFloat)
  }

  val fruitGathererDetails: List[FruitGathererDetail] = fruitGathererIterable.toList.tail map {
    case Array(gatherer, dateString, fruit, amount) =>
      FruitGathererDetail(gatherer, parseLocalDate(dateString), fruit, amount.toFloat)
  }

  private val monthlyFruitGatheringDetails: Map[Option[Month], List[FruitGathererDetail]] = fruitGathererDetails.groupBy(_.date.map(_.getMonth))
  private val monthlyFruitPricesDetails: Map[Option[Month], List[FruitPriceDetail]] = fruitPriceDetails.groupBy(_.date.map(_.getMonth))

  println("=============================== MONTHLY MAX AMOUNT OF FRUIT COLLECTED =================================")
  findBestGathererBasedOnAmountPerMonth(monthlyFruitGatheringDetails)
  println("================================ BEST SPECIFIC FRUIT GATHERER ================================")
  findBestSpecificFruitGatherer(fruitGathererDetails)
  println("================================ BEST EARNING FRUIT OVERALL ================================")
  findBestEarningFruitOverall(fruitPriceDetails)
  println("================================ BEST EARNING FRUIT OF THE MONTH ================================")
  findBestEarningFruitMonthly(monthlyFruitPricesDetails)
  println("================================= LEAST EARNING FRUIT OVERALL ===============================")
  findLeastEarningFruitOverall(fruitPriceDetails)
  println("================================ LEAST EARNING FRUIT OF THE MONTH ================================")
  findLeastEarningFruitMonthly(monthlyFruitPricesDetails)
  println("================================= HIGHEST MONTHLY CONTRIBUTION ===============================")
  findGathererWhoContributedMostMonthly(monthlyFruitPricesDetails, monthlyFruitGatheringDetails)
  println("================================== HIGHEST OVERALL CONTRIBUTION ==============================")
  findGathererWhoContributedMostOverall(fruitPriceDetails, fruitGathererDetails)
  bufferedSourcePrices.close
}