package exercise.Lab3;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Lab3 extends Application {
  @Override
  public void start(Stage primaryStage) throws Exception {
    Pane root = new Pane();
    App app = new App() ;
    root.getChildren().add(app);

    //  root.getChildren().add(app);
    primaryStage.setTitle("Model - View - Controller");
    primaryStage.setScene(new Scene(root, 550, 180));
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
