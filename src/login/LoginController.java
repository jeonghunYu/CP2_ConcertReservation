package login;

import java.io.BufferedReader;
import java.io.InputStreamReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
//import java.net.URL;
//import java.net.UnknownHostException;
//import java.util.ResourceBundle;
import java.util.Arrays;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class LoginController {

	@FXML TextField idfield;
	@FXML PasswordField pwfield;
	@FXML Button btnLogin;
	@FXML Button btnSignUp;
	
	Socket 			socket; // TODO 소켓을 한 번 생성해서 계속 인자로 넘겨주며 사용할지, 클래스마다 새로운 소켓을 만들어서 사용할지 질문하기
	PrintWriter 	out = null;
	BufferedReader 	in = null;
	
	String 		inputLine;
	String[] 	command;
	
	static String name, id, contact, type;
	
//	@Override
//	public void initialize(URL arg0, ResourceBundle arg1) {
//		try {
//			socket = new Socket("cs-cnu.tk", 50000);
//			out = new PrintWriter(socket.getOutputStream(), true);
//			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//		} catch (UnknownHostException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//	}
	
	@FXML public void moveToMainMenu() {
		try {
			socket = Main.getSocket();
			out = Main.getOut();
			in = Main.getIn();
			
//			TODO 로그인 검증 (null 값 입력 안되게)
			out.println("login/" + idfield.getText() + "/" + pwfield.getText());
			
			inputLine = in.readLine();
			if(inputLine.equals("-1")) {
//				TODO 로그인 실패 경고
			} else {
				command = inputLine.split("/");
				name = command[0];
				id = command[1];
				contact = command[2];
				type = command[3];
			}
			Parent main = FXMLLoader.load(getClass().getResource("/mainmenu/MainMenu.fxml"));
			Scene scene = new Scene(main);
			Stage primaryStage = (Stage)btnLogin.getScene().getWindow();
			//scene.getStylesheets().add(getClass().getResource("/mainmenu/mainmenu.css").toExternalForm());
			primaryStage.setScene(scene);

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

}
