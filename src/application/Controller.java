package application;

import java.util.Map;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Controller {
	
	@FXML
	Label lblStatus,lblNotLoaded,lblName,lblDesc;
	@FXML
	MenuItem menuLoad,menuSave,menuExit,menuAdd,menuDelete,menuAbout;
	@FXML
	TextField fieldSearch, fieldName, fieldDiet;
	@FXML
	TextArea fieldDesc;
	@FXML
	Button buttonSearch,buttonSearchClear,buttonCancel,buttonSubmit;
	@FXML
	Tab tabDatabase,tabProperties,tabDiet;
	@FXML
	ListView<String> listDatabase,listDiet;
	
	public void searchButtonClicked() {
		System.out.println("Search for '" + fieldSearch.getText() + "'");
		
		listDatabase.getItems().clear();
		
		Animal result = DataManager.searchAnimals(fieldSearch.getText());
		
		if(fieldSearch.getText().equals("")) {
			populateDatabase();
		}
		else if(result != null)
			listDatabase.getItems().addAll(result.getName());
		else
			lblStatus.setText("No animal found for " + fieldSearch.getText());
		
	}
	
	public void searchClear() {
		listDatabase.getItems().clear();
		lblStatus.setText("Search Filters Cleared");
		populateDatabase();
	}
	
	public void saveData() {
		DataManager.save();
		lblStatus.setText(DataManager.status);
	}
	
	public void loadData() {
		if(!DataManager.isInitialized)
			DataManager.initialize();
		else
			DataManager.load();
		
		if(DataManager.isInitialized) {
			lblNotLoaded.setVisible(false);
			menuSave.setDisable(false);
			menuAdd.setDisable(false);
			buttonSearch.setDisable(false);
			buttonSearchClear.setDisable(false);
			fieldSearch.setDisable(false);
			tabDatabase.setDisable(false);
			
			populateDatabase();
			
			listDatabase.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		}
		else
			lblStatus.setText("Failed to Initialize Data Manager");
	}
	
	public void populateDatabase() {
		
		listDatabase.getItems().clear();
		lblName.setText("n/a");
		lblDesc.setText("n/a");
		fieldSearch.setText("");
		
		listDiet.getItems().clear();
		
		tabProperties.setDisable(true);
		tabDiet.setDisable(true);
		
		for(Map.Entry<String, Edible> m : SaveData.animalsMap.entrySet()) {
			listDatabase.getItems().add(m.getValue().getName());
		}
		
	}
	
	public void itemSelected() {
		if(listDatabase.getSelectionModel().getSelectedItem() != null) {
			System.out.println(listDatabase.getSelectionModel().getSelectedItem());
			
			Animal selection = DataManager.searchAnimals(listDatabase.getSelectionModel().getSelectedItem());
			
			lblName.setText(selection.getName());
			lblDesc.setText(selection.getDesc());
			
			listDiet.getItems().clear();
			listDiet.getItems().addAll(selection.diet);
			
			tabProperties.setDisable(false);
			tabDiet.setDisable(false);
		}
	}
	
	public void exitApp() {
		System.exit(0);
	}
	
	
	public void addEntry() throws Exception {
		Stage stage;
		Parent root;
		
		stage = new Stage();
		root = FXMLLoader.load(getClass().getResource("AddEntry.fxml"));
		stage.setScene(new Scene(root));
		stage.setTitle("Add New Entry");
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.showAndWait();
		
		populateDatabase();
	}
	
	
	public void cancel() {
		Stage stage = (Stage)buttonCancel.getScene().getWindow();
		stage.close();
	}
	
	public void submitNewEntry() {
		String name, desc, dietRaw;
		String[] diet;
		
		name = fieldName.getText();
		desc = fieldDesc.getText();
		dietRaw = fieldDiet.getText();
		
		if(name.equals("") || desc.equals("") || dietRaw.equals("")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning: All Fields Required");
			alert.setHeaderText(null);
			alert.setContentText("You must enter text in every field to add a new Animal.");
			alert.showAndWait();
		}
		else {
			name = name.split(",")[0];
			desc = desc.split(",")[0];
			diet = dietRaw.split(",");
			
			DataManager.addNewAnimal(name, desc, diet);
			cancel();
		}
		
		
	}
	
	public void checkInput(KeyEvent e) {
		if(e.getCharacter().equals(",")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning: Comma Detected");
			alert.setHeaderText(null);
			alert.setContentText("Heads up, commas will be ignored and anything typed in after them will be deleted! You should only use them to separate foods in the Diet section.");
			alert.showAndWait();
		}
	}
	
	public void showAbout() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("About Zoo Manager");
		alert.setHeaderText(null);
		alert.setContentText("Zoo Manager v0.3: Project 3 Edition\n\nCopyright© 2017 Michael Provan\n\nPost Questions and Issues at: https://github.com/ManfranMike/COP2552_Project");
		alert.showAndWait();
	}
}
