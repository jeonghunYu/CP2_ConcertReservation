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
	
//	TODO ��������� logout �۵��ϵ��� �ϴ� ��� ���庸��.
	@FXML public void signUp() {
		try {
			socket = LoginController.getSocket();
			out = LoginController.getOut();
			in = LoginController.getIn();

			if(authority.getSelectedToggle() == null) {
//				TODO ���� â
			} else if(authority.getSelectedToggle().getUserData().toString().equals("audience")) {
				out.println("addAudience/" + nameField.getText() + "/" + idField.getText() + "/" + passwordField.getText() + "/" + phoneNumField.getText());
				if(in.readLine().equals("-1")) {
//					TODO ȸ������ ���� �޽���
				} else {
					checkSignUp = 1;
//					TODO ȸ������ ���� �޽���
				}
			} else if(authority.getSelectedToggle().getUserData().toString().equals("eventRegistrant")) {
				out.println("addEventRegistrant/" + nameField.getText() + "/" + idField.getText() + "/" + passwordField.getText() + "/" + phoneNumField.getText());
				if(in.readLine().equals("-1")) {
//					TODO ȸ������ ���� �޽��� Platform.run���ñ�
				} else {
					checkSignUp = 1;
					System.out.println("success");
//					TODO ȸ������ ���� �޽���
				}
			} else {
				out.println("addManager/" + nameField.getText() + "/" + idField.getText() + "/" + passwordField.getText() + "/" + phoneNumField.getText());
				if(in.readLine().equals("-1")) {
//					TODO ȸ������ ���� �޽��� Platform.run���ñ�
				} else {
					checkSignUp = 1;
//					TODO ȸ������ ���� �޽���
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
