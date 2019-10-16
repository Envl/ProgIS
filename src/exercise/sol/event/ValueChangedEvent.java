package exercise.sol.event;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventType;

import java.util.EventObject;

public class ValueChangedEvent extends Event{

    private double newValue;
    public final static EventType<ValueChangedEvent> VALUE_CHANGED = new EventType<>("VALUE_CHANGED");


    public ValueChangedEvent(Object source, double newValue){
            super(VALUE_CHANGED);
            this.newValue = newValue;
        }

        public double getNewValue(){
            return newValue;
        }
    }
