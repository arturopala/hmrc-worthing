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
                shop.checkout(List(Orange, Apple)) shouldBe Success(BigDecimal("0.85"))
                shop.checkout(List(Orange, Orange)) shouldBe Success(BigDecimal("0.50"))
            }
            "fail when item not found in pricelist" in {
                shop.checkout(List("Foo")) should matchPattern { case Failure(_) => }
                shop.checkout(List("Foo", Apple)) should matchPattern { case Failure(_) => }
                shop.checkout(List("Foo", Apple, Orange)) should matchPattern { case Failure(_) => }
            }
            "apply special offer" which {
                "buy one, get one free on Apples" in {
                    shop.checkout(List(Apple, Apple)) shouldBe Success(BigDecimal("0.60"))
                    shop.checkout(List(Apple, Apple, Apple)) shouldBe Success(BigDecimal("1.20"))
                    shop.checkout(List(Apple, Apple, Apple, Apple)) shouldBe Success(BigDecimal("1.20"))
                    shop.checkout(List(Orange, Apple, Orange)) shouldBe Success(BigDecimal("1.10"))
                    shop.checkout(List(Orange, Apple, Orange, Apple)) shouldBe Success(BigDecimal("1.10"))
                    shop.checkout(List(Orange, Apple, Orange, Apple, Apple, Apple)) shouldBe Success(BigDecimal("1.70"))
                }
                "buy 3 for the price of 2 on Oranges" in {
                    shop.checkout(List(Orange, Orange)) shouldBe Success(BigDecimal("0.50"))
                    shop.checkout(List(Orange, Orange, Orange)) shouldBe Success(BigDecimal("0.50"))
                    shop.checkout(List(Orange, Orange, Orange, Orange)) shouldBe Success(BigDecimal("0.75"))
                    shop.checkout(List(Orange, Orange, Orange, Orange, Orange)) shouldBe Success(BigDecimal("1.00"))
                    shop.checkout(List(Orange, Orange, Orange, Orange, Orange, Orange)) shouldBe Success(BigDecimal("1.00"))
                    shop.checkout(List(Orange, Orange, Orange, Orange, Orange, Orange, Orange)) shouldBe Success(BigDecimal("1.25"))
                    shop.checkout(List(Orange, Apple, Orange, Orange)) shouldBe Success(BigDecimal("1.10"))
                }
            }
        }
    }

}
