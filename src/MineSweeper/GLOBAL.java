package MineSweeper;

public class GLOBAL {
  // variable
  static int COLS = 34;
  static int ROWS = 26;
  // final
  static final int LIFE=5;
  static final int GRID_SIZE = 30;
  static final int WIN_W = GRID_SIZE * COLS - 18;
  static final int HEAD_HEIGHT = 150;
  static final int WIN_H = GRID_SIZE * ROWS + HEAD_HEIGHT + 13;
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
  static final String STYLE_DEFAULT = "-fx-background-color: #eeeeee;" +
          "\n -fx-background-radius: 3px;" +
          "\n -fx-border-color: #000000;" +
          "\n -fx-border-radius: 3px;" +
          "\n -fx-border-width: 1px;";
  static final String STYLE_OVER = "-fx-background-color:#666666;";
  static final String STYLE_OVERBG = "-fx-background-color:#aaaaaa;";

  //----------
  static void calculate() {

  }
}
