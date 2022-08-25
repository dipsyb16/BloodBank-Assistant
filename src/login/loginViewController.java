package login;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

public class loginViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField pfpassword;

    @FXML
    private TextField txtuserid;
    
    void showMsg(String msg)
    {
    	Alert alert=new Alert(AlertType.WARNING);
    	alert.setTitle("Title");
    	alert.setContentText(msg);
    	alert.show();
    }
    void clear()
    {
    	txtuserid.setText(null);
    	pfpassword.setText(null);
    }
    @FXML
    void dologin(ActionEvent event) {
    	if(txtuserid.getText().equals("charu")&&pfpassword.getText().equals("charu"))
    	{
    		try{
        		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/panel/panelView.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));  
                stage.show();
        		
        		
    			//to hide the opened window
    			 
    			  /* Scene scene1=(Scene)btnComboApp.getScene();
    			   scene1.getWindow().hide();
    			 */

    		}
    		catch(Exception e)
    		{
    			e.printStackTrace();
    		}
    	}
    	else
    	{
    		showMsg("Invalid userid or password");
    	}
    	clear();
    }

    @FXML
    void initialize() {
    	assert pfpassword != null : "fx:id=\"pfpassword\" was not injected: check your FXML file 'loginView.fxml'.";
    	assert txtuserid != null : "fx:id=\"txtuserid\" was not injected: check your FXML file 'loginView.fxml'.";

    }

}
