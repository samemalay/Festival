package code

import code.model.JsonUtil
import org.scalatest._
import matchers.should._
import org.scalatest.flatspec.AnyFlatSpec

class FestivalSpec extends AnyFlatSpec with Matchers {

  "The App" should "provide plans for the shows" in {
    val shows = JsonUtil.showFrom("shows.json")
    val expectedPlans = JsonUtil.planFrom("plans.json")
    val plans = Festival.makePlans(shows.toSet)
    plans should be (expectedPlans)
  }

}
