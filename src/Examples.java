
import java.util.ArrayList;
import java.util.Arrays;

import tester.Tester;

class ExamplesGame implements IGame {

  ACell mtCell1;
  ACell mtCell2;
  ACell mtCell3;
  ACell mine1;
  ACell mine2;
  ACell mine3;

  //
  // ACells for Horizontal neighbors
  ACell mine1h;
  ACell mine2h;
  ACell mine3h;
  ACell mtCell1h;
  ACell mtCell2h;
  ACell mtCell3h;

  // ACells for vertical neighbors
  ACell mine1v;
  ACell mine2v;
  ACell mine3v;
  ACell mtCell1v;
  ACell mtCell2v;
  ACell mtCell3v;

  // ACells for diagonal neighbors
  ACell mine1d;
  ACell mine2d;
  ACell mine3d;
  ACell mtCell1d;
  ACell mtCell2d;
  ACell mtCell3d;

  // ACells for all neighbors
  ACell mine1a;
  ACell mine2a;
  ACell mine3a;
  ACell mtCell1a;
  ACell mtCell2a;
  ACell mtCell3a;

  ArrayList<ACell> locell;
  ArrayList<ACell> locellh;
  ArrayList<ACell> locellv;
  ArrayList<ACell> locelld;
  ArrayList<ACell> locella;

  ACell mb;
  ACell me;
  ACell mg;
  ACell a;
  ACell c;
  ACell d;
  ACell f;
  ACell h;
  ACell i;
  ACell rev;
  ArrayList<ACell> arr4;
  ArrayList<ACell> arr5;
  ArrayList<ACell> arr6;
  Game testGame;

  void initializeData() {
    mtCell1 = new MtCell();
    mtCell2 = new MtCell();
    mtCell3 = new MtCell();
    mine1 = new Mine();
    mine2 = new Mine();
    mine3 = new Mine();

    // ACells with horizontal adjacent cells
    mine1h = new Mine(new ArrayList<ACell>(Arrays.asList(this.mine2)));
    mine2h = new Mine(new ArrayList<ACell>(Arrays.asList(this.mine3, this.mine1)));
    mine3h = new Mine(new ArrayList<ACell>(Arrays.asList(this.mine2)));
    mtCell1h = new MtCell(new ArrayList<ACell>(Arrays.asList(this.mtCell2)));
    mtCell2h = new MtCell(new ArrayList<ACell>(Arrays.asList(this.mtCell3, this.mtCell1)));
    mtCell3h = new MtCell(new ArrayList<ACell>(Arrays.asList(this.mtCell2)));

    // ACells with vertical adjacent cells
    mine1v = new Mine(new ArrayList<ACell>(Arrays.asList(this.mtCell1)));
    mine2v = new Mine(new ArrayList<ACell>(Arrays.asList(this.mtCell2)));
    mine3v = new Mine(new ArrayList<ACell>(Arrays.asList(this.mtCell3)));
    mtCell1v = new MtCell(new ArrayList<ACell>(Arrays.asList(this.mine1)));
    mtCell2v = new MtCell(new ArrayList<ACell>(Arrays.asList(this.mine2)));
    mtCell3v = new MtCell(new ArrayList<ACell>(Arrays.asList(this.mine3)));

    // ACells with horizontal adjacent cells
    mine1d = new Mine(new ArrayList<ACell>(Arrays.asList(this.mtCell2)));
    mine2d = new Mine(new ArrayList<ACell>(Arrays.asList(this.mtCell3, this.mtCell1)));
    mine3d = new Mine(new ArrayList<ACell>(Arrays.asList(this.mtCell2)));
    mtCell1d = new MtCell(new ArrayList<ACell>(Arrays.asList(this.mine2)));
    mtCell2d = new MtCell(new ArrayList<ACell>(Arrays.asList(this.mine3, this.mine1)));
    mtCell3d = new MtCell(new ArrayList<ACell>(Arrays.asList(this.mine2)));

    // ACells with all adjacent cells
    mine1a = new Mine(new ArrayList<ACell>(Arrays.asList(this.mine2, this.mtCell1, this.mtCell2)));
    mine2a = new Mine(new ArrayList<ACell>(
        Arrays.asList(this.mine3, this.mine1, this.mtCell2, this.mtCell3, this.mtCell1)));
    mine3a = new Mine(new ArrayList<ACell>(Arrays.asList(this.mine2, this.mtCell3, this.mtCell2)));
    mtCell1a = new MtCell(
        new ArrayList<ACell>(Arrays.asList(this.mtCell2, this.mine1, this.mine2)));
    mtCell2a = new MtCell(new ArrayList<ACell>(
        Arrays.asList(this.mtCell3, this.mtCell1, this.mine2, this.mine3, this.mine1)));
    mtCell3a = new MtCell(
        new ArrayList<ACell>(Arrays.asList(this.mtCell2, this.mine3, this.mine2)));

    // ACells with horizontal adjacent cells
    locell = new ArrayList<ACell>(Arrays.asList(this.mine1, this.mine2, this.mine3, this.mtCell1,
        this.mtCell2, this.mtCell3));
    locellh = new ArrayList<ACell>(Arrays.asList(this.mine1h, this.mine2h, this.mine3h,
        this.mtCell1h, this.mtCell2h, this.mtCell3h));
    locellv = new ArrayList<ACell>(Arrays.asList(this.mine1v, this.mine2v, this.mine3v,
        this.mtCell1v, this.mtCell2v, this.mtCell3v));
    locelld = new ArrayList<ACell>(Arrays.asList(this.mine1d, this.mine2d, this.mine3d,
        this.mtCell1d, this.mtCell2d, this.mtCell3d));
    locella = new ArrayList<ACell>(Arrays.asList(this.mine1a, this.mine2a, this.mine3a,
        this.mtCell1a, this.mtCell2a, this.mtCell3a));

    mb = new Mine();
    me = new Mine();
    mg = new Mine();
    a = new MtCell();
    c = new MtCell();
    d = new MtCell();
    f = new MtCell();
    h = new MtCell();
    i = new MtCell();

    new Utils().addCell(a, mb);
    new Utils().addCell(a, me);
    new Utils().addCell(a, d);
    new Utils().addCell(mb, c);
    new Utils().addCell(mb, me);
    new Utils().addCell(mb, f);
    new Utils().addCell(mb, d);
    new Utils().addCell(c, me);
    new Utils().addCell(c, f);
    new Utils().addCell(d, me);
    new Utils().addCell(d, mg);
    new Utils().addCell(me, f);
    new Utils().addCell(me, mg);
    new Utils().addCell(me, h);
    new Utils().addCell(me, i);
    new Utils().addCell(f, i);
    new Utils().addCell(f, h);
    new Utils().addCell(mg, h);
    new Utils().addCell(h, i);

    arr4 = new ArrayList<ACell>(Arrays.asList(a, mb, c, d, me, f, mg, h, i));
    arr5 = new ArrayList<ACell>(Arrays.asList(a, mb, c, d, me, f));
    arr6 = new ArrayList<ACell>(Arrays.asList(a, c));
    testGame = new Game(arr4);

  }

  void testCellHorizontal(Tester t) {
    initializeData();
    Game game1 = new Game(this.locell);
    Game game2 = new Game(this.locellh);
    t.checkExpect(game1, game1);
    game1.addCellHorizontal();
    t.checkExpect(game1, game2);
  }

  void testCellVertical(Tester t) {
    initializeData();
    Game game1 = new Game(this.locell);
    Game game2 = new Game(this.locellv);
    t.checkExpect(game1, game1);
    game1.addCellVertical();
    t.checkExpect(game1, game2);
  }

  void testCellDiagonal(Tester t) {
    initializeData();
    Game game1 = new Game(this.locell);
    Game game2 = new Game(this.locelld);
    t.checkExpect(game1, game1);
    game1.addCellDiagonal();
    t.checkExpect(game1, game2);
  }

  void testInitBoard(Tester t) {
    initializeData();
    Game game1 = new Game(this.locell);
    Game game2 = new Game(this.locella);
    t.checkExpect(game1, game1);
    game1.addCellHorizontal();
    game1.addCellVertical();
    game1.addCellDiagonal();
    t.checkExpect(game1, game2);
  }

  void testEquality(Tester t) {
    initializeData();
    t.checkExpect(this.me.sameCell(this.me), true);
    t.checkExpect(this.me.sameCell(this.mg), false);
    t.checkExpect(this.mb.sameCell(this.c), false);
  }

  void testCountMines(Tester t) {
    initializeData();
    t.checkExpect(new Utils().countMines(a), 2);
    t.checkExpect(new Utils().countMines(c), 2);
    t.checkExpect(new Utils().countMines(d), 3);
    t.checkExpect(new Utils().countMines(f), 2);

  }

  void testCountMinesHelper(Tester t) {
    initializeData();
    t.checkExpect(new Utils().countMinesHelper(arr4), 3);
    t.checkExpect(new Utils().countMinesHelper(arr5), 2);
    t.checkExpect(new Utils().countMinesHelper(arr6), 0);
  }

  void testFindCell(Tester t) {
    initializeData();
    t.checkExpect(new Utils().findCell(arr4, mb), 1);
    t.checkExpect(new Utils().findCell(arr4, f), 5);
  }

  void testFindCellHelp(Tester t) {
    initializeData();
    t.checkExpect(new Utils().findHelp(arr4, a, 0), 0);
    t.checkExpect(new Utils().findHelp(arr5, mg, 0), -1);
    t.checkExpect(new Utils().findHelp(arr6, c, 1), 1);
  }

  void testX(Tester t) {
    initializeData();
    t.checkExpect(new Utils().xCoor(arr4, a), 50);
    t.checkExpect(new Utils().xCoor(arr4, mb), 150);
    t.checkExpect(new Utils().xCoor(arr4, c), 250);
    t.checkExpect(new Utils().xCoor(arr4, d), 50);
    t.checkExpect(new Utils().xCoor(arr4, me), 150);
    t.checkExpect(new Utils().xCoor(arr4, f), 250);
    t.checkExpect(new Utils().xCoor(arr4, mg), 50);
    t.checkExpect(new Utils().xCoor(arr4, h), 150);
    t.checkExpect(new Utils().xCoor(arr4, i), 250);
  }

  void testY(Tester t) {
    initializeData();
    t.checkExpect(new Utils().yCoor(arr4, a), 50);
    t.checkExpect(new Utils().yCoor(arr4, mb), 50);
    t.checkExpect(new Utils().yCoor(arr4, c), 50);
    t.checkExpect(new Utils().yCoor(arr4, d), 150);
    t.checkExpect(new Utils().yCoor(arr4, me), 150);
    t.checkExpect(new Utils().yCoor(arr4, f), 150);
    t.checkExpect(new Utils().yCoor(arr4, mg), 250);
    t.checkExpect(new Utils().yCoor(arr4, h), 250);
    t.checkExpect(new Utils().yCoor(arr4, i), 250);
  }

  void testIsMine(Tester t) {
    initializeData();
    t.checkExpect(a.isMine(), false);
    t.checkExpect(mg.isMine(), true);
  }

  void testGame(Tester t) {
    initializeData();
    Game g = new Game();
    g.bigBang(SCREEN_W, SCREEN_H, 0.01);

  }

}
