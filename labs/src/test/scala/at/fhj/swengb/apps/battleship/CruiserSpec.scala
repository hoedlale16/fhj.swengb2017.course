package at.fhj.swengb.apps.battleship

import org.scalatest.WordSpecLike

/**
  * Specifications for the Cruiser class.
  */
class CruiserSpec extends WordSpecLike {

  val bp = BattlePos(0, 0)

  val validPositions: Set[BattlePos] = Set(BattlePos(1, 1),
    BattlePos(2, 1),
    BattlePos(3, 1),
    BattlePos(4, 1),
  )

  val validxPos: Set[BattlePos] = (0 until 4).map(x => BattlePos(x, 0)).toSet
  val validyPos: Set[BattlePos] = (0 until 4).map(y => BattlePos(0, y)).toSet
  val illegalPos: Set[BattlePos] = Set(BattlePos(7, 4)) ++ validxPos.tail

  // TODO WP5.1 make all tests green
  "Cruiser" should {

    "a correct cruiser" in {

      val cruiser= Cruiser("a name", validPositions)

      assert(cruiser != null)
    }
    "a cruiser without a name can clear the port" in {

      // intercept checks if an exception is thrown
      intercept[IllegalArgumentException] {
        val cruiser = Cruiser("",
          validPositions)
      }

    }
    "0 pos is illegal" in intercept[IllegalArgumentException](Cruiser("a", Set()))
    "1 pos is illegal" in intercept[IllegalArgumentException](Cruiser("b", Set(bp)))
    "2 pos is illegal" in intercept[IllegalArgumentException](Cruiser("c", Set(bp, bp)))
    "3 pos is illegal" in intercept[IllegalArgumentException](Cruiser("d", Set(bp, bp, bp)))
    "5 pos is illegal" in intercept[IllegalArgumentException](Cruiser("f", Set(bp, bp, bp, bp, bp)))


    "pos has to be horizonal" in {
      val abc = Cruiser("hallo welt", validxPos)
      assert(abc != null)
    }
    "pos has to be vertical" in {
      assert(Cruiser("def", validyPos) != null)
    }

    "test shows how intercept works" in {
      intercept[IllegalArgumentException] {
        throw new IllegalArgumentException("foo")
      }
    }

    "discover if at least one x position is wrong" in {
      intercept[IllegalArgumentException] {
        val b = Cruiser("a b c", Set(
          BattlePos(1, 0),
          BattlePos(0, 1),
          BattlePos(0, 2),
          BattlePos(0, 3),
          BattlePos(0, 4),
        ))
      }
    }
    "discover if at least one y position is wrong" in {
      intercept[IllegalArgumentException] {
        val b = Cruiser("a b c", Set(BattlePos(0, 1),
          BattlePos(1, 0),
          BattlePos(2, 0),
          BattlePos(3, 0),
          BattlePos(4, 0),
        ))
      }
    }
    // illegalPos defines a ship which is not possible
    "pos is connected" in {
      intercept[IllegalArgumentException] {
        val b = Cruiser("a b c", Set(BattlePos(0, 0),
          BattlePos(1, 0),
          BattlePos(2, 0),
          BattlePos(3, 0),
          BattlePos(5, 0),
        ))
      }
    }

  }
}
