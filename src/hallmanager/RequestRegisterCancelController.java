package hallmanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

public class RequestRegisterCancelController implements Initializable {

	@FXML ListView<String> concertRequestList;
	@FXML Button btnHallManager;

	Socket socket;
	PrintWriter out;
	BufferedReader in;
	
	private ObservableList<String> requestList;
	String[] strRequestList;
	
	int addRequestCount = 0;
	
	@FXML public void acceptRequestAction() {
	      
	      int index = concertRequestList.getFocusModel().getFocusedIndex();
	      if(index < 0) {
	         new Alert(Alert.AlertType.WARNING, "승인하실 콘서트를 선택해주세요.", ButtonType.CLOSE).show();
	      }
	      else if(index < addRequestCount) {
	         out.println("addConcert/" + index);
	         out.flush();
	         addRequestCount--;
	         requestList.remove(concertRequestList.getFocusModel().getFocusedIndex());
	      }
	      else {
	         out.println("cancelConcert/" + (index - addRequestCount));
	         out.flush();
	         requestList.remove(concertRequestList.getFocusModel().getFocusedIndex());
	      }
	   }

	@FXML public void moveToHallManager() {
		try {
			Parent main = FXMLLoader.load(getClass().getResource("ConcertHallManager.fxml"));
			Scene scene = new Scene(main);
			Stage primaryStage = (Stage)btnHallManager.getScene().getWindow();
			scene.getStylesheets().add(getClass().getResource("/mainmenu/mainmenu.css").toExternalForm());
			primaryStage.setScene(scene);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		requestList = FXCollections.observableArrayList();
		concertRequestList.setItems(requestList);
		
		out = login.LoginController.getOut();
		in = login.LoginController.getIn();

		out.println("getWaitingList");
		out.flush();
		try {
			strRequestList = in.readLine().split("//");
			if(!strRequestList[0].equals("")) {
				for(int i = 0; i < strRequestList.length; i++) {
					String[] concert = strRequestList[i].split("/");
					requestList.add("등록요청 // " + "Title : " + concert[0] + " Date : " + concert[1] + " Seat : " + concert[2]);
					addRequestCount++;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		out.println("getCancelList");
		out.flush();
		try {
			strRequestList = in.readLine().split("//");
			if(!strRequestList[0].equals("")) {
				for(int i = 0; i < strRequestList.length; i++) {
					String[] concert = strRequestList[i].split("/");
					requestList.add("취소요청 // " + "Title : " + concert[0] + " Date : " + concert[1] + " Seat : " + concert[2]);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML public void acceptRejectAction() {
		int index = concertRequestList.getFocusModel().getFocusedIndex();
	      if(index < 0) {
	         new Alert(Alert.AlertType.WARNING, "승인하실 콘서트를 선택해주세요.", ButtonType.CLOSE).show();
	      }
	      else if(index < addRequestCount) {
	         out.println("rejectAddRequest/" + index);
	         out.flush();
	         addRequestCount--;
	         requestList.remove(concertRequestList.getFocusModel().getFocusedIndex());
	         try {
				login.LoginController.setBalance(Integer.parseInt(in.readLine()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      }
	      else {
	         out.println("rejectCancelRequest/" + (index - addRequestCount));
	         out.flush();
	         requestList.remove(concertRequestList.getFocusModel().getFocusedIndex());
	      }
	}

}
