package exercise;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.text.DecimalFormat;

public class Lab1 extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    Insets nodePadding = new Insets(3, 5, 3, 5);

    HBox btns = new HBox();
    Button btnC=new Button("Clear");
    Button btnE=new Button("Exit");
    btns.getChildren().addAll(btnC,btnE );
    btns.setAlignment(Pos.BOTTOM_RIGHT);
    btns.setSpacing(10);
    btns.setPadding(nodePadding);

    FlowPane up = new FlowPane();
    TextField frh = new TextField();
    TextField cel = new TextField();
    VBox cB = new VBox();
    VBox fB = new VBox();
    cB.getChildren().addAll(new Label("Celsius:"), cel);
    fB.getChildren().addAll(new Label("Fahrenheit:"), frh);
    up.getChildren().addAll(cB, fB);

    up.setPadding(new Insets(5, 10, 15, 10));
    cB.setPadding(nodePadding);
    fB.setPadding(nodePadding);

    BorderPane root = new BorderPane();
    root.setCenter(up);
    root.setBottom(btns);

    primaryStage.setTitle("Temp Converter");
    primaryStage.setScene(new Scene(root, 400, 170));
    primaryStage.show();

    boolean[] callbackLock={false,false};
    DecimalFormat fnum   =   new   DecimalFormat("##0.00");
    cel.textProperty().addListener((obs, pre, now) -> {
      if (callbackLock[0]){// prevent inter invoking
        callbackLock[0]=false;
        return;
      }
      try {
        Float.parseFloat(now);
      } catch (Exception err) {
        return;
      }
      callbackLock[1]=true;// lock frh callback before change its value
      frh.setText(String.valueOf(fnum.format (Float.parseFloat(now) * 9 / 5 + 32)));
    });
    frh.textProperty().addListener((obs,pre,now)->{
      if (callbackLock[1]){
        callbackLock[1]=false;
        return;
      }
      try{
        Float.parseFloat(now);
      }catch (Exception err){
        return;
      }
      callbackLock[0]=true;// lock cel callback before change its value
      cel.setText(String.valueOf(fnum.format( (Float.parseFloat(now)-32)*5/9)));
    });

    frh.setOnMouseClicked(e->{

    });

    btnC.setOnMouseClicked(e->{
      frh.setText("0");
    });
  }


}
