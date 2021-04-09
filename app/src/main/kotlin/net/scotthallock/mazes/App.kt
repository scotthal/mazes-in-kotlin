package net.scotthallock.mazes

fun main() {
  println(Grid(9, 9).binaryTree())
  val sidewinder = Grid(9, 9).sidewinder()
  println(sidewinder)
  println(sidewinder.toSvg())
}
