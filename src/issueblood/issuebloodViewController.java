package issueblood;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class issuebloodViewController {
	
	Connection con;
    PreparedStatement pst;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> combobgrp;

    @FXML
    private DatePicker dpdate;

    @FXML
    private TextField txthospital;

    @FXML
    private TextField txtmobile;

    @FXML
    private TextField txtname;

    @FXML
    private TextField txtreason;

    @FXML
    private TextField txtunits;
    
    void showMsg(String msg)
    {
    	Alert alert=new Alert(AlertType.INFORMATION);
    	alert.setTitle("Title");
    	alert.setContentText(msg);
    	alert.show();
    }
    
    void clear()
    {
    	combobgrp.setValue(null);
    	dpdate.setValue(null);
    	txthospital.setText(null);
    	txtmobile.setText(null);
    	txtname.setText(null);
    	txtreason.setText(null);
    	txtunits.setText(null);
    }
    @FXML
    void doupdate(ActionEvent event) throws Exception {
    	String s=combobgrp.getValue();
    	String m=null;
    	if(s.equals("A+"))
    	{
    		m="AP";
    	}
    	else if(s.equals("A-"))
    	{
    		m="AN";
    	}
    	else if(s.equals("B+"))
    	{
    		m="BP";
    	}
    	else if(s.equals("B-"))
    	{
    		m="BN";
    	}
    	else if(s.equals("AB+"))
    	{
    		m="ABP";
    	}
    	else if(s.equals("AB-"))
    	{
    		m="ABN";
    	}
    	else if(s.equals("O+"))
    	{
    		m="OP";
    	}
    	else
    	{
    		m="ON";
    	}
    	pst=con.prepareStatement("select "+m+" from total_blood_record");
    	ResultSet table1=pst.executeQuery();
    	int q=0;
    	while(table1.next())
    	{
    		q=table1.getInt(m);

    	}
    	if((q-Integer.parseInt(txtunits.getText())<0))
    	{
    		showMsg("This amount of blood is not available.");
    		clear();
    		return ;
    	}
    	pst=con.prepareStatement("insert into issued values(?,?,?,?,?,?)");
    	pst.setString(1, txtname.getText());
    	pst.setString(2,txtmobile.getText());
    	pst.setString(3, txthospital.getText());
    	pst.setString(4, txtreason.getText());
    	LocalDate local=dpdate.getValue();
    	java.sql.Date date=java.sql.Date.valueOf(local);
    	pst.setDate(5, date);
    	pst.setString(6, combobgrp.getValue());
    	pst.executeUpdate();
    	
    	
    	pst=con.prepareStatement("update total_blood_record set "+m+"="+m+"-?");
    	pst.setInt(1,Integer.parseInt(txtunits.getText()) );
    	pst.executeUpdate();
    	showMsg("Record Inserted Successfullyyyy");
    	clear();
    }

    @FXML
    void initialize() {
        assert combobgrp != null : "fx:id=\"combobgrp\" was not injected: check your FXML file 'issuebloodView.fxml'.";
        assert dpdate != null : "fx:id=\"dpdate\" was not injected: check your FXML file 'issuebloodView.fxml'.";
        assert txthospital != null : "fx:id=\"txthospital\" was not injected: check your FXML file 'issuebloodView.fxml'.";
        assert txtmobile != null : "fx:id=\"txtmobile\" was not injected: check your FXML file 'issuebloodView.fxml'.";
        assert txtname != null : "fx:id=\"txtname\" was not injected: check your FXML file 'issuebloodView.fxml'.";
        assert txtreason != null : "fx:id=\"txtreason\" was not injected: check your FXML file 'issuebloodView.fxml'.";
        assert txtunits != null : "fx:id=\"txtunits\" was not injected: check your FXML file 'issuebloodView.fxml'.";
        
        ArrayList<String> items=new ArrayList<String>(Arrays.asList("A+","O+","B+","AB+","A-","O-","B-","AB-"));
        combobgrp.getItems().setAll(items);
        
        con=	DatabaseConnection.doConnect();
    }

}
