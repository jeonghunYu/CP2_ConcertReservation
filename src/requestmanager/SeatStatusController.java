package requestmanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Label;

public class SeatStatusController implements Initializable {

	@FXML ToggleGroup choiceSeat;
	@FXML Button btnReserveStart;
	@FXML Button btnReserveEnd;
	@FXML Button btnRRM;
	@FXML Button btnReserve;
	
	private static int controllerType;
	@FXML AnchorPane seatPane;
	
	String[] selectedConcert;
	@FXML Label leftSeatNum;
	@FXML Label reserveSeatNum;
	
	int totalSeat, reserveSeat;
	
	PrintWriter out;
	BufferedReader in;
	@FXML Label totalSeatNum;
	
	public static void setControllerType(int type) {
		controllerType = type;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		out = login.LoginController.getOut();
		in = login.LoginController.getIn();
		refresh();
	}
	
	public void refresh() {
		out.println("getAllConcertList");
		try {
			String concert = in.readLine();
			String[] strConcertList = concert.split("//");
			selectedConcert = strConcertList[RegistrationRequestManagerController.getSelectedConcertIndex()].split("/");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		totalSeat = Integer.parseInt(selectedConcert[1]);
		reserveSeat = 0;
		totalSeatNum.setText(totalSeat + "");
		
		int[] seats = Arrays.stream(selectedConcert[3].replace("[", "").replace("]", "").split(", ")).mapToInt(Integer::parseInt).toArray();;
		for(int i = 0; i < seats.length; i++) {
			ToggleButton btn = (ToggleButton) choiceSeat.getToggles().get(i);
			if(seats[i] != 0) {
				btn.setDisable(true);
				reserveSeat++;
			} else {
				btn.setDisable(false);
			}
		}
		reserveSeatNum.setText(reserveSeat + "");
		leftSeatNum.setText(totalSeat - reserveSeat + "");
	}
	
	@FXML public void moveToRRM() {
		try {
			Parent reserve = null;
			if(controllerType == 1) {
				reserve = FXMLLoader.load(getClass().getResource("/hallmanager/ConcertHallManager.fxml"));
			} else {
				reserve = FXMLLoader.load(getClass().getResource("/requestmanager/RegistrationRequestManager.fxml"));
			}
			Scene scene = new Scene(reserve);
			Stage primaryStage = (Stage)btnRRM.getScene().getWindow();
			primaryStage.setScene(scene);
	      }
	      catch (Exception e) {
	    	  e.printStackTrace();
	      }
	}

	@FXML public void refreshStatus() {
		refresh();
	}
}
