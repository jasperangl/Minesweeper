import java.util.ArrayList;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;
import java.util.Random;

//
interface IGame {
  int MINE_AMOUNT = 15;
  int GRID_WIDTH = 18;
  int GRID_HEIGHT = 10;
  int SIZE_CELLS = 50;
  int CELL_AMOUNT = GRID_WIDTH * GRID_HEIGHT;
  Color COLOR_HIDDEN_CELLS = Color.LIGHT_GRAY;
  Color COLOR_REVEALED_CELLS = Color.YELLOW;
  Color COLOR_FLAG = Color.RED;

  int SCREEN_W = (SIZE_CELLS * GRID_WIDTH);
  int SCREEN_H = (SIZE_CELLS * GRID_HEIGHT);
  int SIZE_FLAG = SIZE_CELLS - 2;
}

//game represents mine-sweeper game
class Game extends World implements IGame {
  ArrayList<ACell> cells;
  boolean mineClicked;

  // constructor for random game play
  Game() {
    this.cells = this.initGame(this.makeCells());
    addCellHorizontal();
    addCellVertical();
    addCellDiagonal();
    this.mineClicked = false;
  }

  // non-random constructor for use in testing
  Game(ArrayList<ACell> cells) {
    this.cells = cells;
  }

  // creates the board with all of the cells drawn on top of the background scene
  public WorldScene makeScene() {
    WorldScene scene = new WorldScene(SCREEN_W, SCREEN_H);
    ArrayList<ACell> array = this.cells;
    for (ACell c : array) {
      placeCell(scene, c);
    }
    return scene;
  }

  // draws cell on background at the x and y coordinate given by the array
  void placeCell(WorldScene ws, ACell cell) {
    ws.placeImageXY(cell.drawCell(), new Utils().xCoor(this.cells, cell),
        new Utils().yCoor(this.cells, cell));

  }


  // initalizes all the cells of the game containg all their neighbors
  void initBoard() {
    // this.addCellHorizontal();
    // this.addCellVertical();
    this.addCellDiagonal();

  }

  // takes a sorted list of cells and returns a randomized list with the same
  // elements
  public ArrayList<ACell> initGame(ArrayList<ACell> locells) {
    ArrayList<ACell> cells = locells;
    ArrayList<ACell> pHolder = new ArrayList<ACell>();
    ArrayList<ACell> result = new ArrayList<ACell>();
    for (ACell c : cells) {
      pHolder.add(c);
    }
    while (pHolder.size() > 0) {
      int rand = new Random().nextInt(pHolder.size());
      result.add(pHolder.remove(rand));
    }
    return result;
  }

  // creates a sorted list of cells
  public ArrayList<ACell> makeCells() {
    ArrayList<ACell> pHolder = new ArrayList<ACell>();
    int mtCells = GRID_WIDTH * GRID_HEIGHT - MINE_AMOUNT;
    for (int i = 0; i < MINE_AMOUNT; i++) {
      pHolder.add(new Mine());
    }
    for (int i = 0; i < mtCells; i++) {
      pHolder.add(new MtCell());
    }
    return pHolder;
  }

  // adds all the horizontal adjancent cells of the cell from the given list of
  // cells
  void addCellHorizontal() {
    for (int i = 0; i < this.cells.size() - 1; i++) {
      this.cells.get(i).addCell(this.cells.get(i + 1));
    }
    for (int k = GRID_WIDTH - 1; k < this.cells.size() - GRID_WIDTH; k = k + GRID_WIDTH) {
      this.cells.get(k).removeCell(this.cells.get(k + 1));
    }
  }

  // adds all the vertical neighbours to the cells of the given list of cells
  void addCellVertical() {
    for (int i = 0; i < this.cells.size() - GRID_WIDTH; i++) {
      this.cells.get(i).addCell(this.cells.get(i + GRID_WIDTH));
    }
  }

  // adds all the diagonal neighbours to the cells of the given list of cells
  void addCellDiagonal() {

    // adds the right diagonal cells

    for (int i = 0; i < this.cells.size() - GRID_WIDTH - 1; i++) {
      this.cells.get(i).addCell(this.cells.get(i + GRID_WIDTH + 1));
    }
    for (int k = GRID_WIDTH - 1; k < this.cells.size() - (GRID_WIDTH) - 1; k = k + GRID_WIDTH) {
      this.cells.get(k).removeCell(this.cells.get(k + GRID_WIDTH + 1));
    }

    // adds the left diagonal cells
    for (int l = 0; l < this.cells.size() - GRID_WIDTH; l++) {
      this.cells.get(l).addCell(this.cells.get(l + GRID_WIDTH - 1));
    }
    for (int n = 0; n < this.cells.size() - GRID_WIDTH - 1; n = n + GRID_WIDTH) {
      this.cells.get(n).removeCell(this.cells.get(n + GRID_WIDTH - 1));
    }
  }

  public void onMouseClicked(Posn posn, String s) {
    int x = posn.x;
    int y = posn.y;
    int cellWidth = SCREEN_W / GRID_WIDTH;
    int cellHeight = SCREEN_H / GRID_HEIGHT;
    x = x / cellWidth;
    y = y / cellHeight;

    if (s.equals("LeftButton") && (x < GRID_WIDTH) && (y < GRID_HEIGHT)) {
      this.cells.get(x + (GRID_WIDTH * y)).isClicked();
      if (this.cells.get(x + (GRID_WIDTH * y)).isMine()) {
        this.mineClicked = true;
      }

    }

    if (s.equals("RightButton") && (x < GRID_WIDTH) && (y < GRID_HEIGHT)) {
      this.cells.get(x + (GRID_WIDTH * y)).hasFlag();
    }

  }

  public void onTick() {
    for (ACell c : this.cells) {
      if (c.isRevealed && new Utils().countMines(c) == 0 && !(c.isMine())) {
        for (ACell c2 : c.neighbors) {
          if (!c2.isMine()) {
            c2.isClicked();
          }
        }
      }
    }
  }

  public boolean gamewon() {
    boolean temp = true;
    for (ACell c : this.cells) {
      if (!c.isMine() && !c.isRevealed) {
        temp = false;
      }
    }
    return temp;
  }

  public WorldEnd worldEnds() {
    if (this.mineClicked) {
      return new WorldEnd(true, this.makeFinalScene());
    }
    else if (gamewon()) {
      return new WorldEnd(true, this.makeFinalSceneB());
    }
    else {
      return new WorldEnd(false, this.makeScene());
    }
  }

  public WorldScene makeFinalScene() {
    WorldScene scene = this.makeScene();
    scene.placeImageXY(new TextImage("Game Over ", 60, Color.RED), SCREEN_W / 2, SCREEN_H / 2);
    return scene;

  }

  public WorldScene makeFinalSceneB() {
    WorldScene scene = this.makeScene();
    scene.placeImageXY(new TextImage("You Won!", 80, Color.GREEN), SCREEN_W / 2, SCREEN_H / 2);
    return scene;

  }
}

abstract class ACell implements IGame {
  ArrayList<ACell> neighbors;
  boolean isRevealed;
  boolean flagged;

  public ACell(ArrayList<ACell> neighbors) {
    this.neighbors = neighbors;
    this.isRevealed = false;
    this.flagged = false;
  }

  // determines if this is the same MtCell as that
  boolean sameCell(ACell that) {
    return this.neighbors.equals(that.neighbors);
  }

  void isClicked() {
    this.isRevealed = true;
  }

  void hasFlag() {
    this.flagged = !this.flagged;
  }

  // returns true if the ACell is a mine
  boolean isMine() {
    return false;
  }

  // creates a square representing the cell based on the constants supplied
  WorldImage drawCell() {
    if (this.isRevealed) {
      return new FrameImage(
          new RectangleImage(SIZE_CELLS, SIZE_CELLS, OutlineMode.SOLID, COLOR_REVEALED_CELLS),
          Color.BLUE);
    }
    if (this.flagged) {
      return new OverlayImage(new CircleImage(SIZE_CELLS / 4, OutlineMode.SOLID, Color.BLUE),
          new FrameImage(
              new RectangleImage(SIZE_CELLS, SIZE_CELLS, OutlineMode.SOLID, COLOR_HIDDEN_CELLS),
              Color.BLUE));
    }
    return new FrameImage(
        new RectangleImage(SIZE_CELLS, SIZE_CELLS, OutlineMode.SOLID, COLOR_HIDDEN_CELLS),
        Color.BLUE);
  }

  // draws a triangle representing a flag with the given constants
  WorldImage drawFlag() {
    return new CircleImage(SIZE_CELLS / 4, OutlineMode.SOLID, Color.BLUE);
  }

  // adds a neighbour to the cell
  public void addCell(ACell cell) {
    this.neighbors.add(cell);
    cell.neighbors.add(this);
  }

  // removes the given and this cell from each of their neighbors
  public void removeCell(ACell cell) {
    this.neighbors.remove(cell);
    cell.neighbors.remove(this);
  }

  // is this ACell revealed or not
  boolean isRevealed() {
    return this.isRevealed;
  }

}

//to represent a game cell with a mine in it
class Mine extends ACell {

  Mine() {
    this(new ArrayList<ACell>());
  }

  Mine(ArrayList<ACell> neighbors) {
    super(neighbors);
  }

  // is this a mine?
  public boolean isMine() {
    return true;
  }

  // creates a circle representing the cell based on the constants supplied
  WorldImage drawMine() {
    return new CircleImage(SIZE_CELLS / 4, OutlineMode.SOLID, Color.RED);
  }

  // creates a square with the mine shown on top, purely for testing
  // mines will NOT be shown in final game
  WorldImage drawCell() {
    if (this.isRevealed) {
      return new OverlayImage(drawMine(),
          new FrameImage(
              new RectangleImage(SIZE_CELLS, SIZE_CELLS, OutlineMode.SOLID, COLOR_HIDDEN_CELLS),
              Color.BLUE));
    }
    if (this.flagged) {
      return new OverlayImage(new CircleImage(SIZE_CELLS / 4, OutlineMode.SOLID, Color.BLUE),
          new FrameImage(
              new RectangleImage(SIZE_CELLS, SIZE_CELLS, OutlineMode.SOLID, COLOR_HIDDEN_CELLS),
              Color.BLUE));
    }
    return new FrameImage(
        new RectangleImage(SIZE_CELLS, SIZE_CELLS, OutlineMode.SOLID, COLOR_HIDDEN_CELLS),
        Color.BLUE);

  }
}

//to represent an empty game cell, with no mine
class MtCell extends ACell {

  MtCell() {
    this(new ArrayList<ACell>());
  }

  public MtCell(ArrayList<ACell> neighbors) {
    super(neighbors);
  }

  // determines if this MtCell is a mine
  public boolean isMine() {
    return false;
  }

  // creates a square representing the cell based on the constants supplied
  WorldImage drawNumber() {
    return new TextImage(Integer.toString(new Utils().countMines(this)), SIZE_CELLS / 2,
        Color.BLACK);

  }

  // creates a square with a number representing the surrounding mines
  WorldImage drawCell() {
    if (this.isRevealed) {
      if (new Utils().countMines(this) > 0) {
        return new OverlayImage(drawNumber(), drawMtCell());
      }
      return drawMtCell();
    }
    if (this.flagged) {
      return new OverlayImage(new CircleImage(SIZE_CELLS / 4, OutlineMode.SOLID, Color.BLUE),
          new FrameImage(
              new RectangleImage(SIZE_CELLS, SIZE_CELLS, OutlineMode.SOLID, COLOR_HIDDEN_CELLS),
              Color.BLUE));
    }
    return new FrameImage(
        new RectangleImage(SIZE_CELLS, SIZE_CELLS, OutlineMode.SOLID, COLOR_HIDDEN_CELLS),
        Color.BLUE);
  }

  // creates a framed square representing the cell based on the constants supplied
  WorldImage drawMtCell() {
    return new FrameImage(
        new RectangleImage(SIZE_CELLS, SIZE_CELLS, OutlineMode.SOLID, COLOR_REVEALED_CELLS));
  }
}
