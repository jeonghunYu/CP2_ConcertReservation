package login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
//import java.net.URL;
//import java.net.UnknownHostException;
//import java.util.ResourceBundle;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
//import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

public class LoginController implements Initializable {

	@FXML TextField idfield;
	@FXML PasswordField pwfield;
	@FXML Button btnLogin;
	@FXML Button btnSignUp;
	
	static Socket 			socket; // TODO 占쏙옙占쏙옙占쏙옙 占쏙옙 占쏙옙 占쏙옙占쏙옙占쌔쇽옙 占쏙옙占� 占쏙옙占쌘뤄옙 占싼곤옙占쌍몌옙 占쏙옙占쏙옙占쏙옙占�, 클占쏙옙占쏙옙占쏙옙占쏙옙 占쏙옙占싸울옙 占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙底� 占쏙옙占쏙옙占쏙옙占� 占쏙옙占쏙옙占싹깍옙
	static PrintWriter 	out = null;
	static BufferedReader 	in = null;
	
	String 		inputLine;
	String[] 	command;
	
	static String name, id, contact, type;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			socket = new Socket("cs-cnu.tk", 50000);
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML public void moveToMainMenu() {
		try {
			socket = LoginController.getSocket();
			out = LoginController.getOut();
			in = LoginController.getIn();
			
//			TODO 占싸깍옙占쏙옙 占쏙옙占쏙옙 (null 占쏙옙 占쌉뤄옙 占싫되곤옙)
			out.println("login/" + idfield.getText() + "/" + pwfield.getText());
			
			inputLine = in.readLine();
			if(inputLine.equals("-1")) {
				System.out.println(1);
				new Alert(Alert.AlertType.WARNING, "확占쏙옙占싹쏙옙 占쌤쇽옙트占쏙옙 占쏙옙占쏙옙占싹쇽옙占쏙옙.", ButtonType.CLOSE).show();
			} else {
				command = inputLine.split("/");
				name = command[0];
				id = command[1];
				contact = command[2];
				type = command[3];
				
				Parent main = FXMLLoader.load(getClass().getResource("/mainmenu/MainMenu.fxml"));
				Scene scene = new Scene(main);
				Stage primaryStage = (Stage)btnLogin.getScene().getWindow();
				scene.getStylesheets().add(getClass().getResource("/mainmenu/mainmenu.css").toExternalForm());
				primaryStage.setScene(scene);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML public void moveToSignUp() {
		try {
			Parent signUp = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
			Scene scene = new Scene(signUp);
			Stage primaryStage = (Stage)btnSignUp.getScene().getWindow();
			//scene.getStylesheets().add(getClass().getResource("/mainmenu/mainmenu.css").toExternalForm());
			primaryStage.setScene(scene);

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getName() {
		return name;
	}
	
	public static String getId() {
		return id;
	}
	
	public static String getContact() {
		return contact;
	}
	
	public static String getType() {
		return type;
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
