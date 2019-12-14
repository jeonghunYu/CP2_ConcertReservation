package reservationmanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.ListView;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;

public class ReservationAssistantController implements Initializable {

	@FXML TextField searchField;
	@FXML ListView<String> concertListView;
	@FXML Button btnReservation;
	@FXML Button btnMain;
	
	PrintWriter out;
	BufferedReader in;

	private static int selectedConcertIndex;
	private ObservableList<String> concertList, reservedList;
	private FilteredList<String> filteredList;
	
	static String[] strConcertList, strReservedList;
	@FXML ListView<String> reservedConcertListView;
	@FXML TextArea concertInfoTextArea;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		out = login.LoginController.getOut();
		in = login.LoginController.getIn();

		concertList = FXCollections.observableArrayList();
		reservedList = FXCollections.observableArrayList();
		concertListView.setItems(concertList);
		reservedConcertListView.setItems(reservedList);
		filteredList = new FilteredList<String>(concertList);
		
//		서버측 좌석현황 업데이트 끝나면 업데이트 하기.
		out.println("getAllConcertList");
		try {
			String concert = in.readLine();
			strConcertList = concert.split("//");
			System.out.println(concert);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(!strConcertList[0].equals("")) {
			for(int i = 0; i < strConcertList.length; i++) {
				String[] concert = strConcertList[i].split("/");
				Platform.runLater(() -> concertList.add(concert[0]));
			}
		}
		
		out.println("getReservedConcertList");
		try {
			String concert = in.readLine();
			strReservedList = concert.split("//");
			System.out.println(concert);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!strReservedList[0].equals("")) {
			for(int i = 0; i < strReservedList.length; i++) {
				String[] concert = strReservedList[i].split("/");
				Platform.runLater(() -> reservedList.add(concert[0] + " " + concert[3] + "번 좌석"));
			}
		}
		concertListView.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
//				TODO
				String[] concert = strConcertList[(int) newValue].split("/");
				concertInfoTextArea.setText("Title : " + concert[0] + "\nNumberOfSeat : " + concert[1]
						+ "\nDate : " + concert[2]);
			}

		});
		
		reservedConcertListView.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
//				TODO
				String[] concert = strReservedList[(int) newValue].split("/");
				concertInfoTextArea.setText("Title : " + concert[0] + "\nNumberOfSeat : " + concert[1]
						+ "\nDate : " + concert[2]);
			}

		});
	}
	
	@FXML public void searchAction() {
		filteredList.setPredicate(new Predicate<String>() {
			public boolean test(String t) {
				if(t.contains(searchField.getText())) {
					return true;
				}
				return false;
			}
		});
		concertListView.setItems(filteredList);
	}
	
	@FXML public void moveToSeatStatus() {
		try {
				Parent seat = null;
				if((selectedConcertIndex = concertListView.getSelectionModel().getSelectedIndex()) >= 0) {
					String[] selectedConcert = strConcertList[selectedConcertIndex].split("/");
					for(int i = 0; i < requestmanager.RRM_RequestController.getTotalSeatNum().length; i++) {
						if(requestmanager.RRM_RequestController.getTotalSeatNum()[i].equals(selectedConcert[1])) {
							seat = FXMLLoader.load(getClass().getResource("/reservationmanager/SeatStatus" + i + ".fxml"));
							break;
						}
					}
					Scene scene = new Scene(seat);
					Stage primaryStage = (Stage)btnReservation.getScene().getWindow();
					scene.getStylesheets().add(getClass().getResource("/mainmenu/mainmenu.css").toExternalForm());
					primaryStage.setScene(scene);
				} else {
					new Alert(Alert.AlertType.WARNING, "예매하실 콘서트를 선택해주세요.", ButtonType.CLOSE).show();
				}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML public void reservationCancelAction() {
		
	}

	@FXML public void moveToMain() {
		try {
			Parent main = FXMLLoader.load(getClass().getResource("/mainmenu/MainMenu.fxml"));
			Scene scene = new Scene(main);
			Stage primaryStage = (Stage)btnMain.getScene().getWindow();
			scene.getStylesheets().add(getClass().getResource("/mainmenu/mainmenu.css").toExternalForm());
			primaryStage.setScene(scene);
			 
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int getSelectedConcertIndex() {
		return selectedConcertIndex;
	}

}
