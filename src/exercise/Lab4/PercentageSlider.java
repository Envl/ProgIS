package exercise.Lab4;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;

/**
 * A PercentagePieSlider acts boths as a MVC View and Controller of a Percentage
 * It maintains a reference to its model in order to update it.
 */
public class PercentageSlider extends Slider implements ValueListener {

  private static final long serialVersionUID = 1L;

  private final Percentage_Controller myController;

  public PercentageSlider(Percentage_Controller controller) {
    myController = controller;
    setMin(0);
    setMax(100);

    this.setMinorTickCount(5);
    this.setMajorTickUnit(10);
    this.setShowTickLabels(true);
    this.setShowTickMarks(true);
    this.setBlockIncrement(10);
    this.setSnapToTicks(true);

    // "Controller" behaviour : when the value of the slider changes,
    // The model must be updated
    valueProperty().addListener(new javafx.beans.value.ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        //Do something with the controller
        System.out.println("evt changed");
        System.out.println(newValue);
        if (!newValue.equals(oldValue)){

					myController.setValue(this,newValue.doubleValue());
				}
      }
    });
  }


  //Override the good method
  //TODO
  @Override
  public void valueChanged(ValueChangedEvent event) {
    // exclude self
		System.out.println("outside");
		System.out.println(this);
//    if (event.getEvtSource().equals(this)) {
      setValue(event.getNewValue());
//    }

  }

  //getter of the controller
  public Controller_Interface getController() {
    return myController;
  }


}
