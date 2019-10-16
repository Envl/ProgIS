package MineSweeper;

import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;

import static MineSweeper.GLOBAL.*;

public class GameBoard {
  private int _cols = 10;
  private int _rows = 10;
  private int _life=3;
  private GridPane root = new GridPane();
  Button grids[];
  private GameModel _model;
  boolean _lDown=false;

  GameBoard(int rows, int cols) {
    initUI(rows,cols);
  }

  void bindModel(GameModel model) {
    _model = model;

  }
void updateUI(){
    initUI(_model.get_rows(),_model.get_cols());
}
  GridPane rootInstance() {
    return root;
  }
  void setPos(int x,int y){
    root.setLayoutX(x);
    root.setLayoutY(y);
  }

  void revealAll(){
    for (int row = 0; row < _rows; row ++) {
      for (int col = 0; col < _cols; col++) {
        _model.revealCell(row,col);
      }
    }
    updateGridStyles();
  }

  void initUI(int rows,int cols) {
    _rows=rows;
    _cols=cols;
    // game table
    // GUI Components
//    root.setCursor(Cursor.HAND_CURSOR);
root.getChildren().clear();
    grids = new Button[_rows * _cols];
    for (int row = 0; row < _rows; row++) {
      for (int col = 0; col < _cols; col++) {
        grids[_cols * row + col] = new Button("\uD83D\uDE04");
        grids[_cols * row + col].setPrefWidth(GRID_SIZE-1);
        grids[_cols * row + col].setPrefHeight(GRID_SIZE-1);
        grids[_cols * row + col].setId(row + "," + col);
        grids[_cols * row + col].setOnMouseReleased(evt -> {
          updateGridStyles();
          System.out.println("mouse release");
        });
        grids[_cols * row + col].setOnMousePressed(evt -> {
          System.out.println(evt.getSource());
          _lDown=true;
          int[] rc = extractRC(evt.getSource());
          switch (evt.getButton()) {
            case PRIMARY:
              _model.revealCell(rc[0], rc[1]);
              break;
            case SECONDARY:
              _model.flagCell(rc[0], rc[1]);
              break;
          }
          int[] tmpRC = extractRC(evt.getSource());
          grids[_cols*tmpRC[0]+tmpRC[1]].setStyle(STYLE_HALF_PRESSED);
        });

        // drag gesture
        grids[_cols * row + col].setOnDragDetected(evt -> {
          System.out.println(evt);
          int[] tmpRC = extractRC(evt.getSource());
          _model.revealCell(tmpRC[0], tmpRC[1]);
          Dragboard db = grids[_cols * tmpRC[0] + tmpRC[1]].startDragAndDrop(TransferMode.ANY);
//          Dragboard db = source.startDragAndDrop(TransferMode.ANY);
          ClipboardContent content = new ClipboardContent();
          content.putString(((Button) evt.getSource()).getId());
          db.setContent(content);
        });
        grids[_cols * row + col].setOnDragOver(evt -> {
//            evt.consume();
            evt.acceptTransferModes(TransferMode.ANY);
            int tmpRC[] = extractRC(evt.getSource());
            _model.revealCell(tmpRC[0], tmpRC[1]);
            System.out.println(evt.getSource());
            grids[_cols*tmpRC[0]+tmpRC[1]].setStyle(STYLE_HALF_PRESSED);
        });
        grids[_cols * row + col].setOnDragDone(evt -> {
          _lDown=false;
          updateGridStyles();
          System.out.println("drag done");
        });

        // row 0 is reserved for other Controls
        root.add(grids[_cols * row + col], col, row + 1);
      }
    }
  }

  // extract row and col of a grid
  private int[] extractRC(Object evtSource) {
    String[] tmpL = ((Button) evtSource).getId().split(",");
    //                    row                           col
    return new int[]{Integer.parseInt(tmpL[0]), Integer.parseInt(tmpL[1])};
  }

  void updateGridStyles() {
    for (int row = 0; row < _rows; row++) {
      for (int col = 0; col < _cols; col++) {
        switch (_model.gameTable[row][col]._state) {
          case 0: //unrevealed
            // init styles
            grids[_cols * row + col].setStyle(null);
            grids[_cols * row + col].setText("\uD83D\uDE04");
            break;
          case 1://revealed
            if (_model.gameTable[row][col]._isMine) {
              _life--;
              if (_life<=0){
                // todo gameover
              }
              System.out.println("BOOOOOM");
              grids[_cols * row + col].setText("\uD83C\uDF49"); // show text on btn
              grids[_cols * row + col].setStyle(STYLE_BOOM);// update style
            } else {
              grids[_cols * row + col].setText(String.valueOf(_model.gameTable[row][col]._num)); // show text on btn
              grids[_cols * row + col].setStyle(STYLE_REVEALED[_model.gameTable[row][col]._num]);// update style
            }
            break;
          case -1: //flagged
            grids[_cols * row + col].setText("⚑"); // show text on btn
            grids[_cols * row + col].setStyle(STYLE_FLAG);// update style
            break;
        }
      }
    }
  }
}
