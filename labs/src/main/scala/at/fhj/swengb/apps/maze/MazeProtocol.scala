package at.fhj.swengb.apps.maze

import java.lang

import scala.collection.JavaConverters._

/**
  * Encodes conversions between business models and protocol encodings
  */
object MazeProtocol {

  def convert(pos: MazeProtobuf.Pos): Pos = Pos(pos.getX, pos.getY)

  def convert(pbCell: MazeProtobuf.Cell): Cell = {
    val pbPos = pbCell.getPos
    val pbTopLeft = pbCell.getTopLeft
    val pbRegion = pbCell.getRegion

    val up: Option[Pos] =
      if (pbCell.getNoneUp)
        None
      else
        Some(Pos(pbCell.getUp.getX, pbCell.getUp.getY))

    val right: Option[Pos] = pbCell.getNoneRight match {
      case true  => None
      case false => Option(Pos(pbCell.getRight.getX, pbCell.getRight.getY))
    }

    val down: Option[Pos] = pbCell.getNoneDown match {
      case true  => None
      case false => Option(Pos(pbCell.getDown.getX, pbCell.getDown.getY))
    }

    val left: Option[Pos] = pbCell.getNoneLeft match {
      case true  => None
      case false => Option(Pos(pbCell.getLeft.getX, pbCell.getLeft.getY))
    }

    Cell(Pos(pbPos.getX, pbPos.getY),
         Coord(pbTopLeft.getX, pbTopLeft.getY),
         Rect(pbRegion.getWidth, pbRegion.getHeight),
         up,
         right,
         down,
         left)
  }

  def convert(end: Pos): MazeProtobuf.Pos =
    MazeProtobuf.Pos.newBuilder().setX(end.x).setY(end.y).build()

  def convert(cellRect: Rect): MazeProtobuf.Rect =
    MazeProtobuf.Rect
      .newBuilder()
      .setHeight(cellRect.height)
      .setWidth(cellRect.width)
      .build()

  def convert(cell: Cell): MazeProtobuf.Cell = {
    val pbCell = MazeProtobuf.Cell
      .newBuilder()
      .setPos(MazeProtobuf.Pos.newBuilder().setX(cell.pos.x).setY(cell.pos.y))
      .setTopLeft(MazeProtobuf.Coord.newBuilder().setX(cell.x).setY(cell.y))
      .setRegion(
        MazeProtobuf.Rect
          .newBuilder()
          .setWidth(cell.region.width)
          .setHeight(cell.region.height))

    cell.up match {
      case Some(up) => pbCell.setUp(convert(up))
      case None     => pbCell.setNoneUp(true)

    }

    cell.right match {
      case Some(right) => pbCell.setUp(convert(right))
      case None        => pbCell.setNoneRight(true)
    }

    cell.down match {
      case Some(down) => pbCell.setUp(convert(down))
      case None       => pbCell.setNoneDown(true)
    }

    cell.left match {
      case Some(left) => pbCell.setUp(convert(left))
      case None       => pbCell.setNoneLeft(true)
    }

    //Return cell
    pbCell.build()

  }

  /**
    * Provided a protobuf encoded maze, create a business model class 'maze' again
    */
  def convert(protoMaze: MazeProtobuf.Maze): Maze = {

    //Create Maze-Grid
    val mazeGrid: Array[Cell] =
      protoMaze.getGridList.asScala.map(e => convert(e)).toArray[Cell]

    //Create Maze
    Maze(
      protoMaze.getSizeX,
      protoMaze.getSizeY,
      Pos(protoMaze.getStart.getX, protoMaze.getStart.getY),
      Pos(protoMaze.getEnd.getX, protoMaze.getEnd.getY),
      mazeGrid,
      Rect(protoMaze.getCellRect.getWidth, protoMaze.getCellRect.getHeight)
    )
  }

  /**
    * Convert a business model maze to a protocol encoded maze
    */
  def convert(maze: Maze): MazeProtobuf.Maze = {
    val pCoord = MazeProtobuf.Coord.newBuilder().build

    val cells: lang.Iterable[MazeProtobuf.Cell] =
      maze.grid.map(convert).toIterable.asJava

    val pMaze = MazeProtobuf.Maze
      .newBuilder()
      .setSizeX(maze.sizeX)
      .setSizeY(maze.sizeY)
      .setStart(convert(maze.start))
      .setEnd(convert(maze.end))
      .addAllGrid(cells)
      .setCellRect(convert(maze.cellRect))
      .build()
    pMaze
  }

}
