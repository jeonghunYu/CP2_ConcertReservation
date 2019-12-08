package login;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class SignUpController {

	@FXML TextField idField;
	@FXML TextField phoneNumField;
	@FXML PasswordField passwordField;
	@FXML TextField nameField;
	@FXML Button btnSignUp;
	@FXML RadioButton audience;
	@FXML ToggleGroup authority;
	@FXML RadioButton eventRegistrant;
	@FXML RadioButton manager;
	
	Socket 			socket;
	PrintWriter 	out = null;
	BufferedReader 	in = null;
	
	int checkSignUp = -1;
	@FXML Button btnMoveToLogin;
	
//	TODO 강제종료시 logout 작동하도록 하는 방법 여쭤보기.
	@FXML public void signUp() {
		try {
			socket = new Socket("cs-cnu.tk", 50000);
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			if(authority.getSelectedToggle() == null) {
//				TODO 오류 창
			} else if(authority.getSelectedToggle().getUserData().toString().equals("audience")) {
				out.println("addAudience/" + nameField.getText() + "/" + idField.getText() + "/" + passwordField.getText() + "/" + phoneNumField.getText());
				if(in.readLine().equals("-1")) {
//					TODO 회원가입 오류 메시지
				} else {
					checkSignUp = 1;
//					TODO 회원가입 성공 메시지
				}
			} else if(authority.getSelectedToggle().getUserData().toString().equals("eventRegistrant")) {
				out.println("addEventRegistrant/" + nameField.getText() + "/" + idField.getText() + "/" + passwordField.getText() + "/" + phoneNumField.getText());
				if(in.readLine().equals("-1")) {
//					TODO 회원가입 오류 메시지 Platform.run뭐시기
				} else {
					checkSignUp = 1;
					System.out.println("success");
//					TODO 회원가입 성공 메시지
				}
			} else {
				out.println("addManager/" + nameField.getText() + "/" + idField.getText() + "/" + passwordField.getText() + "/" + phoneNumField.getText());
				if(in.readLine().equals("-1")) {
//					TODO 회원가입 오류 메시지 Platform.run뭐시기
				} else {
					checkSignUp = 1;
//					TODO 회원가입 성공 메시지
				}
			}
			
			if (checkSignUp == 1) {
				try {
					Parent main = FXMLLoader.load(getClass().getResource("Login.fxml"));
					Scene scene = new Scene(main);
					Stage primaryStage = (Stage)btnSignUp.getScene().getWindow();
					//scene.getStylesheets().add(getClass().getResource("/mainmenu/mainmenu.css").toExternalForm());
					primaryStage.setScene(scene);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			socket.close();
			out.close();
			in.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML public void moveToLogin() {
		try {
			Parent login = FXMLLoader.load(getClass().getResource("Login.fxml"));
			Scene scene = new Scene(login);
			Stage primaryStage = (Stage)btnMoveToLogin.getScene().getWindow();
			//scene.getStylesheets().add(getClass().getResource("/mainmenu/mainmenu.css").toExternalForm());
			primaryStage.setScene(scene);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
