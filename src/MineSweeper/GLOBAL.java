package MineSweeper;

public class GLOBAL {
  // variable
  static int COLS = 30;
  static int ROWS = 30;
  // final
  static final int GRID_SIZE = 5;
  static final int HEAD_HEIGHT = 150;
  static final String[] STYLE_REVEALED = {
          "-fx-background-color: #eeeeee;"     //0 mine around
          , "-fx-background-color: #dddddd;"   //1
          , "-fx-background-color: #cccccc;"   //2
          , "-fx-background-color: #bbbbbb;"
          , "-fx-background-color: #aaaaaa;"
          , "-fx-background-color: #999999;"
          , "-fx-background-color: #666666;"
          , "-fx-background-color: #333333;"     //7
          , "-fx-background-color: #000000;"      //8
  };
  static final String STYLE_HALF_PRESSED = "-fx-background-color: #666666;";
//  static final String STYLE_BOOM = "-fx-background-color: #ee6666;";
  static final String STYLE_BOOM = "-fx-background-color: #996633;";
  static final String STYLE_FLAG = "-fx-background-color: #663399;";

//----------
  static void calculate(){

  }
}
