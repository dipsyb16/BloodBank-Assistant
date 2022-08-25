package DonorMaster;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import issueblood.DatabaseConnection;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class DonorMasterViewController {
	
	Connection con;
    PreparedStatement pst;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> combobgrp;

    @FXML
    private ComboBox<String> combogender;

    @FXML
    private ImageView imgview;

    @FXML
    private TextField txtaddress;

    @FXML
    private TextField txtage;

    @FXML
    private TextField txtcity;

    @FXML
    private TextField txtdisease;

    @FXML
    private TextField txtmobno;

    @FXML
    private TextField txtname;
    
    File f=null;
    ResultSet table;
    @FXML
    void dobrowse(ActionEvent event) throws Exception {
    	FileChooser fc=new FileChooser();
    	fc.getExtensionFilters().add(new ExtensionFilter("JPG Files","*.jpg"));
    	f=fc.showOpenDialog(null);
    	if(f!=null)
    	{
    		Image image = new Image(new FileInputStream(f.getAbsoluteFile()));
    		imgview.setImage(image);
    	}
    }
    
    void showMsg(String msg)
    {
    	Alert alert=new Alert(AlertType.INFORMATION);
    	alert.setTitle("Title");
    	alert.setContentText(msg);
    	alert.show();
    }

    @FXML
    void docancel(ActionEvent event) {
    	try {
			pst=con.prepareStatement("delete from donors where mobile=?");
			pst.setString(1, txtmobno.getText());
			int count=pst.executeUpdate();
			if(count==0)
				showMsg("Invalid Mobile no....");
			else
			showMsg("Deleted");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void doclear(ActionEvent event) {
    	txtmobno.setText("");
    	txtname.setText("");
    	txtaddress.setText("");
    	txtcity.setText("");
    	txtdisease.setText("");
    	txtage.setText("");
    	combogender.setValue("");
    	combobgrp.setValue("");
    	imgview.setImage(null);
    }
    void clear()
    {
    	txtmobno.setText("");
    	txtname.setText("");
    	txtaddress.setText("");
    	txtcity.setText("");
    	txtdisease.setText("");
    	txtage.setText("");
    	combogender.setValue("");
    	combobgrp.setValue("");
    	imgview.setImage(null);
    }
    boolean findRecord(String mobileno)
    {
    	boolean jasoos=false;
    	try {
        	pst=con.prepareStatement("select * from donors where mobile=?");
        	pst.setString(1, mobileno);
        	
        	table=pst.executeQuery();
        	      	
        	
        	while(table.next())
        	{
        		jasoos=true;
        		txtname.setText(table.getString("name"));
        		combogender.setValue(table.getString("gender"));
        		txtaddress.setText(table.getString("address"));
        		txtcity.setText(table.getString("city"));
        		combobgrp.setValue(table.getString("bgroup"));
        		txtage.setText(String.valueOf(table.getInt("age")));
        		txtdisease.setText(table.getString("disease"));
        		Image image = new Image(new FileInputStream(table.getString("picpath")));
        		imgview.setImage(image);
        		
        	}
        	}
        	catch(Exception exp)
        	{ 		
        	}
    	return jasoos;
    }
    @FXML
    void dofind(ActionEvent event) {
    	
    	String str=txtmobno.getText();
    	if(findRecord(str)==false)
    			showMsg("Invalid mobile no....");
    }
    
    boolean chkmobileno(String mobileno)
    {
    	boolean jasoos=false;
    	try {
        	pst=con.prepareStatement("select * from donors where mobile=?");
        	pst.setString(1,txtmobno.getText());
        	
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
    @FXML
    void doregister(ActionEvent event) {
    	String mobile=txtmobno.getText();
    	if(chkmobileno(mobile)==true)
    		{
    		showMsg("Mobile no already Exists...");
    		return;
    		}
    	
    	try {
			pst=con.prepareStatement("insert into donors values(?,?,?,?,?,?,?,?,?,current_date())");
			pst.setString(1, txtmobno.getText());
			pst.setString(2, f.getAbsolutePath());
			pst.setString(3, txtname.getText());
			pst.setString(4, combogender.getValue().toString());
			pst.setString(5, txtaddress.getText());
			pst.setString(6, txtcity.getText());
			pst.setString(7, combobgrp.getValue().toString());
			pst.setInt(8, Integer.parseInt(txtage.getText()));
			pst.setString(9, txtdisease.getText());
			pst.executeUpdate();
			showMsg("Record Inserted Successfullyyyy");
			clear();
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void doupdate(ActionEvent event) throws Exception {
    	pst=con.prepareStatement("update donors set picpath=?,name=?,gender=?,address=?,city=?,bgroup=?,age=?,disease=? where mobile=?");
    	pst.setString(1, f.getAbsolutePath());
    	pst.setString(2, txtname.getText());
    	pst.setString(3, combogender.getValue().toString());
		pst.setString(4, txtaddress.getText());
		pst.setString(5, txtcity.getText());
		pst.setString(6, combobgrp.getValue().toString());
		pst.setInt(7, Integer.parseInt(txtage.getText()));
		pst.setString(8, txtdisease.getText());
		pst.setString(9, txtmobno.getText());
		int count=pst.executeUpdate();
		if(count==0)
			showMsg("Invalid MobileNo....");
		else	
		    showMsg("Record Updated Successfullyyyy");
		clear();
    }

    @FXML
    void initialize() {
        assert combobgrp != null : "fx:id=\"combobgrp\" was not injected: check your FXML file 'DonorMasterView.fxml'.";
        assert combogender != null : "fx:id=\"combogender\" was not injected: check your FXML file 'DonorMasterView.fxml'.";
        assert imgview != null : "fx:id=\"imgview\" was not injected: check your FXML file 'DonorMasterView.fxml'.";
        assert txtaddress != null : "fx:id=\"txtaddress\" was not injected: check your FXML file 'DonorMasterView.fxml'.";
        assert txtage != null : "fx:id=\"txtage\" was not injected: check your FXML file 'DonorMasterView.fxml'.";
        assert txtcity != null : "fx:id=\"txtcity\" was not injected: check your FXML file 'DonorMasterView.fxml'.";
        assert txtdisease != null : "fx:id=\"txtdisease\" was not injected: check your FXML file 'DonorMasterView.fxml'.";
        assert txtmobno != null : "fx:id=\"txtmobno\" was not injected: check your FXML file 'DonorMasterView.fxml'.";
        assert txtname != null : "fx:id=\"txtname\" was not injected: check your FXML file 'DonorMasterView.fxml'.";
        
        ArrayList<String> items=new ArrayList<String>(Arrays.asList("Male","Female","Others"));
        combogender.getItems().setAll(items);
        
        ArrayList<String> items1=new ArrayList<String>(Arrays.asList("A+","O+","B+","AB+","A-","O-","B-","AB-"));
        combobgrp.getItems().setAll(items1);
        
        con=	DatabaseConnection.doConnect();
    }

}
