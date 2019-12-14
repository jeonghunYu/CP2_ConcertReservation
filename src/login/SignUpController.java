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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class SignUpController {

   @FXML TextField idField;
   @FXML TextField phoneNumField;
   @FXML PasswordField passwordField;
   @FXML TextField nameField;
   @FXML Button btnSignUp;
   @FXML Button btnMoveToLogin;
   @FXML RadioButton audience;
   @FXML ToggleGroup authority;
   @FXML RadioButton eventRegistrant;
   @FXML RadioButton manager;
   
   Socket          socket;
   PrintWriter    out = null;
   BufferedReader    in = null;
   
   int checkSignUp = -1;
   
   
   @FXML public void signUp() {
      try {
         socket = LoginController.getSocket();
         out = LoginController.getOut();
         in = LoginController.getIn();
         
         if(nameField.getText().equals("") || idField.getText().equals("") || passwordField.getText().equals("") || phoneNumField.getText().equals("")) {
            new Alert(Alert.AlertType.WARNING, "빈칸이 있는지 확인한 후 다시 가입해주세요.", ButtonType.CLOSE).show();
         } 
         else {
            if(authority.getSelectedToggle() == null) {
               new Alert(Alert.AlertType.WARNING, "회원님이 만드실 아이디의 권한을 선택해주세요.", ButtonType.CLOSE).show();
            } else if(authority.getSelectedToggle().getUserData().toString().equals("audience")) {
               out.println("addAudience/" + nameField.getText() + "/" + idField.getText() + "/" + passwordField.getText() + "/" + phoneNumField.getText());
               if(in.readLine().equals("-1")) {
                  new Alert(Alert.AlertType.WARNING, "중복된 회원정보 입니다. 다시 한번 확인 부탁드립니다.", ButtonType.CLOSE).show();
               } else {
                  checkSignUp = 1;
                  new Alert(Alert.AlertType.INFORMATION, "회원 가입을 축하드립니다!", ButtonType.CLOSE).show();
               }
            } else if(authority.getSelectedToggle().getUserData().toString().equals("eventRegistrant")) {
               out.println("addEventRegistrant/" + nameField.getText() + "/" + idField.getText() + "/" + passwordField.getText() + "/" + phoneNumField.getText());
               if(in.readLine().equals("-1")) {
                  new Alert(Alert.AlertType.WARNING, "중복된 회원정보 입니다. 다시 한번 확인 부탁드립니다.", ButtonType.CLOSE).show();
               } else {
                  checkSignUp = 1;
                  System.out.println("success");
                  new Alert(Alert.AlertType.INFORMATION, "행사등록자 가입을 축하드립니다!", ButtonType.CLOSE).show();
               }
            } else {
               out.println("addManager/" + nameField.getText() + "/" + idField.getText() + "/" + passwordField.getText() + "/" + phoneNumField.getText());
               if(in.readLine().equals("-1")) {
                  new Alert(Alert.AlertType.WARNING, "중복된 회원정보 입니다. 다시 한번 확인 부탁드립니다.", ButtonType.CLOSE).show();
               } else {
                  checkSignUp = 1;
                  new Alert(Alert.AlertType.INFORMATION, "관리자 가입을 축하드립니다!", ButtonType.CLOSE).show();
               }
            }
            
            if (checkSignUp == 1) {
               try {
                  Parent main = FXMLLoader.load(getClass().getResource("Login.fxml"));
                  Scene scene = new Scene(main);
                  Stage primaryStage = (Stage)btnSignUp.getScene().getWindow();
                  scene.getStylesheets().add(getClass().getResource("/mainmenu/mainmenu.css").toExternalForm());
                  primaryStage.setScene(scene);
               }
               catch (Exception e) {
                  e.printStackTrace();
               }
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
         scene.getStylesheets().add(getClass().getResource("/mainmenu/mainmenu.css").toExternalForm());
         Stage primaryStage = (Stage)btnMoveToLogin.getScene().getWindow();
         primaryStage.setScene(scene);
      }
      catch (Exception e) {
         e.printStackTrace();
      }
   }
}