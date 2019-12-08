package hallmanager;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;

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

public class ManagerController implements Initializable {

	@FXML TextField searchField;
	@FXML ListView<String> concertHallList;
	
	@FXML Button cancelConcertAction;
	@FXML Button btnMain;
	@FXML Button btnRequestList;
	@FXML Button btnShowHallSeats;
	
	private ObservableList<String> concertList;
	
	private FilteredList<String> filteredList; 

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		concertList = FXCollections.observableArrayList();
		concertHallList.setItems(concertList);
		filteredList = new FilteredList<String>(concertList);
	}

	//	TODO 검색 예외처리
	@FXML public void searchAction() {
		filteredList.setPredicate(new Predicate<String>() {

			@Override
			public boolean test(String t) {
				if(t.contains(searchField.getText())) {
					return true;
				}
				return false;
			}
		});
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

	@FXML public void moveToRequsetList() {
		try {
			Parent request = FXMLLoader.load(getClass().getResource("RequestRegisterCancel.fxml"));
			Scene scene = new Scene(request);
			Stage primaryStage = (Stage)btnRequestList.getScene().getWindow();
			scene.getStylesheets().add(getClass().getResource("hallmanager.css").toExternalForm());
			primaryStage.setScene(scene);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML public void showHallSeats() {
		try {
			int selectedIndex = concertHallList.getSelectionModel().getSelectedIndex();
			if(selectedIndex < 0) {
				new Alert(Alert.AlertType.WARNING, "확인하실 콘서트를 선택하세요.", ButtonType.CLOSE).show();
				return;
			}
//			TODO if,else if조건문에서 selectedIndex를 다 바꿔줘야함 다른 걸로 구분해아함
			if(selectedIndex == 1) {
				Parent hall1 = FXMLLoader.load(getClass().getResource("reservationAssistant/RA_SeatStatus1.fxml"));
				Scene scene = new Scene(hall1);
				Stage primaryStage = (Stage)btnShowHallSeats.getScene().getWindow();
//				TODO css변경 : scene.getStylesheets().add(getClass().getResource("hallmanager.css").toExternalForm());
				primaryStage.setScene(scene);
			}
			else if(selectedIndex == 2) {
				Parent hall2 = FXMLLoader.load(getClass().getResource("reservationAssistant/RA_SeatStatus2.fxml"));
				Scene scene = new Scene(hall2);
				Stage primaryStage = (Stage)btnShowHallSeats.getScene().getWindow();
//				TODO css변경 : scene.getStylesheets().add(getClass().getResource("hallmanager.css").toExternalForm());
				primaryStage.setScene(scene);
			}
			else if(selectedIndex == 3) {
				Parent hall3 = FXMLLoader.load(getClass().getResource("reservationAssistant/RA_SeatStatus3.fxml"));
				Scene scene = new Scene(hall3);
				Stage primaryStage = (Stage)btnShowHallSeats.getScene().getWindow();
//				TODO css변경 : scene.getStylesheets().add(getClass().getResource("hallmanager.css").toExternalForm());
				primaryStage.setScene(scene);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML public void cancelConcert() {
//		TODO
		int selectedIndex = concertHallList.getSelectionModel().getSelectedIndex();
		if(selectedIndex < 0) {
			new Alert(Alert.AlertType.WARNING, "삭제할 콘서트를 선택하세요.", ButtonType.CLOSE).show();
			return;
		}
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "삭제하시겠습니까?", ButtonType.OK, ButtonType.CANCEL);
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK) {
			concertList.remove(selectedIndex);
		}
	}

	
}
