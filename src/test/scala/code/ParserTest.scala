package code

import code.model._

import org.scalatest._
import matchers.should._
import org.scalatest.flatspec.AnyFlatSpec

class ParserTest extends AnyFlatSpec with Matchers {

  "The App" should "read Show json correctly" in {
    val shows = JsonUtil.showFrom("shows.json")
    shows(0).priority should be (5)
  }

  "The App" should "read Plan json correctly" in {
    val plans = JsonUtil.planFrom("plans.json")
    plans(1).band should be ("Pearl Jam")
  }

}
