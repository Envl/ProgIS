package exercise.sol.controller;


import exercise.sol.model.*;
import exercise.sol.event.*;
import exercise.sol.controller.*;

/**
 * This class is a MVC "Model" of a percentage (a value such as 0 <= x <= 1)
 **/
public class Percentage_Controller implements Controller_Interface {
	private final Percentage_Model myModel;
	//private Set<Percentage_View> myViews = new HashSet<>();

	/**
	 * Create a Percentage with a model
	 * @param model : the model of this controller
	 * @throws IllegalArgumentException if value is not correct
	 **/
	public Percentage_Controller(Percentage_Model model) {
		myModel = model;
	}



	/**
	 * Change the value of this Percentage.
	 * Update the views
	 * @param value : the value
	 * @throws IllegalArgumentException if value is not correct
	 **/
	public void setValue(Double value) {

		myModel.setValue(value);
	}


    /**
	 * Get the value from the model
	 * @return the percentage value
	 */
	public double getValue() {
		return myModel.getValue();
	}



	/**
	 * Add a new Listener to this model
	 * @param valueL     the new listener
	 **/
	@Override
	public void addListenerValue(ValueListener valueL) {
		myModel.addValueListener(valueL);
	}

	/**
	 * Remove a Listener from this model
	 * @param valueL     the  listener to remove
	 **/
    @Override
    public void removeListenerValue(ValueListener valueL) {
		myModel.removeValueListener(valueL);
	}

    public void notifyValueChanged(double value){
        myModel.setValue(value);
    }




}
