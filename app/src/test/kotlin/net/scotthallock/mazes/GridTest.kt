package net.scotthallock.mazes

import com.google.common.truth.Truth.assertThat
import kotlin.test.Test

class GridTest {
  @Test
  fun simpleGridTest() {
    val grid = Grid(2, 2)
    assertThat(grid[0, 0].row).isEqualTo(0)
    assertThat(grid[1, 1].row).isEqualTo(1)
    assertThat(grid[0, 0].north).isNull()
    assertThat(grid[0, 0].south).isSameInstanceAs(grid[1, 0])
    assertThat(grid[0, 0].east).isSameInstanceAs(grid[0, 1])
    assertThat(grid[0, 0].west).isNull()
    assertThat(grid[0, 0].neighbors()).hasSize(2)
    assertThat(grid[0, 0].neighbors()).contains(grid[1, 0])
    assertThat(grid[0, 0].neighbors()).contains(grid[0, 1])
  }

  @Test
  fun canIterateOverGrid() {
    val grid = Grid(2, 2)
    var atLeastOneCellVisited = false
    for (cell: Cell in grid) {
      atLeastOneCellVisited = true
      assertThat(cell).isNotNull()
    }
    assertThat(atLeastOneCellVisited).isTrue()
  }

  @Test
  fun simpleBinaryTreeTest() {
    val grid = Grid(4, 4)
    var atLeastOneCellVisited = false
    for (cell in grid) {
      atLeastOneCellVisited = true
      assertThat(cell.linksAsSet()).isEmpty()
    }
    assertThat(atLeastOneCellVisited).isTrue()
    atLeastOneCellVisited = false
    for (cell in grid.binaryTree()) {
      atLeastOneCellVisited = true
      assertThat(cell.linksAsSet()).isNotEmpty()
    }
    assertThat(atLeastOneCellVisited).isTrue()
  }
}