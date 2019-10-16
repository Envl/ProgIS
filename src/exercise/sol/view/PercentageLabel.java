package exercise.sol.view;


import exercise.Lab4.Percentage_Controller;
import exercise.Lab4.ValueChangedEvent;
import exercise.Lab4.ValueListener;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Label;

/**
 * A MVC View of a Percentage as a label.
 * This is not a MVC Controller, so it does not allow to change the Percentage value
 * This class implements PercentageListener, to be informed of changes in the Percentage
 * This class does not need a reference to the model, since all needed information is contained
 * in the PercentageEvent.
 */
public class PercentageLabel extends Label implements Percentage_View, ValueListener {

	private static final long serialVersionUID = 1L;

	private final Percentage_Controller myController;

	public PercentageLabel(Percentage_Controller controller) {
		myController = controller;

		this.addEventHandler(ValueChangedEvent.VALUE_CHANGED, new EventHandler<ValueChangedEvent>() {
			@Override
			public void handle(ValueChangedEvent event) {
				System.out.println("Test de l'envois d'evenement");
			}
		});

	}

	/**
	 * Called by the Model (Percentage) when a change occurs
	 */
	@Override
	public void update(Event event) {
		if(event instanceof ValueChangedEvent){
			ValueChangedEvent e = (ValueChangedEvent) event;
			setText("" + Math.round(e.getNewValue() * 100) + "%");

			//test envois d'evenement
			fireEvent(new ValueChangedEvent(ValueChangedEvent.VALUE_CHANGED, 20));

		}
	}

	@Override
	public void valueChanged(ValueChangedEvent event ) {
		update(event);
	}






}
