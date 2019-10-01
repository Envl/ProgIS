package MineSweeper;

import java.util.function.Function;

public class ArrayHelper {
  int _rows, _cols;

  public ArrayHelper(int rows, int cols) {
    _rows = rows;
    _cols = cols;
  }

  public boolean testInside(int row, int col) {
    return col >= 0 && col < _cols && row >= 0 && row < _rows;
  }


}
