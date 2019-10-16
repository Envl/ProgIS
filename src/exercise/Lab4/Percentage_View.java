package exercise.Lab4;

import exercise.Lab4.Percentage_Controller;
import javafx.event.Event;


public interface Percentage_View   {

	Percentage_Controller controller = null;

	public abstract void update(Event event);

}
