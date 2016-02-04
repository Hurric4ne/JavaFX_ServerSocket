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

public class GreetingClient extends Application {
	
	@FXML						//Änderung der Schriftart auf Courier New,
	private TextArea text_area;	//um Probemen mit der Zeichengröße zu entgehen
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
	void action_about_close(ActionEvent event) {	//Schließen des Info-Fensters
		Stage stage = (Stage) btn_info_close.getScene().getWindow();
		stage.close();
	}

	@FXML
	void action_connect(ActionEvent event) {
		try{
			String serverName = "127.0.0.2";	//IP-Adresse 127.0.0.2
			int port = 9001;					//mit Portnummer 9001
			System.out.println("Connecting to " + serverName + " on port " + port);
			Socket client = new Socket(serverName, port);
			if (event.getSource() == btn_connect) {
				try {
					BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
					String line = "";
					String text = "";
					while ((line = in.readLine()) != null) {
						text = text + line + "\n" ;		//Text vom Server zeilenweise lesen
					}									//und dann auf das Textfeld schreiben
					text_area.setText(text);
					client.close();						//Schließen der Verbindung
				} catch (IOException e) {
					text_area.setStyle("-fx-text-fill: red");
					text_area.setText("IOException!");
					e.printStackTrace();
				}
			}
		} catch (UnknownHostException uh) {
			text_area.setStyle("-fx-text-fill: red");
			text_area.setText("Unbekannter Host!");
			uh.printStackTrace();
		} catch (IOException io) {
			text_area.setStyle("-fx-text-fill: red");
			text_area.setText("Verbindung nicht möglich!");
			io.printStackTrace();
		}
	}

	@FXML
	void action_close(ActionEvent event) {	//Schließen des Client-Fensters
		System.exit(0);
	}

	@FXML
	void action_info(ActionEvent event) {	//Öffnen des Info-Fensters
		Stage info = new Stage();			//und lesen der Info-FXML-Datei
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

	public void start(Stage stage) throws Exception {	//Laden der Standart FXML-Datei
		Parent parent = FXMLLoader.load(getClass().getResource("GUI_Client.fxml"));
		stage.setScene(new Scene(parent));
		stage.show();
	}
}