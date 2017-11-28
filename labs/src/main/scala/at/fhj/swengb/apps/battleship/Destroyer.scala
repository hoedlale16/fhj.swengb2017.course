package at.fhj.swengb.apps.battleship

object Destroyer {
  /**
    * This is a convenience constructor for a Destroyer. You can define a starting position and
    * a direction and don't need to define all positions explicitly.
    *
    * @param name the name of the Destroyer
    * @param p    starting position
    * @param d    direction
    * @return
    */
  def apply(name: String, p: BattlePos, d: Direction): Destroyer = {
    d match {
      case Horizontal =>
        Destroyer(name, (p.x until (p.x + 3)).map(x => BattlePos(x, p.y)).toSet)
      case Vertical =>
        Destroyer(name, (p.y until (p.y + 3)).map(y => BattlePos(p.x, y)).toSet)
    }
  }
}

case class Destroyer(name: String, positions: Set[BattlePos]) extends Vessel {

  // every Destroyer has to have a name
  require(name.nonEmpty, "Name has to be set.")

  // require proofs that positions is of size 3
  require(
    positions.size == 3,
    s"For mighty Destroyer '$name' required 3 positions, but got ${positions.size}.")

  // mission: we have to proof that all x positions or all y positions are the same and that all cells are connected.
  private val allXCoordinatesAreTheSame = positions.map(_.x).size == 1
  private val allYCoordinatesAreTheSame = positions.map(_.y).size == 1

  val allCoordinatesAreTheSameForXOrY
  : Boolean = allXCoordinatesAreTheSame || allYCoordinatesAreTheSame

  // either all x coordinates are the same or all y coordinates are the same
  require(allCoordinatesAreTheSameForXOrY)

  // additional requirement is needed to check for connectedness
  def isChained(list: Seq[Int], current: Int): Boolean = {
    if (current >= list.length - 1)
    /*Looped over all items found nothing weired -> all elements sorted we're done*/
      true;
    else if ((list(current) + 1) == list(current + 1))
    /*Looks good, check next... */
      isChained(list, current + 1)
    else
    /*Ha- There it is! => unsorted elements found! */
      false;
  }

  //Sort list and check if sorted chained is chained
  val sortedXList = positions.map(_.x).toSeq.sorted;
  val sortedYList = positions.map(_.y).toSeq.sorted;

  require(isChained(sortedXList, 0), "Destroyer positions required to be chained")
  require(isChained(sortedYList, 0), "Destroyer positions required to be chained")
}
