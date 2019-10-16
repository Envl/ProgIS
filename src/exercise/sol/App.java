package exercise.sol;

import exercise.sol.model.*;
import exercise.sol.view.*;
import exercise.sol.controller.*;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;



public class App extends VBox {



	private static final long serialVersionUID = 1L;

	// The "Model" of a Percentage
	Percentage_Model myModel;
	// The "Controller" of a Percentage
	Percentage_Controller myController;

	// A Views
	PercentageLabel myTextView;
	PercentagePieChart myPieViewAndController;
	PercentageSlider mySliderViewAndController;
	ConsoleView myConsoleView;

	/** Construct the application */
	public App() {

		// Create the model and its controller
		myModel = new Percentage_Model(0.33f);
		myController = new Percentage_Controller(myModel);

		// Create the views
		myTextView = new PercentageLabel(myController);
		myPieViewAndController = new PercentagePieChart(myController);
		mySliderViewAndController = new PercentageSlider(myController);
		myConsoleView = new ConsoleView(myController);


		// Connect the views to the controller
		myController.addListenerValue( myTextView);
		myController.addListenerValue( myPieViewAndController);
		myController.addListenerValue( mySliderViewAndController);
		myController.addListenerValue( myConsoleView);

		// Initialize the GUI
		initUI();

		// Resize window and make it visible
		setVisible(true);
	}


	private void initUI() {

		Label label1 = new Label("Percentage:");

		//JLabel jLabel1 = new JLabel("Percentage:");

		myTextView.setDisable(true);


		HBox northPanel = new HBox();
		northPanel.getChildren().addAll(label1,myTextView);


		this.setSpacing(30);

		this.getChildren().addAll(northPanel,myPieViewAndController,mySliderViewAndController);
	}



}
