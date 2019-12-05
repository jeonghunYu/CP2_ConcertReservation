package hallmanager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.ListView;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class ManagerController {

	@FXML TextField searchField;
	@FXML ListView concertHallList;
	@FXML Button addNewConcertAction;
	@FXML Button cancelConcertAction;
	@FXML Button btnMain;
	@FXML Button btnRequestList;

	@FXML public void searchAction() {}

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
			 
//			StackPane root = (StackPane)btnReserving.getScene().getRoot();
//		    root.getChildren().add(reserve);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
