package hmrc.exercise

import org.scalatest.{WordSpecLike, Matchers, GivenWhenThen}
import org.scalatest.prop.PropertyChecks
import org.scalacheck._
import scala.util.{Success, Failure}

class ShopSpec extends WordSpecLike with Matchers {

    val Apple = "Apple"
    val Orange = "Orange"
    val shop: Shop = new Shop(Map(
        Apple -> BigDecimal("0.60"),
        Orange -> BigDecimal("0.25")))

    "A checkout system" when {
        "takes a list of items scanned at the till" should {
            "outputs the total cost" in {
                shop.checkout(List(Apple)) shouldBe Success(BigDecimal("0.60"))
                shop.checkout(List(Orange)) shouldBe Success(BigDecimal("0.25"))
                shop.checkout(List(Apple, Orange)) shouldBe Success(BigDecimal("0.85"))
                shop.checkout(List(Apple, Apple)) shouldBe Success(BigDecimal("1.20"))
                shop.checkout(List(Orange, Apple)) shouldBe Success(BigDecimal("0.85"))
                shop.checkout(List(Orange, Orange)) shouldBe Success(BigDecimal("0.50"))
                shop.checkout(List(Orange, Apple, Orange)) shouldBe Success(BigDecimal("1.10"))
                shop.checkout(List(Orange, Apple, Orange, Apple)) shouldBe Success(BigDecimal("1.70"))
                shop.checkout(List(Orange, Apple, Orange, Apple, Apple, Apple)) shouldBe Success(BigDecimal("2.90"))
            }
        }
    }

}
