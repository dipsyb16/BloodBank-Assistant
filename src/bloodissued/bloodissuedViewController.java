package bloodissued;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import issueblood.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class bloodissuedViewController {
	Connection con;
    PreparedStatement pst;
    ResultSet table;
    ObservableList<issuedBean> allRecords;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> combobgrp;

    @FXML
    private DatePicker dpdate;

    @FXML
    private TableView<issuedBean> tvtable;
    
    ObservableList<issuedBean> getAllObjects() 
    {
  	ObservableList<issuedBean> ary=FXCollections.observableArrayList();
  	
  	PreparedStatement pst;
  	try {
  		  	pst=con.prepareStatement("select * from issued where bgroup=? and doi=?");
  		  	pst.setString(1, combobgrp.getValue());
  		    LocalDate local=dpdate.getValue();
      	    java.sql.Date date=java.sql.Date.valueOf(local);
      	    pst.setDate(2, date);
  		
  	table=pst.executeQuery();
  	while(table.next())
  	{
  		String nname=table.getString("nname");
  		String mobile=table.getString("mobile");
  		String hospital=table.getString("hospital");
  		String reason=table.getString("reason");
  		String doi=table.getString("doi");
  		String bgroup=table.getString("bgroup");
  		issuedBean obj=new issuedBean(nname,mobile,hospital,reason,doi,bgroup);
  		ary.add(obj);
  	}
  	}
  	catch(Exception exp)
  	{ 	
  		System.out.println(exp);
  	}
  	return ary;
  }
    

    @SuppressWarnings("unchecked")
	@FXML
    void doshow(ActionEvent event) {
    	TableColumn<issuedBean, String> nname=new TableColumn<issuedBean, String>("Needy Name");
    	nname.setCellValueFactory(new PropertyValueFactory<>("nname"));
    	nname.setMinWidth(100);
    	
    	TableColumn<issuedBean, String> mobile=new TableColumn<issuedBean, String>("Mobile No.");
    	mobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
    	mobile.setMinWidth(100);
    	
    	TableColumn<issuedBean, String> hospital=new TableColumn<issuedBean, String>("Hospital Name");
    	hospital.setCellValueFactory(new PropertyValueFactory<>("hospital"));
    	hospital.setMinWidth(100);
    	
    	TableColumn<issuedBean, String> reason=new TableColumn<issuedBean, String>("Reason");
    	reason.setCellValueFactory(new PropertyValueFactory<>("reason"));
    	reason.setMinWidth(100);
    	
    	TableColumn<issuedBean, String> doi=new TableColumn<issuedBean, String>("Date of Issue");
    	doi.setCellValueFactory(new PropertyValueFactory<>("doi"));
    	doi.setMinWidth(100);
    	
    	TableColumn<issuedBean, String> bgroup=new TableColumn<issuedBean, String>("Blood Group");
    	bgroup.setCellValueFactory(new PropertyValueFactory<>("bgroup"));
    	bgroup.setMinWidth(100);
    	
    	tvtable.getColumns().clear();
    	tvtable.getColumns().addAll(nname,mobile,hospital,reason,doi,bgroup);
    	   	
    	
    	allRecords=getAllObjects();	
    	

    	tvtable.setItems(allRecords);
    }
    
    public void writeExcel( ObservableList<issuedBean> list) throws Exception {
        Writer writer = null;
        try {
        	File file = new File("BloodIssuedinfo.csv");
            writer = new BufferedWriter(new FileWriter(file));
            String text="Needy Name,Mobile No,Hospital Name,Reason,Date of Issue,Blood Group\n";
            writer.write(text);
            for (issuedBean p : list)
            {
				text = p.getNname() + "," + p.getMobile()+ "," +  p.getHospital()+"," + p.getReason()+ "," +  p.getDoi()+","+p.getBgroup()+"\n";
                writer.write(text);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
           
            writer.flush();
             writer.close();
        }
    }
    @FXML
    void doexport(ActionEvent event) {
    	try {
			writeExcel(allRecords);
			System.out.println("Exported to excel..");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    @FXML
    void initialize() {
        assert combobgrp != null : "fx:id=\"combobgrp\" was not injected: check your FXML file 'bloodissuedView.fxml'.";
        assert dpdate != null : "fx:id=\"dpdate\" was not injected: check your FXML file 'bloodissuedView.fxml'.";
        assert tvtable != null : "fx:id=\"tvtable\" was not injected: check your FXML file 'bloodissuedView.fxml'.";
        
        ArrayList<String> items=new ArrayList<String>(Arrays.asList("A+","O+","B+","AB+","A-","O-","B-","AB-"));
        combobgrp.getItems().setAll(items);
        con=	DatabaseConnection.doConnect();
    }

}
