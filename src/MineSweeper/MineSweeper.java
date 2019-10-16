package MineSweeper;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.opencv.core.Core;

import static MineSweeper.GLOBAL.*;


public class MineSweeper extends Application {
  // const values

  // variables
  private CVAgent cvAgent = new CVAgent();
  private GameModel model = new GameModel(ROWS, COLS, 0.1f);
  // GUI objc
  // 改用 stack pane  用来支持拖拽事件
  //   使用道具 就能拖拽交换两个 雷 的位置??
  // 图像识别?  像素化, 然后生成雷 map??
  private Pane _root = new Pane();
  private GameBoard _board = new GameBoard(ROWS, COLS);

  static {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
  }

  public static void main(String[] args) {
    launch(args);
  }


  @Override
  public void init() throws Exception {
    super.init();
    _board.bindModel(model);
    Button btnStart = new Button("(Re)Start Game");
    btnStart.setOnMouseClicked(evt -> {
      Button tmp = (Button) evt.getSource();
//      model.initTable(model.genMineTable(ROWS,COLS,0.15f));
      model.initTable(cvAgent.genTable());

      _board.updateUI();
      _board.revealAll();
      _board.updateGridStyles();

    });
    ImageView cameraView = new ImageView();
    cvAgent.setCameraView(cameraView);
    _board.setPos(0, HEAD_HEIGHT);
    _root.getChildren().addAll(_board.rootInstance(), cameraView, btnStart);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {

    cvAgent.startCamera();

    Scene scene = new Scene(_root, COLS * GRID_SIZE, ROWS * GRID_SIZE + HEAD_HEIGHT);
    primaryStage.setTitle("Mine Sweeper");
    primaryStage.setScene(scene);
    primaryStage.setWidth(COLS * GRID_SIZE);
    primaryStage.setHeight(ROWS * GRID_SIZE + HEAD_HEIGHT);
    primaryStage.show();

    // Cleaning job before destroy
    primaryStage.setOnCloseRequest((WindowEvent evt) -> {
      System.out.println("Closing App");
      System.out.println(evt);
      cvAgent.setClosed();
    });
  }
}
