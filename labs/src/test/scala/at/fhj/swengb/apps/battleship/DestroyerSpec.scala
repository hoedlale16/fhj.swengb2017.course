package at.fhj.swengb.apps.battleship

import org.scalatest.WordSpecLike

/**
  * Specifications for the Destroyer class.
  */
class DestroyerSpec extends WordSpecLike {

  val bp = BattlePos(0, 0)

  val validPositions: Set[BattlePos] = Set(BattlePos(1, 0),
    BattlePos(1, 1),
    BattlePos(1, 2),
  )

  val validxPos: Set[BattlePos] = (0 until 3).map(x => BattlePos(x, 0)).toSet
  val validyPos: Set[BattlePos] = (0 until 3).map(y => BattlePos(0, y)).toSet
  val illegalPos: Set[BattlePos] = Set(BattlePos(7, 4)) ++ validxPos.tail

  // TODO WP5.1 make all tests green
  "Destroyer" should {

    "a correct Destroyer" in {

      val ship = Destroyer("a name", validPositions)

      assert(ship != null)
    }
    "a Destroyer without a name can clear the port" in {

      // intercept checks if an exception is thrown
      intercept[IllegalArgumentException] {
        val ship = Destroyer("",
          validPositions)
      }

    }
    "0 pos is illegal" in intercept[IllegalArgumentException](Destroyer("a", Set()))
    "1 pos is illegal" in intercept[IllegalArgumentException](Destroyer("b", Set(bp)))
    "2 pos is illegal" in intercept[IllegalArgumentException](Destroyer("c", Set(bp, bp)))
    "4 pos is illegal" in intercept[IllegalArgumentException](Destroyer("e", Set(bp, bp, bp, bp)))


    "pos has to be horizonal" in {
      val abc = Destroyer("hallo welt", validxPos)
      assert(abc != null)
    }
    "pos has to be vertical" in {
      assert(Destroyer("def", validyPos) != null)
    }

    "test shows how intercept works" in {
      intercept[IllegalArgumentException] {
        throw new IllegalArgumentException("foo")
      }
    }

    "discover if at least one x position is wrong" in {
      intercept[IllegalArgumentException] {
        val b = Destroyer("a b c", Set(
          BattlePos(1, 0),
          BattlePos(0, 1),
          BattlePos(0, 2),
        ))
      }
    }
    "discover if at least one y position is wrong" in {
      intercept[IllegalArgumentException] {
        val b = Destroyer("a b c", Set(BattlePos(0, 1),
          BattlePos(1, 0),
          BattlePos(2, 0),
          BattlePos(4, 0),
        ))
      }
    }
    // illegalPos defines a ship which is not possible
    "pos is connected" in {
      intercept[IllegalArgumentException] {
        val b = Destroyer("a b c", Set(BattlePos(0, 0),
          BattlePos(1, 0),
          BattlePos(3, 0),
        ))
      }
    }

  }
}
