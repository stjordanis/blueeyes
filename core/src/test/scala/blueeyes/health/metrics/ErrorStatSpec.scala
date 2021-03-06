package blueeyes.health.metrics

import org.specs2.mutable.Specification
import blueeyes.json._

class ErrorStatSpec extends Specification{
  "counts errors" in{
    val stats = new ErrorStat()

    stats += new NullPointerException()
    stats += new NullPointerException()

    stats.count mustEqual (2)
  }
  "creates details" in{
    val stats = new ErrorStat()

    stats += new NullPointerException()
    stats += new NullPointerException()
    stats += new RuntimeException()

    stats.details.get(classOf[NullPointerException]).get mustEqual (2)
    stats.details.get(classOf[RuntimeException]).get mustEqual (1)
  }

  "composes ErrorStat" in{
    val stats = new ErrorStat()

    stats += new NullPointerException()
    stats += new NullPointerException()

    stats.toJValue mustEqual (JObject(JField("errorCount", JNum(2)) :: JField("errorDistribution", JObject(JField(classOf[NullPointerException].getName, JNum(2)) :: Nil)) :: Nil))
  }
}