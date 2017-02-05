package hmrc.exercise

import scala.util.Try

class Shop(pricelist: Map[String, BigDecimal]) {

    def priceOf(item: String) = pricelist.get(item).getOrElse(throw new RuntimeException(s"Price of $item not found"))

    def checkout(items: List[String]): Try[BigDecimal] = Try {
        items.map(priceOf).sum
    } flatMap {
        price => specialOffers(items).map(discount => price - discount)
    }

    def specialOffers(items: List[String]): Try[BigDecimal] = Try {

        val buyOneGetOnForFreeOnApplesDiscount =
            (items.filter(_ == "Apple").size / 2) * priceOf("Apple")

        val buy3ForThePriceOf2OnOrangesDiscount =
            items
                .filter(_ == "Orange")
                .zipWithIndex
                .collect { case (item, i) if i % 3 == 2 => priceOf(item) }
                .sum

        buyOneGetOnForFreeOnApplesDiscount +
            buy3ForThePriceOf2OnOrangesDiscount
    }

}
