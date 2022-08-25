package bloodcollectiontbl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import issueblood.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class bloodcollectiontblViewController {
	
	Connection con;
    PreparedStatement pst;
    ResultSet table;
    ObservableList<collectionBean> allRecords;
    int f=0;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<collectionBean> tvtable;

    @FXML
    private TextField txtmobile;
    
    ObservableList<collectionBean> getAllObjects() 
    {
  	ObservableList<collectionBean> ary=FXCollections.observableArrayList();
  	
  	PreparedStatement pst;
  	try {
  		if(f==0)
  		{
  		  	pst=con.prepareStatement("select * from donations");

  		}
  		else
  		{
  		  	pst=con.prepareStatement("select * from donations where mobile=?");
  		  	pst.setString(1, txtmobile.getText());
  		}
  	table=pst.executeQuery();
  	while(table.next())
  	{
  		String mobile=table.getString("mobile");
  		String bgroup=table.getString("bgroup");
  		String dateofdonation=table.getString("dateofdonation");
  		collectionBean obj=new collectionBean(mobile,bgroup,dateofdonation);
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
    void doall(ActionEvent event) {
    	
      	
    	f=0;
    	    	
    	    	TableColumn<collectionBean, String> mobile=new TableColumn<collectionBean, String>("Mobile No");
    	    	mobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
    	    	mobile.setMinWidth(100);
    	    	

    	    	
    	    	TableColumn<collectionBean, String> bgroup=new TableColumn<collectionBean, String>("Blood Group");
    	    	bgroup.setCellValueFactory(new PropertyValueFactory<>("bgroup"));
    	    	bgroup.setMinWidth(100);
    	    	
    	    	TableColumn<collectionBean, String> dateofdonation=new TableColumn<collectionBean, String>("Date of Donation");
    	    	dateofdonation.setCellValueFactory(new PropertyValueFactory<>("dateofdonation"));
    	    	dateofdonation.setMinWidth(200);
    	    	
    	    	tvtable.getColumns().clear();
    	    	tvtable.getColumns().addAll(mobile,bgroup,dateofdonation);
    	    	   	
    	    	
    	    	allRecords=getAllObjects();	
    	    	

    	    	tvtable.setItems(allRecords);
    	    }
    
    public void writeExcel( ObservableList<collectionBean> list) throws Exception {
        Writer writer = null;
        try {
        	File file = new File("Bloodcollectioninfo.csv");
            writer = new BufferedWriter(new FileWriter(file));
            String text="Mobile,Blood Group,Date of Donation\n";
            writer.write(text);
            for (collectionBean p : list)
            {
				text = p.getMobile() + "," + p.getBgroup()+ "," +  p.getDateofdonation()+"\n";
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

    @SuppressWarnings("unchecked")
	@FXML
    void dorecent(ActionEvent event) {
    	
f=1;
    	
    	TableColumn<collectionBean, String> mobile=new TableColumn<collectionBean, String>("Mobile No");
    	mobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
    	mobile.setMinWidth(100);
    	

    	
    	TableColumn<collectionBean, String> bgroup=new TableColumn<collectionBean, String>("Blood Group");
    	bgroup.setCellValueFactory(new PropertyValueFactory<>("bgroup"));
    	bgroup.setMinWidth(100);
    	
    	TableColumn<collectionBean, String> dateofdonation=new TableColumn<collectionBean, String>("Date of Donation");
    	dateofdonation.setCellValueFactory(new PropertyValueFactory<>("dateofdonation"));
    	dateofdonation.setMinWidth(200);
    	
    	tvtable.getColumns().clear();
    	tvtable.getColumns().addAll(mobile,bgroup,dateofdonation);
    	   	
    	
    	allRecords=getAllObjects();	
    	

    	tvtable.setItems(allRecords);
    }

    @FXML
    void initialize() {
        assert tvtable != null : "fx:id=\"tvtable\" was not injected: check your FXML file 'bloodcollectiontblView.fxml'.";
        assert txtmobile != null : "fx:id=\"txtmobile\" was not injected: check your FXML file 'bloodcollectiontblView.fxml'.";
        
        con=	DatabaseConnection.doConnect();
    }

}
