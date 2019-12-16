package mainmenu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import login.LoginController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;

public class MainMenuController implements Initializable {

   @FXML Label idLabel;
   @FXML Label nameLabel;
   @FXML Label titleLabel;
   @FXML Label authorityLabel;
   @FXML ListView<String> randomConcertView;
   @FXML Label phoneNumLabel;
   @FXML Button btnReserving;
   @FXML Button btnEditing;
   @FXML Button btnLogOut;
   @FXML Button btnMoveToAddConcert;

   Socket          socket;
   PrintWriter    out = null;
   BufferedReader    in = null;
   
   String       inputLine;
   String[]    command;
   
   ObservableList<String> randomConcertList;
@FXML Label balanceLabel;
@FXML Button btnCashManagement;
   
   
   @Override
   public void initialize(URL arg0, ResourceBundle arg1) {
	  out = login.LoginController.getOut();
	  in = login.LoginController.getIn();
	  
	  randomConcertList = FXCollections.observableArrayList();
	  
      idLabel.setText(LoginController.getId());
      nameLabel.setText(LoginController.getName());
      authorityLabel.setText(LoginController.getType());
      phoneNumLabel.setText(LoginController.getContact());
      out.println("getBalance");
      try {
    	  int balance = Integer.parseInt(in.readLine());
          balanceLabel.setText(balance + "");
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
      
      if(LoginController.getType().equals("Audience")) {
         btnEditing.setDisable(true);
         btnMoveToAddConcert.setDisable(true);
      }
      else if(LoginController.getType().equals("EventRegistrant")) {
         btnReserving.setDisable(true);
         btnEditing.setDisable(true);
      }
      else if(LoginController.getType().equals("ServerManager")) {
         btnReserving.setDisable(true);
         btnMoveToAddConcert.setDisable(true);
      }
      
      randomConcertView.setItems(randomConcertList);
      
      out.println("getAllConcertList");
      out.flush();
      String[] strConcertList = null;
		try {
			String concert = in.readLine();
			strConcertList = concert.split("//");
			System.out.println("concert : " + concert);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(!strConcertList[0].equals("")) {
			for(int i = 0; i < strConcertList.length; i++) {
				String[] concert = strConcertList[i].split("/");
				randomConcertList.add(concert[2] + " " + concert[0] + " " + concert[4] + "/" + concert[1]);
			}
		}
   }
   
   @FXML public void reserveAction() {
      try {
         Parent reserve = FXMLLoader.load(getClass().getResource("/audience/ReservationAssistant.fxml"));
         Scene scene = new Scene(reserve);
         Stage primaryStage = (Stage)btnEditing.getScene().getWindow();
         scene.getStylesheets().add(getClass().getResource("/mainmenu/mainmenu.css").toExternalForm());
         primaryStage.setScene(scene);
      }
      catch (Exception e) {
         e.printStackTrace();
      }
   }

   @FXML public void addConcertAction() {
      try {
         Parent reserve = FXMLLoader.load(getClass().getResource("/eventRegistrant/RegistrationRequestManager.fxml"));
         Scene scene = new Scene(reserve);
         Stage primaryStage = (Stage)btnMoveToAddConcert.getScene().getWindow();
         scene.getStylesheets().add(getClass().getResource("/mainmenu/mainmenu.css").toExternalForm());
         primaryStage.setScene(scene);
      }
      catch (Exception e) {
         e.printStackTrace();
      }
   }

   @FXML public void editScheduleAction() {
      try {
         Parent edit = FXMLLoader.load(getClass().getResource("/manager/ConcertHallManager.fxml"));
          Scene scene = new Scene(edit);
          Stage primaryStage = (Stage)btnEditing.getScene().getWindow();
          scene.getStylesheets().add(getClass().getResource("/mainmenu/mainmenu.css").toExternalForm());
          primaryStage.setScene(scene);
      }
      catch (Exception e) {
         e.printStackTrace();
      }
   }
   
   @FXML public void moveToLogin() {
      try {
         out = login.LoginController.getOut();
         out.println("logout");
         out.flush();
         Parent login = FXMLLoader.load(getClass().getResource("/login/Login.fxml"));
          Scene scene = new Scene(login);
          Stage primaryStage = (Stage)btnLogOut.getScene().getWindow();
          scene.getStylesheets().add(getClass().getResource("/mainmenu/mainmenu.css").toExternalForm());
          primaryStage.setScene(scene);
      }
      catch (Exception e) {
         e.printStackTrace();
      }
      
   }

	@FXML public void manageCash() {
		try {
	         Parent cash = FXMLLoader.load(getClass().getResource("/mainmenu/CashManagement.fxml"));
	         Scene scene = new Scene(cash);
	         Stage primaryStage = (Stage)btnCashManagement.getScene().getWindow();
	         scene.getStylesheets().add(getClass().getResource("/mainmenu/mainmenu.css").toExternalForm());
	         primaryStage.setScene(scene);
	      }
	      catch (Exception e) {
	         e.printStackTrace();
	      }
	}
}