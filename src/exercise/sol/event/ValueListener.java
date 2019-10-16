package exercise.sol.event;

import javafx.event.Event;
import java.util.EventListener;

public interface ValueListener extends EventListener {
        public void valueChanged(ValueChangedEvent event);
}
