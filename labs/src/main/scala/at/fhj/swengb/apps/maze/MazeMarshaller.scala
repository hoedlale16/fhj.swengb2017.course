package at.fhj.swengb.apps.maze

import java.lang
import java.nio.file.{Files, Paths}

import scala.collection.JavaConverters


/**
  * Showcases how to serialize and deserialize a maze from disc via protobuf
  */
object MazeMarshaller {

  import JavaConverters._

  def convert(end: Pos): MazeProtobuf.Pos = MazeProtobuf.Pos.newBuilder().build()

  def convert(cell: Cell): MazeProtobuf.Cell = MazeProtobuf.Cell.newBuilder().build

  def convert(cellRect: Rect): MazeProtobuf.Rect = MazeProtobuf.Rect.newBuilder().build()


  /**
    * Provided a protobuf encoded maze, create a business model class 'maze' again
    *
    * @param protoMaze
    * @return
    */
  // TODO implement missing functionality
  def convert(protoMaze: MazeProtobuf.Maze): Maze = {
    println(protoMaze.getSizeX)
    println(protoMaze.getSizeY)
    Maze(
      protoMaze.getSizeX,
      protoMaze.getSizeY,
      Pos(0, 0), // TODO read from proto
      Pos(1, 0), // TODO read from proto
      Array.fill(protoMaze.getSizeX * protoMaze.getSizeY)(Cell(Pos(0, 0), Coord(0, 0), Rect(0, 0))), // TODO read from proto
      Rect(100, 100) // TODO read from proto
    )
  }

  def main(args: Array[String]): Unit = {

    val maze: Maze = MazeGenerator.gen(PositiveInt(10), PositiveInt(10), Pos(0, 0), Pos(9, 9), Rect(100, 100))

    val pCoord = MazeProtobuf.Coord.newBuilder().build

    val cells: lang.Iterable[MazeProtobuf.Cell] = maze.grid.map(convert).toIterable.asJava

    val pMaze = MazeProtobuf.Maze.newBuilder()
      .setSizeX(maze.sizeX)
      .setSizeY(maze.sizeY)
      .setStart(convert(maze.start))
      .setEnd(convert(maze.end))
      .addAllGrid(cells)
      .setCellRect(convert(maze.cellRect))
      .build()

    val path = Paths.get("target/MazeProtobuf.bin")
    val outstream = Files.newOutputStream(path)

    pMaze.writeTo(outstream)

    println("Wrote to " + path.toAbsolutePath.toString)

    val in = Files.newInputStream(path)

    val protoMaze: MazeProtobuf.Maze = MazeProtobuf.Maze.parseFrom(in)

    val m: Maze = convert(protoMaze)

    println("Read maze from disc: sizeX:" + m.sizeX)
    // print out other data ...
  }

}
