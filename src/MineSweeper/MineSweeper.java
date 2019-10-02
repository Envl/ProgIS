package MineSweeper;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.opencv.core.Core;

// todo
// MVCfy  (in progress

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

public class MineSweeper extends Application {
  // const values
  final int COLS = 10;
  final int ROWS = 10;
  final int GRID_SIZE = 40;
  final int HEAD_HEIGHT=50;
  final String STYLE_REVEALED="-fx-background-color: #eeeeee;";

  // variables
  Cell[][] gameTable = new Cell[ROWS][COLS]; // 0-8 mines around
  ArrayHelper tableHelper = new ArrayHelper(ROWS, COLS);
  CVAgent cvAgent=new CVAgent();
  GameModel model=new GameModel(ROWS, COLS);
  // GUI objc
  // 改用 stack pane  用来支持拖拽事件
  //   使用道具 就能拖拽交换两个 雷 的位置??
  // 图像识别?  像素化, 然后生成雷 map??
  GridPane gameBoard = new GridPane();
  Button grids[] = new Button[ROWS * COLS];

  static{
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
  }
  public static void main(String[] args) {
    launch(args);
  }

  int mineCounter(int col, int row) {
    int amount = 0;
    for (int i = -1; i < 2; i++) {
      for (int j = -1; j < 2; j++) {
        if (tableHelper.testInside(row + i, col + j) && gameTable[row + i][col + j]._isMine) {
          amount++;
        }
      }
    }
    return amount;
  }

  // DFS auto reveal surrounding grids
  void revealAround(int row,int col){
    // out bound check
    if(!tableHelper.testInside(row,col) ){
      return;
    }
    // reveal self
    gameTable[row][col].set_state(1);// set revealed in MODEL
    grids[COLS *row+col].setText(String.valueOf(gameTable[row][col]._num)); // show text on btn
    grids[COLS *row+col].setStyle(STYLE_REVEALED);// update style
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
        revealAround(row+i,col+j);
      }
    }
    // after check around
    gameTable[row][col]._revealedAround=true;
    gameTable[row][col]._underChecking=false;
    return;
  }

  @Override
  public void init() throws Exception {
    super.init();

    // GUI Components
    Button btnStart=new Button("(Re)Start Game");
    gameBoard.add(btnStart,0,0,3,1);
    btnStart.setOnMouseClicked(evt->{
      Button tmp=(Button)evt.getSource();
      initGame();
    });
    ImageView cameraView=new ImageView();
    gameBoard.add(cameraView,4,0);
    // 需要包装一个 controller 类, 把下面代码放进去
    for (int i = 0; i < ROWS; i++) {
      System.out.println(i);
      for (int j = 0; j < COLS; j++) {
        gameTable[i][j] = new Cell();
      }
    }
    // 要包装进 Cell 类里面, 然后这里只从gameTable获取每个Button添加到 gameBoard
    for (int row = 0; row < ROWS; row++) {
      for (int col = 0; col < COLS; col++) {
        grids[COLS * row + col] = new Button("\uD83D\uDE04");
//        grids[cols * row + col].setText("O");
        grids[COLS * row + col].setPrefWidth(GRID_SIZE);
        grids[COLS * row + col].setPrefHeight(GRID_SIZE);
        grids[COLS * row + col].setId(row+","+col);
        grids[COLS * row + col].setOnMouseClicked(evt -> {
          System.out.println(evt.getSource());
          Button btn=(Button)evt.getSource();
          String[] tmpL =btn.getId().split(",");
          int tmpR= Integer.parseInt( tmpL[0]);
          int tmpC= Integer.parseInt( tmpL[1]);
          switch (evt.getButton()) {
            case PRIMARY:
              gameTable[tmpR][tmpC].set_state(1); // open
              if(gameTable[tmpR][tmpC]._isMine){
                // controller. endGame()
                btn.setText("\uD83C\uDF49");
                System.out.println("BOOOOOM");
                btn.setStyle("-fx-background-color: #ee6666;");
              }else{
                revealAround(tmpR,tmpC);
//                btn.setText(String.valueOf(gameTable[tmpR][tmpC]._num));
//                btn.setStyle(STYLE_REVEALED);
              }
              break;

            case SECONDARY:
              btn.setText("⚑");
              gameTable[tmpR][tmpC].set_state(-1);// flaged
              btn.setStyle("-fx-background-color: #663399;");
              break;
          }
        });
        // row 0 is reserved for other Controls
        gameBoard.add(grids[COLS * row + col], col, row+1);
      }
    }



    initGame();
    cvAgent.setCameraView(cameraView);

  }

void initGame(){
  // put mines and init state
  for (int i = 0; i < ROWS; i++) {
    System.out.println(i);
    for (int j = 0; j < COLS; j++) {
      gameTable[i][j].set_isMine(Math.random() < 0.1);
      gameTable[i][j].set_state(0);
      gameTable[i][j]._revealedAround=false;
      gameTable[i][j]._underChecking=false;
      // init styles
      grids[COLS * i + j].setStyle(
              "-fx-background-color: #D7E0EC;" +
              "-fx-border: #ff0000 2px;"+
              "-fx-border-color:#777777;" +
              "-fx-border-width: 1;"
      );
      // reset text
      grids[COLS * i + j].setText("\uD83D\uDE04");
    }
  }
  // count mines
  System.out.println("Game Table with count");
  for (int i = 0; i < ROWS; i++) {
    for (int j = 0; j < COLS; j++) {
      gameTable[i][j].set_num(mineCounter(j, i));
      System.out.print(gameTable[i][j]._num);
    }
    System.out.println();
  }
  System.out.println();
  System.out.println("Mine Table");
  // print mine table
  for (int i = 0; i < ROWS; i++) {
    for (int j = 0; j < COLS; j++) {
      System.out.print(gameTable[i][j]._isMine ? 1 : 0);
    }
    System.out.println();
  }
  System.out.println();
  System.out.println();
}


  @Override
  public void start(Stage primaryStage) throws Exception {

    cvAgent.startCamera();
    System.out.println(grids[0]);

    primaryStage.setTitle("Mine Sweeper");
    primaryStage.setScene(new Scene(gameBoard, COLS * GRID_SIZE, ROWS * GRID_SIZE +HEAD_HEIGHT));
    primaryStage.setWidth(COLS * GRID_SIZE);
    primaryStage.setHeight(ROWS * GRID_SIZE +HEAD_HEIGHT);
    primaryStage.show();

    // Cleaning job before destroy
    primaryStage.setOnCloseRequest((WindowEvent evt) -> {
      System.out.println("Closing App");
      System.out.println(evt);
      cvAgent.setClosed();
    });
  }
}
