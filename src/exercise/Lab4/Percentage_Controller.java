package exercise.Lab4;


/**
 * This class is a MVC "Controller" of a percentage (a value such as 0 <= x <= 1)
 **/
public class Percentage_Controller implements Controller_Interface {
  private final Percentage_Model myModel;
  /**
   * Create a Percentage with a model
   *
   * @param model : the model of this controller
   * @throws IllegalArgumentException if value is not correct
   **/
  public Percentage_Controller(Percentage_Model model) {
    myModel = model;
  }


  /**
   * Change the value of this Percentage.
   * Update the views
   *
   * @param value : the value
   * @throws IllegalArgumentException if value is not correct
   **/
  public void setValue(Double value) {
    //TODO set the value of the model
    myModel.setValue(value);
  }


  /**
   * Get the value from the model
   *
   * @return the percentage value
   */
  public double getValue() {
    return 0.0f; // TODO return the value of the percentage from the model instead
  }

  /**
   * Add a new Listener to the model
   *
   * @param valueL the new listener
   **/
  @Override
  public void addListenerValue(ValueListener valueL) {
    // TODO add the listener from the model
    myModel.addValueListener(valueL);
  }

  /**
   * Remove a Listener from the model
   *
   * @param valueL the  listener to remove
   **/
  @Override
  public void removeListenerValue(ValueListener valueL) {
    // TODO remove the listener from the model
    myModel.removeValueListener(valueL);
  }

  public void notifyValueChanged(double value) {
    //TODO refere the model that the value changed
  }


}
