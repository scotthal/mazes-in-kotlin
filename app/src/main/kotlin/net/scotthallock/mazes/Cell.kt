package net.scotthallock.mazes

class Cell(val row: Int, val column: Int) {
  var north: Cell? = null
  var south: Cell? = null
  var east: Cell? = null
  var west: Cell? = null
  private val links: MutableMap<Cell, Boolean> = mutableMapOf()

  fun link(cell: Cell, bidi: Boolean = true) {
    links[cell] = true
    if (bidi) {
      cell.link(this, false)
    }
  }

  fun unlink(cell: Cell, bidi: Boolean = true) {
    links.remove(cell)
    if (bidi) {
      cell.unlink(this, false)
    }
  }

  fun linksAsSet() = links.keys
  fun isLinkedTo(cell: Cell) = links.containsKey(cell)

  fun neighbors(): List<Cell> {
    val neighborsList: MutableList<Cell?> = mutableListOf(north, south, east, west)
    return neighborsList.filterNotNull().toList()
  }
}