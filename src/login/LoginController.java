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
	
	static Socket 			socket; // TODO ������ �� �� �����ؼ� ��� ���ڷ� �Ѱ��ָ� �������, Ŭ�������� ���ο� ������ ���� ������� �����ϱ�
	static PrintWriter 	out = null;
	static BufferedReader 	in = null;
	
	String 		inputLine;
	String[] 	command;
	
	static String name, id, contact, type;
	static int balance;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			socket = new Socket("localhost", 50000);
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
		if(idfield.getText().equals("") || pwfield.getText().equals("")) {
	         new Alert(Alert.AlertType.WARNING, "아이디와 패스워드를 입력해주세요.", ButtonType.CLOSE).show();
	      }
		try {
			socket = LoginController.getSocket();
			out = LoginController.getOut();
			in = LoginController.getIn();
			
//			TODO �α��� ���� (null �� �Է� �ȵǰ�)
			out.println("login/" + idfield.getText() + "/" + pwfield.getText());
			out.flush();
			
			inputLine = in.readLine();
			if(inputLine.equals("-1")) {
				System.out.println(1);
				new Alert(Alert.AlertType.WARNING, "Ȯ���Ͻ� �ܼ�Ʈ�� �����ϼ���.", ButtonType.CLOSE).show();
			} else {
				command = inputLine.split("/");
				name = command[0];
				id = command[1];
				contact = command[2];
				type = command[3];
				balance = Integer.parseInt(command[4]);
				if(command.length == 6) {
					String[] msgList = command[5].split("=");
					String msg = "";
					for(int i = 0; i < msgList.length; i++) {
						msg += msgList[i] + "\n";
					}
					new Alert(Alert.AlertType.CONFIRMATION, msg, ButtonType.CLOSE).show();
				}
				
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
			scene.getStylesheets().add(getClass().getResource("/mainmenu/mainmenu.css").toExternalForm());
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
	public static void setBalance(int newBalance) {
		balance = newBalance;
	}
	public static int getBalance() {
		return balance;
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
