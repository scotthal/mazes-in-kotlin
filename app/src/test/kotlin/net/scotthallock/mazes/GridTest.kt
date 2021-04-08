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
    for (cell: Cell in grid) {
      assertThat(cell).isNotNull()
    }
  }

  @Test
  fun simpleBinaryTreeTest() {
    val grid = Grid(4, 4)
    for (cell in grid) {
      assertThat(cell.linksAsSet()).isEmpty()
    }
    for (cell in grid.binaryTree()) {
      assertThat(cell.linksAsSet()).isNotEmpty()
    }
  }
}