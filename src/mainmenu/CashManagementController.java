package mainmenu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Label;

public class CashManagementController implements Initializable {

	@FXML Button btnMain;
	@FXML TextField chargeField;
	@FXML TextField refundField;
	@FXML Label balanceLabel;
	@FXML Label returnCodeField;
	
	PrintWriter out = null;
	BufferedReader in = null;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		out = login.LoginController.getOut();
		in = login.LoginController.getIn();
		
		out.println("getBalance");
	      try {
	    	  int balance = Integer.parseInt(in.readLine());
	          balanceLabel.setText(balance + "");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	@FXML public void moveToMain() {
		try {
			Parent main = FXMLLoader.load(getClass().getResource("/mainmenu/MainMenu.fxml"));
			Scene scene = new Scene(main);
			Stage primaryStage = (Stage)btnMain.getScene().getWindow();
			scene.getStylesheets().add(getClass().getResource("/mainmenu/mainmenu.css").toExternalForm());
			primaryStage.setScene(scene);
			 
//			StackPane root = (StackPane)btnReserving.getScene().getRoot();
//		    root.getChildren().add(reserve);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	@FXML public void chargeMoney() {
		if(chargeField.getText().contains("tkdvnarnjs")) {
			int amount = Integer.parseInt(chargeField.getText().split("tkdvnarnjs")[0]);
			out.println("chargeMoney/" + amount);
			out.flush();
			try {
				int result = Integer.parseInt(in.readLine());
				if(result == -1) {
//					오류 메시지 출력
				} else {
					balanceLabel.setText(result + "원");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	@FXML public void refundMoney() {
		if(refundField.getText().equals("")) {
			
		} else {
			for(int i = 0; i < refundField.getText().length(); i++) {
				if (refundField.getText().charAt(i) < '0' || refundField.getText().charAt(i) > '9') {
					new Alert(Alert.AlertType.WARNING, "숫자만 입력해주세요.", ButtonType.CLOSE).show();
					return;
				}
			}
			int amount = Integer.parseInt(refundField.getText());
			out.println("refundMoney/" + amount);
			out.flush();
			try {
				int result = Integer.parseInt(in.readLine());
				if(result == -1) {
					new Alert(Alert.AlertType.WARNING, "잔액을 초과하여 환불할 수 없습니다.", ButtonType.CLOSE).show();
				} else {
					balanceLabel.setText(result + "원");
					returnCodeField.setText(amount + "tkdvnarnjs");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
