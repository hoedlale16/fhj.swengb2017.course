package at.fhj.swengb.apps.battleship

import org.scalatest.WordSpecLike

/**
  * Specifications for the Battleship class.
  */
class SubmarineSpec extends WordSpecLike {

  val bp = BattlePos(0, 0)

  val validPositions: Set[BattlePos] = Set(BattlePos(0, 0),
    BattlePos(0, 1),
  )

  val validxPos: Set[BattlePos] = (0 until 2).map(x => BattlePos(x, 0)).toSet
  val validyPos: Set[BattlePos] = (0 until 2).map(y => BattlePos(0, y)).toSet
  val illegalPos: Set[BattlePos] = Set(BattlePos(7, 4)) ++ validxPos.tail

  // TODO WP5.1 make all tests green
  "Submarine" should {

    "a correct Submarine" in {

      val ship = Submarine("a name", validPositions)

      assert(ship != null)
    }
    "a Submarine without a name can clear the port" in {

      // intercept checks if an exception is thrown
      intercept[IllegalArgumentException] {
        val ship = Submarine("",
          validPositions)
      }

    }
    "0 pos is illegal" in intercept[IllegalArgumentException](Submarine("a", Set()))
    "1 pos is illegal" in intercept[IllegalArgumentException](Submarine("b", Set(bp)))
    "3 pos is illegal" in intercept[IllegalArgumentException](Submarine("d", Set(bp, bp, bp)))


    "pos has to be horizonal" in {
      val abc = Submarine("hallo welt", validxPos)
      assert(abc != null)
    }
    "pos has to be vertical" in {
      assert(Submarine("def", validyPos) != null)
    }

    "test shows how intercept works" in {
      intercept[IllegalArgumentException] {
        throw new IllegalArgumentException("foo")
      }
    }

    "discover if at least one x position is wrong" in {
      intercept[IllegalArgumentException] {
        val b = Submarine("a b c", Set(
          BattlePos(1, 0),
          BattlePos(0, 1),
        ))
      }
    }
    "discover if at least one y position is wrong" in {
      intercept[IllegalArgumentException] {
        val b = Submarine("a b c", Set(BattlePos(0, 1),
          BattlePos(1, 0),
        ))
      }
    }

  }
}
