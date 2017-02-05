package hmrc.exercise

import scala.util.Try

class Shop(pricelist: Map[String, BigDecimal]) {

    def priceOf(item: String) = pricelist.get(item).getOrElse(throw new RuntimeException(s"Price of $item not found"))

    def checkout(items: List[String]): Try[BigDecimal] = Try {
        items.map(priceOf).sum
    }

}
