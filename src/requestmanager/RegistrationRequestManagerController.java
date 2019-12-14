package requestmanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.Arrays;
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
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class RegistrationRequestManagerController implements Initializable {

	@FXML TextField searchField;
	@FXML ListView<String> concertListView;
	@FXML Button btnRequest;
	@FXML Button btnRequestCancel;
	@FXML Button btnCharge;
	@FXML Button btnSeatStatus;
	@FXML Button btnMain;
	
	Socket socket;
	PrintWriter out;
	BufferedReader in;
	
	static String[] strConcertList;
	
	private static ObservableList<String> concertList;
	private static FilteredList<String> filteredList;
	
	private static int selectedConcertIndex;
	
	@FXML TextArea selectedConcertInfo;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		out = login.LoginController.getOut();
		in = login.LoginController.getIn();
		
		refresh();
	}
	
	public void refresh() {
		out.println("getRegisteredConcertList");
		try {
			String concert = in.readLine();
			strConcertList = concert.split("//");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!strConcertList[0].equals("1")) {
			for(int i = 0; i < strConcertList.length; i++) {
				String[] concert = strConcertList[i].split("/");
				Platform.runLater(() -> concertList.add(concert[0]));
			}
		}
		concertList = FXCollections.observableArrayList();
		concertListView.setItems(concertList);
		filteredList = new FilteredList<String>(concertList);
		
		concertListView.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
//				TODO
				String[] concert = strConcertList[(int) newValue].split("/");
				selectedConcertInfo.setText("Title : " + concert[0] + "\nNumberOfSeat : " + concert[1]
						+ "\nDate : " + concert[2]);
			}

		});
	}
	@FXML public void searchAction() {
		refresh();
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

	@FXML public void moveToRequest() {
		try {
			Parent request = FXMLLoader.load(getClass().getResource("/requestmanager/RRM_Request.fxml"));
			Scene scene = new Scene(request);
			Stage primaryStage = (Stage)btnRequest.getScene().getWindow();
			scene.getStylesheets().add(getClass().getResource("/mainmenu/mainmenu.css").toExternalForm());
			primaryStage.setScene(scene);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML public void requestCancel() {
		System.out.println("cancelRequest/" + concertListView.getSelectionModel().getSelectedIndex());
		out.println("cancelRequest/" + concertListView.getSelectionModel().getSelectedIndex());
	}
	
	@FXML public void moveToSeatStatus() {
		try {
			selectedConcertIndex = concertListView.getSelectionModel().getSelectedIndex();
			String[] selectedConcert = strConcertList[selectedConcertIndex].split("/");
			Parent status = null;
			for(int i = 0; i < RRM_RequestController.getTotalSeatNum().length; i++) {
				if(Integer.parseInt(RRM_RequestController.getTotalSeatNum()[i]) == Integer.parseInt(selectedConcert[1])) {
					status = FXMLLoader.load(getClass().getResource("/requestmanager/SeatStatus" + i + ".fxml"));
					break;
				}
			}
			Scene scene = new Scene(status);
			Stage primaryStage = (Stage)btnSeatStatus.getScene().getWindow();
			scene.getStylesheets().add(getClass().getResource("/mainmenu/mainmenu.css").toExternalForm());
			primaryStage.setScene(scene);
			requestmanager.SeatStatusController.setControllerType(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
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

	@FXML public void refreshStatus() {
		refresh();
	}

}
