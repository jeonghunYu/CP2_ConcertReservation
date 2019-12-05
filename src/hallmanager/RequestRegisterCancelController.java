package hallmanager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class RequestRegisterCancelController {

	@FXML ListView concertRequestList;
	@FXML Button btnHallManager;

	@FXML public void acceptRequestAction() {}

	@FXML public void moveToHallManager() {
		try {
			Parent main = FXMLLoader.load(getClass().getResource("ConcertHallManager.fxml"));
			Scene scene = new Scene(main);
			Stage primaryStage = (Stage)btnHallManager.getScene().getWindow();
			scene.getStylesheets().add(getClass().getResource("hallmanager.css").toExternalForm());
			primaryStage.setScene(scene);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
