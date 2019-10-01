package exercise;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.File;

public class Controller2 extends VBox{


    ObservableList<String> optionsListview;  // string of files in directory
    ObservableList<String> optionsListRep  ; // string of parent directories
    FileSelector fs;						 // class the reads directories

    private Button btnCancel;
    private Button btnOpen;
    private  ListView listViewFile;		// list to show files inside directory
    private ComboBox<String> comboxRep; // combo box of parent directories

    public Controller2(){
        super();
         fs = new FileSelector();
         initialize();
    }

    public void initialize(){


    	// intialization
    	btnOpen = new Button("Open");
    	btnCancel = new Button("Cancel");
    	comboxRep = new ComboBox<String>();
    	listViewFile = new ListView();

    	// layout
    	HBox allBtns = new HBox();
    	allBtns.setAlignment(Pos.CENTER_RIGHT);
    	allBtns.setSpacing(10);
    	allBtns.setPadding(new Insets(10, 10, 10, 10));
    	allBtns.getChildren().addAll(btnOpen,btnCancel);
    	this.getChildren().addAll(comboxRep,listViewFile, allBtns);

    	// initial loading of lists for combo and list
    	// first get the path, update the combo, set the selected item, update the list
        String path = System.getProperty("user.dir");
        System.out.println(path);
        majComboView(path);
        comboxRep.getSelectionModel().selectLast();
        majListeView();


        comboxRep.setOnAction(event ->  {
            majListeView();
        });


        btnOpen.setOnMouseClicked(event -> {
        	File dir = new File(comboxRep.getValue()+listViewFile.getSelectionModel().getSelectedItem()) ;
        	if(dir.isFile()){
        		//Do something with the file (open it?)
        		System.exit(0);

        	} else if(dir.isDirectory()){
        		majComboView(dir.getAbsolutePath());
        	} else{
        		//DO something else
        	}
        });



        listViewFile.setOnMouseClicked(event -> {
        	btnOpen.setDisable(false);
        	if(event.getClickCount() == 2){
        		System.out.println("Double clicked");
        		btnOpen.fireEvent(event); // explicitly firing an event on another widget
        	}
        });



        // with a lambda expression
        btnCancel.setOnAction((event)-> {
        	System.exit(0);
        });
    }


    /*
     * updates the data model (i.e., what is stored inside the list)
     * depending on the selected item in the combo
     */
    private void majListeView() {

    	if (comboxRep.getSelectionModel().getSelectedItem() != null ){
    		optionsListview =
    				FXCollections.observableArrayList(
    						fs.getListFile(comboxRep.getSelectionModel().getSelectedItem())

    						);

    		listViewFile.setItems(optionsListview);
    	}

    	// when list is updated check if there is a selected item to activate button
    	if (listViewFile.getSelectionModel().getSelectedItem() != null) {
    		btnOpen.setDisable(false);
    	}else {
    		btnOpen.setDisable(true);
    	}

    }

    /*
     * updates the combo view based on the selected path
     */
    private void majComboView(String path){
    	optionsListRep =  FXCollections.observableArrayList(fs.getListRepParent(path) );
    	comboxRep.setItems(optionsListRep);
    	comboxRep.getSelectionModel().selectLast();
    }
}






