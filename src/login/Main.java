package login;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	static Socket 			socket;
	static PrintWriter 		out;
	static BufferedReader 	in;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			socket = new Socket("cs-cnu.tk", 50000);
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setTitle("콘서트 예매 프로그램");
			// scene.getStylesheets().add(getClass().getResource("hallmanager.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public static Socket getSocket() {
		return socket;
	}
	public static PrintWriter getOut() {
		return out;
	}
	public static BufferedReader getIn() {
		return in;
	}
}
