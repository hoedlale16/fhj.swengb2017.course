package at.fhj.swengb.apps.helloworld

import org.scalatest._

class HelloSpec extends FlatSpec with Matchers {
  "The Hello object" should "say Servus" in {
    Hello.greeting shouldEqual "Servus"
  }
}


