package at.fhj.swengb.apps.maze

import org.scalatest.WordSpecLike

/**
  * Specification for Maze marshaller.
  */
class MazeMarshallerSpec extends WordSpecLike {

  "Mazemarshaller" should {
    ".convert(pos : Pos)" in {
      val pos = Pos(4711, 815)
      val actual: MazeProtobuf.Pos = MazeProtocol.convert(pos)

      assert(pos.x == actual.getX)
      assert(pos.y == actual.getY)
    }

    ".convert(pos : Rect)" in {
      val pos = Rect(4711.0, 815.0)
      val actual: MazeProtobuf.Rect = MazeProtocol.convert(pos)

      assert(pos.width == actual.getWidth)
      assert(pos.height == actual.getHeight)
    }

  }
  ".conect(cell: Cell) - None options" in {
    val pos = Pos(4711, 815)
    val coord = Coord(2, 2)
    val rect = Rect(5, 5)

    val cell = Cell(pos, coord, rect, None,None,None,None)
    val actual: MazeProtobuf.Cell = MazeProtocol.convert(cell)

    //Some crazy tests are going on...
    assert(cell == MazeProtocol.convert(actual))
  }

  ".conect(cell: Cell) - Some options" in {
    val pos = Pos(4711, 815)
    val coord = Coord(2, 2)
    val rect = Rect(5, 5)

    val cell = Cell(pos, coord, rect, Some(pos),None,None,None)
    val actual: MazeProtobuf.Cell = MazeProtocol.convert(cell)

    //Some crazy tests are going on...
    assert(cell == MazeProtocol.convert(actual))
  }
}
