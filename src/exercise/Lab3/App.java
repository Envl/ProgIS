package exercise.Lab3;



import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;


public class App extends HBox {
	private static final long serialVersionUID = 1L;

	private RGBColorController controller; // Color controller
	private Rectangle sample; // Color preview box
	private TextField colorCode; // Text field containing the color in hex format

	/** Construct the application */
	public App() {
		controller = new RGBColorController(79, 178, 255);
		initUI();
	}

	public RGBColorController getController() {
		return controller;
	}
	
	private void initUI() {

		// Column 1: Sliders
		
		VBox sliderPane = new VBox();
		sliderPane.setPadding(new Insets(20, 10, 10, 20));
		sliderPane.setSpacing(30);
		// TODO add sliders to sliderPane and bind sliders to controller
		// Red
		Slider sR=new Slider();
		Slider sG=new Slider();
		Slider sB=new Slider();
		sliderPane.getChildren().addAll(sR,sG,sB);
		controller.red.bind(sR);
		controller.blue.bind(sB);
		controller.green.bind(sG);
		// Green
		// ...
		// Blue
		// ...

		// Column 2: Text fields (next to the sliders)
		VBox textPane = new VBox();		// Red
		textPane.setPadding(new Insets(10, 10, 10, 10));
		textPane.setSpacing(20);
		// TODO add textfields to textPane and bind textfields to controller
		// Red
		TextField tR=new TextField("R");
		TextField tG=new TextField("G");
		TextField tB=new TextField("B");
		textPane.getChildren().addAll(tR,tG,tB);
		controller.red.bind(tR);
		controller.blue.bind(tB);
		controller.green.bind(tG);
		// Green
		// ...
		// Blue
		// ...

		// Column 3: Hex color field and color sample
		VBox hexPane = new VBox();
		hexPane.setAlignment(Pos.CENTER);
		hexPane.setPadding(new Insets(10, 10, 10, 10));
		hexPane.setSpacing(10); 
		// Color in hex format
		// TODO add colorfield  to hexPane and bind it to controller
		TextField tHex=new TextField("HEX");
		hexPane.getChildren().add(tHex);
		// Color preview
		sample = new Rectangle (100,100);
		hexPane.getChildren().add(sample);
		controller.bind(sample);
		controller.bind(tHex);

		// Resize window and make it visible
		this.getChildren().addAll(sliderPane, textPane, hexPane);	}



}
