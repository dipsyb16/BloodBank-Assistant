package bloodunitcollection;

import java.io.FileInputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import issueblood.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class bloodunitcollectionViewController {
	Connection con;
    PreparedStatement pst;
    ResultSet table;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> combomobno;

    @FXML
    private DatePicker dpdate;

    @FXML
    private ImageView imgview;

    @FXML
    private TextField txtbgrp;
    
    @FXML
    void doclear(ActionEvent event) {
    	imgview.setImage(null);
    	txtbgrp.setText("");
    	combomobno.getEditor().setText("");
    	dpdate.setValue(null);
    }
    void showMsg(String msg)
    {
    	Alert alert=new Alert(AlertType.INFORMATION);
    	alert.setTitle("Title");
    	alert.setContentText(msg);
    	alert.show();
    }
    
    boolean chkmobileno(String mobileno)
    {
    	boolean jasoos=false;
    	try {
        	pst=con.prepareStatement("select * from donors where mobile=?");
        	pst.setString(1,combomobno.getEditor().getText());
        	
        	table=pst.executeQuery();
        	      	
        	
        	while(table.next())
        	{
        		jasoos=true;
        	}
        	}
        	catch(Exception exp)
        	{ 		
        	}
    	return jasoos;
    }
    
    void clear()
    {
    	combomobno.getEditor().setText(null);
    	txtbgrp.setText(null);
    	imgview.setImage(null);
    	dpdate.setValue(null);
    }
    @FXML
    void doupload(ActionEvent event) throws Exception {
    	pst=con.prepareStatement("insert into donations values(?,?,?)");
    	pst.setString(1, combomobno.getEditor().getText());
    	pst.setString(2, txtbgrp.getText());
    	LocalDate local=dpdate.getValue();
    	java.sql.Date date=java.sql.Date.valueOf(local);
    	pst.setDate(3, date);
    	pst.executeUpdate();
    	String s=txtbgrp.getText();
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
    	pst=con.prepareStatement("update total_blood_record set "+m+"="+m+"+1");
    	pst.executeUpdate();
    	showMsg("Record Inserted Successfullyyyy");
    	clear();
    }

    @FXML
    void dosearch(ActionEvent event) throws Exception {
    	String mobileno=combomobno.getEditor().getText();
    	if(chkmobileno(mobileno)==false)
    		{
    		showMsg("Mobile no doesn't Exists...");
    		return;
    		}
    	pst=con.prepareStatement("select picpath,bgroup from donors where mobile=?");
    	pst.setString(1, mobileno);
    	table=pst.executeQuery();
    	while(table.next())
    	{
    		txtbgrp.setText(table.getString("bgroup"));
    		Image image = new Image(new FileInputStream(table.getString("picpath")));
    		imgview.setImage(image);
    	}
    }

    @FXML
    void initialize() throws Exception {
        assert combomobno != null : "fx:id=\"combomobno\" was not injected: check your FXML file 'bloodunitcollectionView.fxml'.";
        assert dpdate != null : "fx:id=\"dpdate\" was not injected: check your FXML file 'bloodunitcollectionView.fxml'.";
        assert imgview != null : "fx:id=\"imgview\" was not injected: check your FXML file 'bloodunitcollectionView.fxml'.";
        assert txtbgrp != null : "fx:id=\"txtbgrp\" was not injected: check your FXML file 'bloodunitcollectionView.fxml'.";
        
        con=	DatabaseConnection.doConnect();
        pst=con.prepareStatement("select distinct mobile from donors");
        ArrayList<String> item=new ArrayList<String>();
        table=pst.executeQuery();
        while(table.next())
        {
        	item.add(table.getString("mobile"));
        }
        combomobno.getItems().setAll(item);
    }

}
