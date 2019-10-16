package exercise.Lab3;


import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;


public class ColorController {

	private ColorModel model;
	private RGBColorController superController;
	private ArrayList<ColorView> views;
	
	public ColorController(RGBColorController sController, Integer value) {
		model = new ColorModel(value);
		superController = sController;
		views = new ArrayList<ColorView>();
	}

	void setValue(Integer value, Object caller) {
		model.setValue(value);
		for (ColorView view : views) {
			view.update(this, caller);
		}
	}

	void setValue(String value, Object caller) {
		try {
			this.setValue(Integer.parseInt(value), caller);
		} catch (NumberFormatException e) {

		}
	}
	
	public Integer getValue() {
		return model.getValue();
	}
	
	public String toHex() {
		return model.toHex();
	}
	
	public String toHex(Integer minLength) {
		return model.toHex(minLength);
	}
	
	public RGBColorController getSuperController() {
		return superController;
	}

	public void addView(ColorView view) {
		views.add(view);
	}

	public void removeView(ColorView view) {
		views.remove(view);
	}

	/**
	 * Binds a slider to the integer.
	 */
	public void bind(final Slider slider) {
		// view for the color
		this.addView(new ColorView() {
			Slider that = slider;

			@Override
			public void update(ColorController controller, Object caller) {
				if (!that.equals(caller)) {
					that.setValue(controller.getValue());
				}
			}
		});
		
		
		// Listener for slider changes
		slider.valueProperty().addListener(new ChangeListener<Number>() {
			Slider that = slider;
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
            	
            	Double value = that.getValue();
            	Integer ivalue = value.intValue();
            	ColorController.this.setValue(ivalue, that);
            }
        });
		
		
		// Update the slider to the integer value
		slider.setValue(getValue());
	}

	/**
	 * Binds a text field to the integer.
	 */
	public void bind(final TextField textField) {
		// view for the color
		this.addView(new ColorView() {
			TextField that = textField;

			@Override
			public void update(ColorController controller, Object caller) {
				if (!that.equals(caller)) {
					that.setText(controller.getValue().toString());
				}
			}
		});
		// Listener for text field input		
		EventHandler textFieldListener = new EventHandler<KeyEvent>() {
			TextField that = textField;
            @Override
            public void handle(KeyEvent e) {
                if (e.getCode().equals(KeyCode.ENTER)) {
                	ColorController.this.setValue(that.getText(), that);
                } else{
                    //nothing
                }
            }
        };
        textField.setOnKeyReleased(textFieldListener);
		
		// Update the text field to the integer value
		textField.setText(getValue().toString());
	}

}
