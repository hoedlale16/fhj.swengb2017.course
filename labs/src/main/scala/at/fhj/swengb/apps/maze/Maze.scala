package at.fhj.swengb.apps.maze

object Maze {

  val SmallMaze = Maze(10, 10, Rect(10, 10))
  val MediumMaze = Maze(40, 40, Rect(10, 10))
  val BigMaze = Maze(100, 100, Rect(5, 5))

  /**
    * Creates a maze with all doors closed
    *
    * @param sizeX
    * @param sizeY
    * @param cellRect
    * @return
    */
  def apply(sizeX: Int, sizeY: Int, cellRect: Rect): Maze = {
    val arrays: Array[Cell] =
      Array.tabulate(sizeX, sizeY)((y, x) => {
        val pos = Pos(x, y)
        val coord = Coord(x * cellRect.width, y * cellRect.height)
        Cell(pos, coord, cellRect)
      }).flatten //
    Maze(sizeX, sizeY, Pos(0, 0), Pos(sizeX, sizeY), arrays, cellRect)
  }

}

//Definiert den klassichen Default-Konstruktor aus Java
//case class => immutable datenstructur - nicht veränderbar
case class Maze(nrX: Int
                , nrY: Int
                , start: Pos
                , end: Pos
                , grid: Array[Cell]
                , cellRect: Rect) {

  val cellMap: Map[Pos, Cell] = grid.map { c => c.pos -> c }.toMap

  val cellWidth: Double = cellRect.width
  val cellHeight: Double = cellRect.height
  val boundingBox: Rect = Rect(nrX * cellWidth, nrY * cellHeight)

  def getCell(pos: Pos): Cell = grid(pos.x + pos.y * nrX)

  val topBoundary: Set[Cell] = grid.slice(0, nrX).toSet

  //es gibt bei for auch stepsizes for(i <- 100 to 5 by 10) yield ...
  // von 1-100 rückwersts(bis 5) jedoch in 10er schritten
  val rightBoundary: Set[Cell] = (for (i <- (nrX - 1) until nrX * nrY by nrX) yield {
    grid(i)
  }).toSet

  val downBoundary: Set[Cell] = grid.slice(grid.length - nrX, grid.length).toSet

  val leftBoundary: Set[Cell] = (for (i <- 0 until nrX * nrY by nrX) yield grid(i)).toSet

  val boundaryCells: Set[Cell] = topBoundary ++ rightBoundary ++ downBoundary ++ leftBoundary
}





