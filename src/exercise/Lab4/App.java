package exercise.Lab4;

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

  /**
   * Construct the application
   */
  public App() {
    super();
    // Create the model and its controller
    myModel = new Percentage_Model(0.33f);
    myController = new Percentage_Controller(myModel);

    // Create the views
    myTextView = new PercentageLabel(myController);
    // TODO create other views
    myPieViewAndController = new PercentagePieChart(myController);
    mySliderViewAndController = new PercentageSlider(myController);
    // Connect the views to the controller
    myController.addListenerValue(myTextView);
    // TODO connect other views
//		myController
		myController.addListenerValue(myPieViewAndController);
		myController.addListenerValue(mySliderViewAndController);

    // Initialize the GUI
    initUI();

    // Resize window and make it visible
    setVisible(true);
  }


  private void initUI() {
    Label label1 = new Label("Percentage:");
    myTextView.setDisable(true);
    HBox northPanel = new HBox();
    northPanel.getChildren().addAll(label1, myTextView);
    this.setSpacing(30);
    this.getChildren();
    this.getChildren().addAll(northPanel, myPieViewAndController, mySliderViewAndController);
  }


}
