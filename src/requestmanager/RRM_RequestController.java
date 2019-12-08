package requestmanager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class RRM_RequestController {

	@FXML Label totalSeat;
	@FXML TextField concertNameField;
	@FXML ImageView seatView;
	@FXML Button btnRRM;
	
	private Image[] imageRes = {
			new Image(getClass().getResource("images/concertHall1.jpg").toString()),
			new Image(getClass().getResource("images/concertHall2.jpg").toString()),
			new Image(getClass().getResource("images/concertHall3.jpg").toString()),
	};
	private String[] totalSeatNum = {"168", "138", "128"};
	
	private int index;
	
	@FXML public void previousAction() {
		if (index == 0) {
			index = imageRes.length;
		}
		seatView.setImage(imageRes[--index]);
		totalSeat.setText(totalSeatNum[index]);
	}

	@FXML public void nextAction() {
		if (index == imageRes.length - 1) {
			index = -1;
		}
		seatView.setImage(imageRes[++index]);
		totalSeat.setText(totalSeatNum[index]);
	}

	public int getIndex() {
		return index;
	}
	
	@FXML public void requestAction() {

	}

	@FXML public void moveToRRM() {
		try {
			Parent main = FXMLLoader.load(getClass().getResource("/requestmanager/RegistrationRequestManager.fxml"));
			Scene scene = new Scene(main);
			Stage primaryStage = (Stage)btnRRM.getScene().getWindow();
			scene.getStylesheets().add(getClass().getResource("/requestmanager/requestmanager.css").toExternalForm());
			primaryStage.setScene(scene);
			 
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
