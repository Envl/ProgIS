package exercise.Lab4;

import exercise.Lab4.Percentage_Controller;

import javafx.scene.control.Label;


/**
 * A MVC View of a Percentage as a label.
 * This is not a MVC Controller, so it does not allow to change the Percentage value
 * This class implements ValueListener, to be informed of changes in the Percentage
 */

//Implements something
public class PercentageLabel extends Label implements ValueListener {

	private static final long serialVersionUID = 1L;

	private final Percentage_Controller myController;

	public PercentageLabel(Percentage_Controller controller) {
		myController = controller;
	}


	@Override
	public void valueChanged(ValueChangedEvent event) {

	}

	//Override methods




}
