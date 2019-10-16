package exercise.Lab4;

import java.util.EventListener;

//Interface of a Listener of the value
public interface ValueListener extends EventListener {
        public void valueChanged(ValueChangedEvent event);
}
