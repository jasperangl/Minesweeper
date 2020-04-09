import java.util.ArrayList;

class Utils implements IGame {

  // adds a neighbor to the cell
  public void addCell(ACell cell1, ACell cell2) {
    cell1.neighbors.add(cell2);
    cell2.neighbors.add(cell1);
  }

  // returns the number of mines in the given ArrayList<ACell>
  public int countMinesHelper(ArrayList<ACell> neighbors) {
    int mines = 0;
    for (ACell c : neighbors) {
      if (c.isMine()) {
        mines = mines + 1;
      }
    }
    return mines;
  }

  // returns the number of mines neighboring the given ACell
  int countMines(ACell cell) {
    return new Utils().countMinesHelper(cell.neighbors);
  }

  // given an Array of ACells returns the X coordinate position of this cell
  int xCoor(ArrayList<ACell> locells, ACell cell) {
    int index = findCell(locells, cell);
    if (index < GRID_WIDTH) {
      return (index * SIZE_CELLS) + (SIZE_CELLS / 2);
    }
    else {
      return ((index % GRID_WIDTH) * SIZE_CELLS) + (SIZE_CELLS / 2);
    }
  }

  // given an Array of ACells returns the Y coordinate position of this cell
  int yCoor(ArrayList<ACell> locells, ACell cell) {
    int index = findCell(locells, cell);
    return ((index / GRID_WIDTH) * SIZE_CELLS) + (SIZE_CELLS / 2);
  }

  // Returns the index of the first cell that is equal to that cell
  // or -1 if no such item was found
  int findCell(ArrayList<ACell> arr, ACell cell) {
    return findHelp(arr, cell, 0);
  }

  // given an array finds the given cell
  int findHelp(ArrayList<ACell> arr, ACell cell, int index) {
    if (index >= arr.size()) {
      return -1;
    }
    else if (cell.sameCell(arr.get(index))) {
      return index;
    }
    else {
      return findHelp(arr, cell, index + 1);
    }
  }
}
