package net.scotthallock.mazes

import kotlin.random.Random

class Grid(val rowCount: Int, val columnCount: Int) {
  private val grid: Array<Array<Cell>> = Array(rowCount) {row -> Array(columnCount) {column -> Cell(row, column)}}
  init {
    for (row in grid) {
      for (cell in row) {
        if (cell.row > 0) cell.north = grid[cell.row - 1][cell.column]
        if (cell.row < grid.size - 1) cell.south = grid[cell.row + 1][cell.column]
        if (cell.column > 0) cell.west = grid[cell.row][cell.column -1]
        if (cell.column < grid[cell.row].size - 1) cell.east = grid[cell.row][cell.column + 1]
      }
    }
  }

  operator fun get(row: Int, column: Int): Cell = grid[row][column]
  operator fun iterator(): Iterator<Cell> = GridIterator()
  inner class GridIterator(): Iterator<Cell> {
    var row = 0
    var column = -1
    override operator fun hasNext(): Boolean = ((row != grid.size - 1) || (column != grid[row].size - 1))
    override operator fun next(): Cell {
      return when {
        column < grid[row].size - 1 -> grid[row][++column]
        row < grid.size - 1 -> {
          column = 0
          grid[++row][column]
        }
        else -> throw IllegalStateException()
      }
    }
  }

  override fun toString(): String {
    val outputBuilder = StringBuilder("+")
    for (column in 0 until columnCount) {
      outputBuilder.append("---+")
    }
    outputBuilder.appendLine()

    for (row in 0 until rowCount) {
      val topBuilder = StringBuilder("|")
      val bottomBuilder = StringBuilder("+")

      for (column in 0 until columnCount) {
        val body = "   "
        val eastBoundary =
          if (grid[row][column].isLinkedTo(grid[row][column].east)) " " else "|"
        topBuilder.append(body).append(eastBoundary)

        val southBoundary = if (grid[row][column].isLinkedTo(grid[row][column].south)) "   " else "---"
        val corner = "+"
        bottomBuilder.append(southBoundary).append(corner)
      }
      outputBuilder.appendLine(topBuilder)
      outputBuilder.appendLine(bottomBuilder)
    }

    return outputBuilder.toString()
  }

  fun toSvg(cellSize: Int = 20): String {
    val imageWidth = cellSize * columnCount
    val imageHeight = cellSize * rowCount
    val outputBuilder = StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>")
    outputBuilder.appendLine()
    outputBuilder.appendLine("<svg version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" baseProfile=\"full\" width=\"${imageWidth + 1}\" height=\"${imageHeight + 1}\">")
    outputBuilder.appendLine("<rect width=\"100%\" height=\"100%\" fill=\"white\" />")

    for (cell in this) {
      val left = cell.column * cellSize
      val top = cell.row * cellSize
      val right = (cell.column + 1) * cellSize
      val bottom = (cell.row + 1) * cellSize

      if (cell.north == Cell.sentinelCell) {
        outputBuilder.appendLine("<line x1=\"${left}\" y1=\"${top}\" x2=\"${right}\" y2=\"${top}\" stroke=\"black\" stroke-width=\"1\" />")
      }
      if (cell.west == Cell.sentinelCell) {
        outputBuilder.appendLine("<line x1=\"${left}\" y1=\"${top}\" x2=\"${left}\" y2=\"${bottom}\" stroke=\"black\" stroke-width=\"1\" />")
      }
      if (!cell.isLinkedTo(cell.east)) {
        outputBuilder.appendLine("<line x1=\"${right}\" y1=\"${top}\" x2=\"${right}\" y2=\"${bottom}\" stroke=\"black\" stroke-width=\"1\" />")
      }
      if (!cell.isLinkedTo(cell.south)) {
        outputBuilder.appendLine("<line x1=\"${left}\" y1=\"${bottom}\" x2=\"${right}\" y2=\"${bottom}\" stroke=\"black\" stroke-width=\"1\" />")
      }
    }

    outputBuilder.appendLine("</svg>")
    return outputBuilder.toString()
  }
}

fun Grid.binaryTree(): Grid {
  for (cell in this) {
    when {
      (cell.north != Cell.sentinelCell) && (cell.east != Cell.sentinelCell) -> {
        if (Random.nextInt(0, 100) < 50) {
          cell.link(cell.north)
        } else {
          cell.link(cell.east)
        }
      }
      cell.north != Cell.sentinelCell -> cell.link(cell.north)
      cell.east != Cell.sentinelCell -> cell.link(cell.east)
      else -> {}
    }
  }
  return this
}

fun Grid.sidewinder(): Grid {
  val run: MutableList<Cell> = mutableListOf()
  for (cell in this) {
    run.add(cell)

    val atEasternBoundary = (cell.east == Cell.sentinelCell)
    val atNorthernBoundary = (cell.north == Cell.sentinelCell)
    val shouldCloseOut = atEasternBoundary || (!atNorthernBoundary && Random.nextInt(0, 2) == 0)
    if (shouldCloseOut) {
      val member = run.random()
      if (member.north != Cell.sentinelCell) member.link(member.north)
      run.clear()
    } else {
      cell.link(cell.east)
    }
  }
  return this
}