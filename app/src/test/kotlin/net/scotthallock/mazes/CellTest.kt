package net.scotthallock.mazes

import com.google.common.truth.Truth.assertThat
import kotlin.test.Test

class CellTest {
  @Test
  fun cellTracksItsLocation() {
    val cell = Cell(3, 4)
    assertThat(cell.row).isEqualTo(3)
    assertThat(cell.column).isEqualTo(4)
  }

  @Test
  fun cellHasSentinelNeighbors() {
    val cell = Cell(0, 0)
    assertThat(cell.north).isEqualTo(Cell.sentinelCell)
    assertThat(cell.south).isEqualTo(Cell.sentinelCell)
    assertThat(cell.east).isEqualTo(Cell.sentinelCell)
    assertThat(cell.west).isEqualTo(Cell.sentinelCell)
  }

  @Test
  fun cellNeighborsAreMutable() {
    val cell = Cell(0, 0)
    val neighbor = Cell(0, 1)

    cell.north = neighbor
    assertThat(cell.north).isSameInstanceAs(neighbor)
    cell.north = Cell.sentinelCell
    assertThat(cell.north).isEqualTo(Cell.sentinelCell)

    cell.south = neighbor
    assertThat(cell.south).isSameInstanceAs(neighbor)
    cell.south = Cell.sentinelCell
    assertThat(cell.south).isEqualTo(Cell.sentinelCell)

    cell.east = neighbor
    assertThat(cell.east).isSameInstanceAs(neighbor)
    cell.east = Cell.sentinelCell
    assertThat(cell.east).isEqualTo(Cell.sentinelCell)

    cell.west = neighbor
    assertThat(cell.west).isSameInstanceAs(neighbor)
    cell.west = Cell.sentinelCell
    assertThat(cell.west).isEqualTo(Cell.sentinelCell)
  }

  @Test
  fun cellsCanLink() {
    val cell = Cell(0, 0)
    val neighbor = Cell(0, 1)
    val otherNeighbor = Cell(0, 2)

    assertThat(cell.linksAsSet()).isEmpty()
    assertThat(neighbor.linksAsSet()).isEmpty()
    assertThat(cell.isLinkedTo(neighbor)).isFalse()
    assertThat(neighbor.isLinkedTo(cell)).isFalse()

    cell.link(neighbor)
    assertThat(cell.linksAsSet()).containsExactly(neighbor)
    assertThat(neighbor.linksAsSet()).containsExactly(cell)
    assertThat(cell.isLinkedTo(neighbor)).isTrue()
    assertThat(neighbor.isLinkedTo(cell)).isTrue()

    cell.link(otherNeighbor)
    assertThat(cell.linksAsSet()).hasSize(2)
    assertThat(cell.linksAsSet()).contains(otherNeighbor)
    assertThat(otherNeighbor.linksAsSet()).containsExactly(cell)
    assertThat(cell.isLinkedTo(otherNeighbor)).isTrue()
    assertThat(otherNeighbor.isLinkedTo(cell)).isTrue()
    assertThat(neighbor.isLinkedTo(otherNeighbor)).isFalse()
    assertThat(otherNeighbor.isLinkedTo(neighbor)).isFalse()
    assertThat(neighbor.linksAsSet()).containsExactly(cell)

    cell.unlink(neighbor)
    assertThat(cell.linksAsSet()).containsExactly(otherNeighbor)
    assertThat(otherNeighbor.linksAsSet()).containsExactly(cell)
    assertThat(neighbor.linksAsSet()).isEmpty()
    assertThat(cell.isLinkedTo(neighbor)).isFalse()
    assertThat(cell.isLinkedTo(otherNeighbor)).isTrue()
    assertThat(otherNeighbor.isLinkedTo(cell)).isTrue()
    assertThat(neighbor.isLinkedTo(otherNeighbor)).isFalse()
    assertThat(otherNeighbor.isLinkedTo(neighbor)).isFalse()

    cell.unlink(otherNeighbor)
    assertThat(cell.linksAsSet()).isEmpty()
    assertThat(otherNeighbor.linksAsSet()).isEmpty()
    assertThat(cell.isLinkedTo(otherNeighbor)).isFalse()
    assertThat(otherNeighbor.isLinkedTo(cell)).isFalse()
  }

  @Test
  fun directionalNeighborsListCorrectly() {
    val cell = Cell(0, 0)
    val neighbor = Cell(0, 1)

    assertThat(cell.neighbors()).isEmpty()
    cell.north = neighbor
    assertThat(cell.neighbors()).containsExactly(neighbor)
    cell.north = Cell.sentinelCell
    assertThat(cell.neighbors()).isEmpty()

    cell.south = neighbor
    assertThat(cell.neighbors()).containsExactly(neighbor)
    cell.south = Cell.sentinelCell
    assertThat(cell.neighbors()).isEmpty()

    cell.east = neighbor
    assertThat(cell.neighbors()).containsExactly(neighbor)
    cell.east = Cell.sentinelCell
    assertThat(cell.neighbors()).isEmpty()

    cell.west = neighbor
    assertThat(cell.neighbors()).containsExactly(neighbor)
    cell.west = Cell.sentinelCell
    assertThat(cell.neighbors()).isEmpty()
  }

  @Test
  fun multipleDirectionalNeighborsResultInExpectedList() {
    val cell = Cell(0, 0)
    val neighbor = Cell(0, 1)
    val otherNeighbor = Cell(0, 2)

    assertThat(cell.neighbors()).isEmpty()
    cell.north = neighbor
    assertThat(cell.neighbors()).containsExactly(neighbor)

    cell.south = otherNeighbor
    assertThat(cell.neighbors()).hasSize(2)
    assertThat(cell.neighbors()).contains(neighbor)
    assertThat(cell.neighbors()).contains(otherNeighbor)
    cell.south = Cell.sentinelCell
    assertThat(cell.neighbors()).containsExactly(neighbor)

    cell.east = otherNeighbor
    assertThat(cell.neighbors()).hasSize(2)
    assertThat(cell.neighbors()).contains(neighbor)
    assertThat(cell.neighbors()).contains(otherNeighbor)
    cell.east = Cell.sentinelCell
    assertThat(cell.neighbors()).containsExactly(neighbor)

    cell.west = otherNeighbor
    assertThat(cell.neighbors()).hasSize(2)
    assertThat(cell.neighbors()).contains(neighbor)
    assertThat(cell.neighbors()).contains(otherNeighbor)
    cell.west = Cell.sentinelCell
    assertThat(cell.neighbors()).containsExactly(neighbor)
  }
}