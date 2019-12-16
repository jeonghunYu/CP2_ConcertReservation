package audience;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
	
	private int controllerType;
	@FXML AnchorPane seatPane;
	
	String[] selectedConcert;
	@FXML Label leftSeatNum;
	@FXML Label reserveSeatNum;
	
	int totalSeat, reserveSeat;
	
	PrintWriter out;
	BufferedReader in;
	@FXML Label totalSeatNum;
	
	public SeatStatusController() {
	}
	
	public SeatStatusController(int controllerType) {
		this.controllerType = controllerType;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		out = login.LoginController.getOut();
		in = login.LoginController.getIn();
		refresh();
	}
	
	public void refresh() {
		out.println("getAllConcertList");
		out.flush();
		try {
			String concert = in.readLine();
			String[] strConcertList = concert.split("//");
			selectedConcert = strConcertList[ReservationAssistantController.getSelectedConcertIndex()].split("/");
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
	         Parent reserve = FXMLLoader.load(getClass().getResource("/audience/ReservationAssistant.fxml"));
	         Scene scene = new Scene(reserve);
	         Stage primaryStage = (Stage)btnRRM.getScene().getWindow();
	         scene.getStylesheets().add(getClass().getResource("/mainmenu/mainmenu.css").toExternalForm());
	         primaryStage.setScene(scene);
	      }
	      catch (Exception e) {
	         e.printStackTrace();
	      }
	}
	
	@FXML public void moveToCharge() {
	      int choicedSeat = choiceSeat.getToggles().indexOf(choiceSeat.getSelectedToggle());
	      if(choicedSeat >= 0) {
	         out.println("reserveSeat/" + ReservationAssistantController.getSelectedConcertIndex() +"/" + choicedSeat);
	         out.flush();
	         try {
	            String result = in.readLine();
	            if(result.equals("1")) {
	               System.out.println("예약성공");
	               new Alert(Alert.AlertType.CONFIRMATION, "예약성공!", ButtonType.CLOSE).show();
	            } else if(result.equals("-1")) {
	            	new Alert(Alert.AlertType.WARNING, "잔액이 부족합니다.", ButtonType.CLOSE).show();
	            }
	         } catch (IOException e) {
	            e.printStackTrace();
	         }
	      } else {
	         new Alert(Alert.AlertType.WARNING, "예매하실 좌석을 선택해주세요.", ButtonType.CLOSE).show();
	      }
	      refresh();
	   }
}
