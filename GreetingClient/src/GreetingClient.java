// File Name GreetingClient.java

import java.io.*;
import java.net.*;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class GreetingClient extends Application{
	@FXML
    private TextArea text_area;
    @FXML
    private MenuItem btn_info;
    @FXML
    private Button btn_connect;
    @FXML
    private MenuItem btn_close;
    @FXML
    private Button btn_info_close;
    @FXML
    private TextArea info_area;
    @FXML
    void action_about_close(ActionEvent event) {
    	Stage stage = (Stage) btn_info_close.getScene().getWindow();
    	stage.close();
    }
    @FXML
    void action_connect(ActionEvent event) {
    	if(event.getSource() == btn_connect){
	    	String serverName = "127.0.0.2";
			int port = 9000;
			try {
				System.out.println("Connecting to " + serverName + " on port " + port);
				Socket client = new Socket(serverName, port);
				BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				String line = "";
				String text = "";
				while ((line = in.readLine()) != null) {
					text = text+"\n"+line;
				}
				text_area.setText(text);
				System.out.println(text); //debug
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    }
    @FXML
    void action_close(ActionEvent event) {
    	
		System.exit(0);
    }
    @FXML
    void action_info(ActionEvent event) {
    	
    	Stage info = new Stage();
    	try {
			javafx.scene.Parent root = FXMLLoader.load(getClass().getResource("info.fxml"));
			info.setTitle("Info");
			info.setScene(new Scene(root));
			info.show();
		} catch (IOException e) {
			System.err.println("Keine Datei!");
			e.printStackTrace();
		}
		
    }
	 
	public static void main(String[] args) {
        launch(args);
    }
    
    public void start(Stage stage) throws Exception {
		Parent parent = FXMLLoader.load(getClass().getResource("GUI_Client.fxml"));
         stage.setScene(new Scene(parent));
         stage.show();
    }
}