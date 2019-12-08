package requestmanager;

import java.net.URL;
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
import javafx.scene.control.Button;

public class RegistrationRequestManagerController implements Initializable {

	@FXML TextField searchField;
	@FXML ListView<String> concertListView;
	@FXML Button btnRequest;
	@FXML Button btnRequestCancel;
	@FXML Button btnCharge;
	@FXML Button btnSeatStatus;
	@FXML ListView dateSelectList;
	@FXML Button btnMain;
	
	private ObservableList<String> concertList;
	private FilteredList<String> filteredList;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		concertList = FXCollections.observableArrayList();
		concertListView.setItems(concertList);
		filteredList = new FilteredList<String>(concertList);
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

	@FXML public void moveToRequest() {
		try {
			Parent request = FXMLLoader.load(getClass().getResource("/requestmanager/RRM_Request.fxml"));
			Scene scene = new Scene(request);
			Stage primaryStage = (Stage)btnRequest.getScene().getWindow();
			primaryStage.setScene(scene);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML public void requestCancel() {
		
	}
	
	@FXML public void moveToCharge() {
		
	}
	
	@FXML public void moveToSeatStatus() {
		
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

}
