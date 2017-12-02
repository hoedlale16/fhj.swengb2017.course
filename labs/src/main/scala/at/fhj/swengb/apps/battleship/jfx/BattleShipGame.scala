package at.fhj.swengb.apps.battleship.jfx

import javafx.scene.layout.GridPane

import at.fhj.swengb.apps.battleship.{BattleField, BattlePos, Vessel}


case class BattleShipGame(battleField: BattleField,
                          getCellWidth: Int => Double,
                          getCellHeight: Int => Double,
                          log: String => Unit) {

  var hits: Map[Vessel, Set[BattlePos]] = Map()

  var sunkShips: Set[Vessel] = Set()

  /**
    * We don't ever change cells, they should be initialized only once.
    */
  private val cells: Seq[BattleCell] = for {x <- 0 until battleField.width
                                            y <- 0 until battleField.height
                                            pos = BattlePos(x, y)} yield {
    BattleCell(BattlePos(x, y), getCellWidth(x), getCellHeight(y), log, battleField.fleet.findByPos(pos), updateGameState)
  }

  def updateGameState(vessel: Vessel, pos: BattlePos): Unit = {

    if ( ! sunkShips.contains(vessel)) {
      log(s"Hit an enemy vessel!")

      //It is the first hit of the vessel
      if (hits.contains(vessel)) {

        //Vessel already hit at least once
        val currentHits: Set[BattlePos] = hits(vessel)
        val updatedHits: Set[BattlePos] = currentHits + pos

        if (currentHits.contains(pos)) {
          log(s"Vessel already hit and destroyed at position <" + pos + ">!")
        } else {
          //Not hit yet, add position to list and update hit map
          hits = hits.updated(vessel, updatedHits)

          //WP2 => Check if ship is totatlly destroyed
          if (updatedHits.equals(vessel.occupiedPos)) {
            log("Vessel" + vessel.name + "was totally destroyed and sunk!")

            //Add ship to sunk sihps
            sunkShips = sunkShips + vessel

            //Recolor sunk ship
            for {x <- vessel.startPos.x until vessel.startPos.x + vessel.size
                 y <- vessel.startPos.y until vessel.startPos.y + vessel.size} yield {

              //if ( cells.find(p => p.pos.x == x && p.pos.y == y) )

            }

          }
        }
      } else {
        hits = hits.updated(vessel, Set(pos))
      }

      //WP3 => Check if all vessels are destroyed now
      if (sunkShips.equals(battleField.fleet.vessels)) {
        log("Game over - All Ships destroyed!")
      }

    }
    else {
        log("Hit Water - Ship already on the ground!");
      }
  }


  // 'display' cells
  def init(gridPane: GridPane): Unit = {
    gridPane.getChildren.clear()
    for (c <- cells) gridPane.add(c, c.pos.x, c.pos.y)
    cells.foreach(c => c.init)
  }


  /**
    * Create a new game.
    *
    * This means
    *
    * - resetting all cells to 'empty' state
    * - placing your ships at random on the battleground
    *
    */
  def newGame(gridPane: GridPane) = init(gridPane)


}
