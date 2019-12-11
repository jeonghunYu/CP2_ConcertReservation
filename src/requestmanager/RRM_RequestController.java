package requestmanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;
import login.Main;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;

public class RRM_RequestController implements Initializable {

	@FXML Label totalSeat;
	@FXML TextField concertNameField;
	@FXML ImageView seatView;
	@FXML Button btnRRM;
	@FXML DatePicker concertDate;
	
	Socket 			socket;
	PrintWriter 	out = null;
	BufferedReader 	in = null;
	
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
	
	@FXML public void requestAction() throws UnknownHostException, IOException {
		socket = login.Main.getSocket();
		out = login.Main.getOut();
		in = login.Main.getIn();
		
		int year = concertDate.getValue().getYear();
		int month = concertDate.getValue().getMonthValue();
		int day = concertDate.getValue().getDayOfMonth();
		
		String date = Integer.toString(year);
		if(month < 10) date += "0";
		date += Integer.toString(month);
		if(day < 10) date += "0";
		date += Integer.toString(day);
		
		out.println("requestRegistration/" + concertNameField.getText() + "/" + date + "/" + totalSeatNum[index]);
		
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		concertDate.setValue(LocalDate.now());
		Callback<DatePicker, DateCell> dayCellFactory = dp -> new DateCell()
        {
            
                 
            @Override
            public void updateItem(LocalDate item, boolean empty)
            {
                super.updateItem(item, empty);
                if(item.isBefore(LocalDate.now()))
                {
                    setStyle("-fx-background-color: #ffc0cb;");
					setDisable(true);
                }
            }
        };
        concertDate.setDayCellFactory(dayCellFactory);
	}

}
