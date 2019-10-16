package exercise.Lab3;


import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class RGBColorController {

	public ColorController red;
	public ColorController green;
	public ColorController blue;

	public RGBColorController(Integer red, Integer green, Integer blue) {
		this.red = new ColorController(this, red);
		this.green = new ColorController(this, green);
		this.blue = new ColorController(this, blue);
	}

	public void set(Integer red, Integer green, Integer blue, Object caller) {
		setRed(red, caller);
		setGreen(green, caller);
		setBlue(blue, caller);
	}

	public void setRed(Integer value, Object caller) {
		red.setValue(value, caller);
	}

	public void setGreen(Integer value, Object caller) {
		green.setValue(value, caller);
	}

	public void setBlue(Integer value, Object caller) {
		blue.setValue(value, caller);
	}
	
	public String toHex() {
		return red.toHex(2) + green.toHex(2) + blue.toHex(2);
	}

	/**
	 * Binds a text field to this color model. The text field receives the hex
	 * value of the color.
	 */
	public void bind(final TextField textField) {
		// view for each color
		ColorView view = new ColorView() {
			TextField that = textField;

			@Override
			public void update(ColorController controller, Object caller) {
				if (!that.equals(caller)) {
					that.setText(controller.getSuperController().toHex());
				}
			}
		};
		red.addView(view);
		green.addView(view);
		blue.addView(view);
		
		// Listener for text field changes
		textField.setOnKeyReleased(new EventHandler<KeyEvent>() {
			TextField that = textField;

			@Override
			public void handle(KeyEvent e) {
				String value = that.getText();
				switch (value.length()) {
				// Support hex color format with only 3 digits by converting it
				// to
				// a 6 digit hex color format
				case 3:
					value = "" + value.charAt(0) + value.charAt(0)
							+ value.charAt(1) + value.charAt(1)
							+ value.charAt(2) + value.charAt(2);
					// Parse a regular 6 digit hex color format
				case 6:
					try {
						Integer r = Integer.parseInt(value.substring(0, 2), 16);
						Integer g = Integer.parseInt(value.substring(2, 4), 16);
						Integer b = Integer.parseInt(value.substring(4, 6), 16);
						red.setValue(r, that);
						green.setValue(g, that);
						blue.setValue(b, that);
					} catch (NumberFormatException ex) {
						// Abord in case there is a parsing error
					}
					break;
				}
			}
		});
		// Update the text field to the current color value
		textField.setText(this.toHex());
	}

	/**
	 * Binds a panel to this color model. The panel's background color is set to
	 * the components of this color model.
	 */
	public void bind(final Rectangle panel) {
		// view for each color 
		ColorView view = new ColorView() {
			Rectangle that = panel;

			@Override
			public void update(ColorController controller, Object caller) {
				RGBColorController tmp = controller.getSuperController();
				Color color = Color.rgb(tmp.red.getValue(), tmp.green.getValue(),
						tmp.blue.getValue());
				that.setFill(color);
			}
		};
		red.addView(view);
		green.addView(view);
		blue.addView(view);
		
		// Update the panel to the current color value
		Color color = Color.rgb(red.getValue(), green.getValue(),
				blue.getValue());
		panel.setFill(color);
	}

}
