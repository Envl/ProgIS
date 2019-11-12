package MineSweeper;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.opencv.core.Core;

import static MineSweeper.GLOBAL.*;


public class MineSweeper extends Application {
  // const values
  // variables
  private CVAgent cvAgent = new CVAgent();
  private GameModel model = new GameModel(ROWS, COLS, 0.1f);
  private float lifeBarWidth = (COLS * GRID_SIZE - HEAD_HEIGHT * 1.3333f) + 20;
  private float lastLife = model.life;
  private float newLifeBarWidth = (COLS * GRID_SIZE - HEAD_HEIGHT * 1.3333f) + 20;
  float lastTime = 0;
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
    cvAgent.startCamera();
    super.init();
    _board.bindModel(model);
    Button btnStart = new Button("(Re)Start Game");
    btnStart.setLayoutX(HEAD_HEIGHT * 1.3333f);
    btnStart.setPrefWidth(COLS * GRID_SIZE - HEAD_HEIGHT * 1.3333f);
    btnStart.setPrefHeight(HEAD_HEIGHT);
    btnStart.setStyle("-fx-font-size:50px;" +
            "\n -fx-background-color: #00000000;");
    btnStart.setOnMouseClicked(evt -> {
      initGame();
    });
    Canvas btnBegin = new Canvas(COLS * GRID_SIZE - HEAD_HEIGHT * 1.3333f, HEAD_HEIGHT);
    btnBegin.setOnMouseClicked(evt -> {
              initGame();
            }
    );
    btnBegin.setLayoutX(HEAD_HEIGHT * 1.3333f);
    new AnimationTimer() {
      public void handle(long currentNanoTime) {
        if (lastTime == 0) {
          lastTime = currentNanoTime;
        }
        float delta = (currentNanoTime - lastTime) / 1000000000f;
        btnBegin.getGraphicsContext2D().setFill(new Color(0.9, 0.9, 0.9, 1f));
        btnBegin.getGraphicsContext2D().fillRect(0, 0,
                COLS * GRID_SIZE - HEAD_HEIGHT * 1.3333, HEAD_HEIGHT * 1.3333);
        btnBegin.getGraphicsContext2D().setFill(new Color(0.4, 0.2 * model.life, 0.1, 1));
        if (model.life < lastLife) {
          lastLife = model.life;
          newLifeBarWidth = (COLS * GRID_SIZE - HEAD_HEIGHT * 1.3333f) * model.life / LIFE + 20;
        }
        if (newLifeBarWidth <= lifeBarWidth) {
          lifeBarWidth -= delta * 100;
        }
        btnBegin.getGraphicsContext2D().fillRect(0, 0,
                lifeBarWidth, HEAD_HEIGHT);
        lastTime = currentNanoTime;
      }
    }.start();
    ImageView cameraView = new ImageView();
    cvAgent.setCameraView(cameraView);
    _board.setPos(0, HEAD_HEIGHT);
    _root.getChildren().addAll(_board.rootInstance(), cameraView, btnBegin, btnStart);

  }

  void initGame() {
    model.initTable(cvAgent.genTable());
    _board.updateUI();
    _board.updateGridStyles();
    lastLife = model.life;
    lifeBarWidth = (COLS * GRID_SIZE - HEAD_HEIGHT * 1.3333f) + 20;
    newLifeBarWidth = (COLS * GRID_SIZE - HEAD_HEIGHT * 1.3333f) + 20;
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    Scene scene = new Scene(_root, COLS * GRID_SIZE, ROWS * GRID_SIZE + HEAD_HEIGHT);
    primaryStage.setTitle("Mine Sweeper");
    primaryStage.setScene(scene);
    primaryStage.setWidth(WIN_W);
    primaryStage.setHeight(WIN_H);
    primaryStage.show();

    initGame();

    // Cleaning job before destroy
    primaryStage.setOnCloseRequest((WindowEvent evt) -> {
      System.out.println("Closing App");
      System.out.println(evt);
      cvAgent.setClosed();
    });
  }
}
