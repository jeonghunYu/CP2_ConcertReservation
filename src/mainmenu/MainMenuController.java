package mainmenu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;

public class MainMenuController {

	@FXML Label idLabel;
	@FXML Label nameLabel;
	@FXML Label titleLabel;
	@FXML Label authorityLabel;
	@FXML TableView randomConcertView;
	@FXML Label phoneNumLabel;
	@FXML Button btnReserving;
	@FXML Button btnEditing;

	@FXML public void reserveAction() {
		try {
			Parent reserve = FXMLLoader.load(getClass().getResource("/reservationAssistant/ReservationAssistant.fxml"));
		    StackPane root = (StackPane)btnReserving.getScene().getRoot();
		    root.getChildren().add(reserve);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML public void addConcertAction() {}

	@FXML public void editScheduleAction() {
		try {
			Parent edit = FXMLLoader.load(getClass().getResource("/hallmanager/ConcertHallManager.fxml"));
		    Scene scene = new Scene(edit);
		    Stage primaryStage = (Stage)btnEditing.getScene().getWindow();
		    primaryStage.setScene(scene);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML public void showDate() {}

	@FXML public void moveToLogin() {}

}
