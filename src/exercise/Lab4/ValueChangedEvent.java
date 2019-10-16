package exercise.Lab4;

import javafx.event.Event;
import javafx.event.EventType;

public class ValueChangedEvent extends Event {

  private double newValue;
  private Object _source;
  public final static EventType<ValueChangedEvent> VALUE_CHANGED = new EventType<>("VALUE_CHANGED");

  //Evenement fire when the value has been changed
  public ValueChangedEvent(Object source, double newValue) {
    super(VALUE_CHANGED);
    this.newValue = newValue;
    _source = source;
  }

  public double getNewValue() {
    return newValue;
  }
  public Object getEvtSource(){
      return  _source;
  }
}
