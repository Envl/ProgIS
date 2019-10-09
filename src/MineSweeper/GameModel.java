package MineSweeper;

public class GameModel {
  class Cell {
    int _num = 0;
    int _state = 0;  // 0: unrevealed  1:revealed   -1:flaged
    boolean _isMine = false;
    boolean _revealedAround=false;
    boolean _underChecking=false; // to avoid StackOverFlow caused by inter invoking of adjacent grids


    public Cell(int n) {
      _num = n;
    }

    public Cell() {
      _num = 0;
    }

    public boolean is_isMine() {
      return _isMine;
    }

    public void set_isMine(boolean _isMine) {
      this._isMine = _isMine;
    }

    public int get_num() {
      return _num;
    }

    public void set_num(int _num) {
      this._num = _num;
    }

    public int get_state() {
      return _state;
    }

    public void set_state(int _state) {
      this._state = _state;
    }
  }

  private int _cols=10;
  private int _rows=10;
  Cell[][] gameTable = new Cell[_rows][_cols];
  ArrayHelper tableHelper = new ArrayHelper(_rows, _cols);

  public int get_cols() {
    return _cols;
  }

  public int get_rows() {
    return _rows;
  }

  public GameModel(int rowCount, int colCount){
    _cols=colCount;
    _rows=rowCount;
    // create game table
    for (int i = 0; i < _rows; i++) {
      System.out.println(i);
      for (int j = 0; j < _cols; j++) {
        gameTable[i][j] = new Cell();
      }
    }
    initTable();
  }


  int countMineAround(int row, int col,boolean[][] mineTable) {
    int amount = 0;
    for (int i = -1; i < 2; i++) {
      for (int j = -1; j < 2; j++) {
        if (tableHelper.testInside(row + i, col + j) && mineTable[row + i][col + j]) {
          amount++;
        }
      }
    }
    return amount;
  }

  // DFS auto reveal surrounding grids
  void revealCell(int row, int col){
    // out bound check
    if(!tableHelper.testInside(row,col) ){
      return;
    }
    // reveal self
    gameTable[row][col].set_state(1);// set revealed in MODEL
    // reveal around if self is 0
    if( gameTable[row][col]._num!=0
            || gameTable[row][col]._revealedAround
            || gameTable[row][col]._underChecking){
      return;
    }
    gameTable[row][col]._underChecking=true;
    // check around
    for (int i = -1; i < 2; i++) {
      for (int j = -1; j < 2; j++) {
        revealCell(row+i,col+j);
      }
    }
    // after check around
    gameTable[row][col]._revealedAround=true;
    gameTable[row][col]._underChecking=false;
  }

  private boolean[][] genMineTable(){
    boolean[][] table=new boolean[_rows][_cols];
    for (int i = 0; i < _rows; i++) {
      for (int j = 0; j < _cols; j++) {
        table[i][j]= Math.random() < 0.1;
      }
    }
    return table;
  }

  void flagCell(int row,int col){
    gameTable[row][col]._state=-1;
  }

  void initTable(){
    boolean[][] mineTable=genMineTable();
    for (int i = 0; i < _rows; i++) {
      for (int j = 0; j < _cols; j++) {
        gameTable[i][j].set_isMine(mineTable[i][j]);
        gameTable[i][j].set_num(countMineAround(i, j,mineTable));
        gameTable[i][j].set_state(0);
        gameTable[i][j]._revealedAround=false;
        gameTable[i][j]._underChecking=false;
      }
    }

    // print mine table
    System.out.println();
    System.out.println("Mine Table");
    for (int i = 0; i < _rows; i++) {
      for (int j = 0; j < _cols; j++) {
        System.out.print(gameTable[i][j]._isMine ? 1 : 0);
      }
      System.out.println();
    }
    System.out.println();
    System.out.println();
  }
}
