/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;

import java.util.ResourceBundle;
import it.polito.tdp.food.model.Model;
import it.polito.tdp.food.model.VicinoPeso;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

//importato

public class FoodController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtCalorie"
    private TextField txtCalorie; // Value injected by FXMLLoader

    @FXML // fx:id="txtPassi"
    private TextField txtPassi; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCorrelate"
    private Button btnCorrelate; // Value injected by FXMLLoader

    @FXML // fx:id="btnCammino"
    private Button btnCammino; // Value injected by FXMLLoader

    @FXML // fx:id="boxPorzioni"
    private ComboBox<String> boxPorzioni; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCammino(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Cerco cammino peso massimo...\n\n");
    	
    	int passi=0;
    	try {
    		passi=Integer.parseInt(txtPassi.getText());
    	}
    	catch(NumberFormatException e) {
    		System.out.println("\n\nDevi inserire un numero!!\n\n");
    		return;
    	}
    	
    	String partenza=boxPorzioni.getValue();
    	if(partenza==null) {
    		System.out.println("\n\nDevi selezionare un tipo di porzione!!\n\n");
    		return;
    	}
    	
    	
    	txtResult.appendText(model.trovaPercorso(partenza, passi));
    	
    }

    @FXML
    void doCorrelate(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Cerco porzioni correlate...\n");
    	
    	String selezionato=boxPorzioni.getValue();
    	if(selezionato==null) {
    		System.out.println("\n\nDevi selezionare un tipo di porzione!!\n\n");
    		return;
    	}
    	
    	for(VicinoPeso v: model.rstituisciVicini(selezionato)) {
    		txtResult.appendText(v.getVicino()+" con peso "+v.getPeso()+"\n");
    	}
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Creazione grafo...");
    	int calorie=0;
    	try {
    		calorie=Integer.parseInt(txtCalorie.getText());
    	}
    	catch(NumberFormatException e) {
    		System.out.println("\n\nDevi inserire un numero!!\n\n");
    		return;
    	}
    	
    	System.out.println("Calorie inserite: "+calorie);
    	model.creaGrafo(calorie);
    	boxPorzioni.getItems().removeAll();
    	boxPorzioni.getItems().addAll(model.restituisciVertici());
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtCalorie != null : "fx:id=\"txtCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtPassi != null : "fx:id=\"txtPassi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCorrelate != null : "fx:id=\"btnCorrelate\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCammino != null : "fx:id=\"btnCammino\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxPorzioni != null : "fx:id=\"boxPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
