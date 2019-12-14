package requestmanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
	
	String[] strConcertList;

	private Image[] imageRes = {
			new Image(getClass().getResource("images/concertHall1.jpg").toString()),
			new Image(getClass().getResource("images/concertHall2.jpg").toString()),
	};
	private static String[] totalSeatNum = {"32", "4"};
	
	private int index;
	
	public static String[] getTotalSeatNum() {
		return totalSeatNum;
	}
	
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
		int year = concertDate.getValue().getYear();
		int month = concertDate.getValue().getMonthValue();
		int day = concertDate.getValue().getDayOfMonth();
		
		String date = Integer.toString(year);
		date += "-";
		if(month < 10) date += "0";
		date += Integer.toString(month);
		date += "-";
		if(day < 10) date += "0";
		date += Integer.toString(day);
		
		out.println("requestRegistration/" + concertNameField.getText() + "/" + date + "/" + totalSeatNum[index]);
		
		
		moveToRRM();
		
	}

	@FXML public void moveToRRM() {
		try {
			Parent main = FXMLLoader.load(getClass().getResource("/requestmanager/RegistrationRequestManager.fxml"));
			Scene scene = new Scene(main);
			Stage primaryStage = (Stage)btnRRM.getScene().getWindow();
			scene.getStylesheets().add(getClass().getResource("/mainmenu/mainmenu.css").toExternalForm());
			primaryStage.setScene(scene);
			 
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		out = login.LoginController.getOut();
		in = login.LoginController.getIn();
//		TODO
		
		totalSeat.setText(totalSeatNum[index]);
		strConcertList = RegistrationRequestManagerController.strConcertList;
		ArrayList<LocalDate> concertDateList = new ArrayList<>();
		for(int i = 0; i < strConcertList.length; i++) {
			if(strConcertList[i].split("/").length < 2) {
				
			} else {
				String date = strConcertList[i].split("/")[2];
				concertDateList.add(LocalDate.parse(date));
			}
		}
		System.out.println(Arrays.deepToString(strConcertList));
		
		concertDate.setValue(LocalDate.now());
		Callback<DatePicker, DateCell> dayCellFactory = dp -> new DateCell()
        {
            
                 
            @Override
            public void updateItem(LocalDate item, boolean empty)
            {
                super.updateItem(item, empty);
                if(item.isBefore(LocalDate.now()) || concertDateList.contains(item))
                {
                    setStyle("-fx-background-color: #808080;");
					setDisable(true);
                }
            }
        };
        concertDate.setDayCellFactory(dayCellFactory);
	}

}
