package net.scotthallock.mazes

class Cell(val row: Int, val column: Int) {
  var north: Cell = sentinelCell
  var south: Cell = sentinelCell
  var east: Cell = sentinelCell
  var west: Cell = sentinelCell

  companion object {
    val sentinelCell = Cell(-1, -1)
  }

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

  fun neighbors(): List<Cell> = listOf(north, south, east, west).filter({it != sentinelCell})
}