package MineSweeper;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
//import org.opencv.core.Core;



public class MineSweeper extends Application {
  // const values
  final int COLS = 10;
  final int ROWS = 10;
  final int GRID_SIZE = 40;
  final int HEAD_HEIGHT = 50;
  final String STYLE_REVEALED = "-fx-background-color: #eeeeee;";

  // variables
  //  CVAgent cvAgent=new CVAgent();
  GameModel model = new GameModel(ROWS, COLS);
  // GUI objc
  // 改用 stack pane  用来支持拖拽事件
  //   使用道具 就能拖拽交换两个 雷 的位置??
  // 图像识别?  像素化, 然后生成雷 map??
  GridPane root = new GridPane();
  Button grids[] = new Button[ROWS * COLS];

  //  static{
//    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//  }
  public static void main(String[] args) {
    launch(args);
  }



  @Override
  public void init() throws Exception {
    super.init();

    // GUI Components
    Button btnStart = new Button("(Re)Start Game");
    root.add(btnStart, 0, 0, 3, 1);
    btnStart.setOnMouseClicked(evt -> {
      Button tmp = (Button) evt.getSource();
      model.initTable();
      updateGridStyles();
    });
    ImageView cameraView = new ImageView();
    root.add(cameraView, 4, 0);
    // 要包装进 Cell 类里面, 然后这里只从gameTable获取每个Button添加到 gameBoard
    for (int row = 0; row < ROWS; row++) {
      for (int col = 0; col < COLS; col++) {
        grids[COLS * row + col] = new Button("\uD83D\uDE04");
        grids[COLS * row + col].setPrefWidth(GRID_SIZE);
        grids[COLS * row + col].setPrefHeight(GRID_SIZE);
        grids[COLS * row + col].setId(row + "," + col);
        grids[COLS * row + col].setOnMouseClicked(evt -> {
          System.out.println(evt.getSource());
          Button btn = (Button) evt.getSource();
          String[] tmpL = btn.getId().split(",");
          int tmpR = Integer.parseInt(tmpL[0]);
          int tmpC = Integer.parseInt(tmpL[1]);
          switch (evt.getButton()) {
            case PRIMARY:
              model.revealCell(tmpR, tmpC);
              break;
            case SECONDARY:
              model.flagCell(tmpR, tmpC);
              break;
          }
          updateGridStyles();
        });
        // row 0 is reserved for other Controls
        root.add(grids[COLS * row + col], col, row + 1);
      }
    }
  }


  void updateGridStyles() {
    for (int row = 0; row < ROWS; row++) {
      for (int col = 0; col < COLS; col++) {
        switch (model.gameTable[row][col]._state) {
          case 0: //unrevealed
            // init styles
            grids[COLS * row + col].setStyle(null);
//                    "-fx-background-color: #D7E0EC;" +
//                            "-fx-border: #ff0000 2px;" +
//                            "-fx-border-color:#777777;" +
//                            "-fx-border-width: 1;"
//            );
            // reset text
            grids[COLS * row + col].setText("\uD83D\uDE04");
            break;
          case 1://revealed
            if (model.gameTable[row][col]._isMine) {
              System.out.println("BOOOOOM");
              grids[COLS * row + col].setText("\uD83C\uDF49"); // show text on btn
              grids[COLS * row + col].setStyle("-fx-background-color: #ee6666;");// update style
            } else {
              grids[COLS * row + col].setText(String.valueOf(model.gameTable[row][col]._num)); // show text on btn
              grids[COLS * row + col].setStyle(STYLE_REVEALED);// update style
            }
            break;
          case -1: //flagged
            grids[COLS * row + col].setText("⚑"); // show text on btn
            grids[COLS * row + col].setStyle("-fx-background-color: #663399;");// update style
            break;
        }
      }
    }
  }

  @Override
  public void start(Stage primaryStage) throws Exception {

//    cvAgent.startCamera();
    System.out.println(grids[0]);

    primaryStage.setTitle("Mine Sweeper");
    primaryStage.setScene(new Scene(root, COLS * GRID_SIZE, ROWS * GRID_SIZE + HEAD_HEIGHT));
    primaryStage.setWidth(COLS * GRID_SIZE);
    primaryStage.setHeight(ROWS * GRID_SIZE + HEAD_HEIGHT);
    primaryStage.show();

    // Cleaning job before destroy
    primaryStage.setOnCloseRequest((WindowEvent evt) -> {
      System.out.println("Closing App");
      System.out.println(evt);
//      cvAgent.setClosed();
    });
  }
}
