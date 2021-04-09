package net.scotthallock.mazes

import com.google.common.truth.Truth.assertThat
import kotlin.test.Test

class GridTest {
  @Test
  fun simpleGridTest() {
    val grid = Grid(2, 2)
    assertThat(grid[0, 0].row).isEqualTo(0)
    assertThat(grid[1, 1].row).isEqualTo(1)
    assertThat(grid[0, 0].north).isEqualTo(Cell.sentinelCell)
    assertThat(grid[0, 0].south).isSameInstanceAs(grid[1, 0])
    assertThat(grid[0, 0].east).isSameInstanceAs(grid[0, 1])
    assertThat(grid[0, 0].west).isEqualTo(Cell.sentinelCell)
    assertThat(grid[0, 0].neighbors()).hasSize(2)
    assertThat(grid[0, 0].neighbors()).contains(grid[1, 0])
    assertThat(grid[0, 0].neighbors()).contains(grid[0, 1])
  }

  @Test
  fun canIterateOverGrid() {
    val grid = Grid(2, 2)
    var cellsVisited = 0
    for (cell: Cell in grid) {
      cellsVisited++
      assertThat(cell).isNotNull()
    }
    assertThat(cellsVisited).isEqualTo(4)
  }

  @Test
  fun simpleBinaryTreeTest() {
    val grid = Grid(4, 4)
    var cellsVisited = 0
    for (cell in grid) {
      cellsVisited++
      assertThat(cell.linksAsSet()).isEmpty()
    }
    assertThat(cellsVisited).isEqualTo(16)
    cellsVisited = 0
    for (cell in grid.binaryTree()) {
      cellsVisited++
      assertThat(cell.linksAsSet()).isNotEmpty()
    }
    assertThat(cellsVisited).isEqualTo(16)
  }

  @Test
  fun simpleSidewinderTest() {
    val grid = Grid(4, 4)
    var cellsVisited = 0
    for (cell in grid) {
      cellsVisited++
      assertThat(cell.linksAsSet()).isEmpty()
    }
    assertThat(cellsVisited).isEqualTo(16)
    cellsVisited = 0
    for (cell in grid.sidewinder()) {
      cellsVisited++
      assertThat(cell.linksAsSet()).isNotEmpty()
    }
    assertThat(cellsVisited).isEqualTo(16)
  }
}