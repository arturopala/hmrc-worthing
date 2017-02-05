package hmrc.exercise

import scala.util.Try

class Shop(pricelist: Map[String, BigDecimal]) {

    def checkout(items: List[String]): Try[BigDecimal] = ???

}
