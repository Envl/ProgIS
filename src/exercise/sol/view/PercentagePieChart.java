package exercise.sol.view;


import exercise.Lab4.Percentage_Controller;
import exercise.Lab4.ValueChangedEvent;
import exercise.Lab4.ValueListener;
import javafx.event.Event;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

/**
 * A PercentagePieChart acts as a MVC View of a Percentage It maintains a
 * reference to its model in order to repaint itself.
 **/
public class PercentagePieChart  extends Canvas implements Percentage_View, ValueListener {

	private static final long serialVersionUID = 1L;
	/**
	 * Hold a reference to the model
	 */
	private final Percentage_Controller myController;
	private boolean isPressedOnPin;
    GraphicsContext gc;



	public PercentagePieChart(Percentage_Controller controller) {


		super(100,100);
		myController = controller;
		isPressedOnPin = false;

		gc = this.getGraphicsContext2D();
        drawShapes(0.0);



        this.addEventHandler(MouseEvent.MOUSE_PRESSED, event-> {
            if (inPin(event)) {
                isPressedOnPin = true;
            }
        });

        this.addEventHandler(MouseEvent.MOUSE_RELEASED, event-> { isPressedOnPin = false;});

        this.addEventHandler(MouseEvent.MOUSE_DRAGGED, event-> {
            if (inPin(event)) {
                isPressedOnPin = true;
            }
        });
         this.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
             if (isPressedOnPin) {
                 myController.notifyValueChanged(pointToPercentage(event));
             }
         });


	}

	// "View" behaviour : when the percentage changes, the piechart must be repainted
	public void update(Event event) {
		if(event instanceof ValueChangedEvent) {
			ValueChangedEvent e = (ValueChangedEvent) event;
			double value = e.getNewValue();
			this.drawShapes(value);
		}

	}

	private void drawShapes(double value) {
		double centerX = this.getWidth() / 2;
		double centerY = this.getHeight() / 2;
		double radius = Math.min(getWidth() - 4, getHeight() - 4) / 2;
		double angle = value * 2 * Math.PI;
		gc.setFill(Color.GRAY);

		gc.fillOval(centerX - radius, centerY - radius, radius * 2,
				radius * 2);
        gc.setFill(Color.YELLOW);
        gc.fillArc(centerX - radius, centerY - radius, radius * 2,
				radius * 2, 0, (double) Math.toDegrees(angle), ArcType.ROUND);

		double pinX = centerX + (int) (Math.cos(angle) * radius);
		double pinY = centerY - (int) (Math.sin(angle) * radius);

        gc.setFill(Color.GRAY);

	}


// for "controller" behaviour
	/**
	 * Test if a mouse event is inside the "Pin" that allows
	 * to change the percentage
	 */
	private boolean inPin(MouseEvent ev) {
		double mouseX = ev.getX();
		double mouseY = ev.getY();
		double centerX = this.getWidth() / 2;
		double centerY = this.getHeight() / 2;
		double radius = Math.min(getWidth() - 4, getHeight() - 4) / 2;
		double angle = myController.getValue() * 2 * Math.PI;
		double pinX = centerX + (int) (Math.cos(angle) * radius);
		double pinY = centerY - (int) (Math.sin(angle) * radius);


        //System.out.println("mouseX :  " +  mouseX + "  mouseY :  " + mouseY +  " centerX  " + centerX + "  centerY " + centerY);


		return true;
	}


	// Converts a mouse position to a Percentage value
	private double pointToPercentage(MouseEvent e) {

        //A point at angle theta on the circle whose
        // centre is (x0,y0) and whose radius is r is (x0 + r cos theta, y0 + r sin theta).
        // Now choose theta values evenly spaced between 0 and 2pi.

		double centerX = this.getWidth() / 2;
		double centerY = this.getHeight() / 2;
		double mouseX = e.getX() - centerX;
		double mouseY = e.getY() - centerY;
		double l = Math.sqrt(mouseX * mouseX + mouseY * mouseY);
		double lx = mouseX / l;
		double ly = mouseY / l;
		double theta = 0;

		if (lx >= 0) {
			theta = Math.atan(ly / lx);
		} else if (lx < 0) {
			theta = -1 * Math.atan(ly / lx);
		}

		if ( (mouseX > 0) && (mouseY < 0)) {
			theta = -1 * theta;

        } else if (mouseX < 0) {
			theta += Math.PI;
        } else {
			theta = 2 * Math.PI - theta;
		}
		return  (theta / (2 * Math.PI));
	}

	@Override
    public void valueChanged(ValueChangedEvent event) {
	    update(event);
    }
}
