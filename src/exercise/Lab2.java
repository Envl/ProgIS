package exercise;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Lab2 extends Application {
    public void start(Stage primaryStage) throws Exception{

    	Controller2 c = new Controller2();

        primaryStage.setTitle("File selector");
        primaryStage.setScene(new Scene(c, 216, 404));
        primaryStage.show();
    }


    public static void main(String[] args)
    {
        launch(args);
    }
}
