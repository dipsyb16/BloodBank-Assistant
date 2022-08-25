package bloodstock;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import issueblood.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

@SuppressWarnings("unused")
public class bloodstockViewController {
	
	Connection con;
    PreparedStatement pst;
    ResultSet table;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtabn;

    @FXML
    private TextField txtabp;

    @FXML
    private TextField txtan;

    @FXML
    private TextField txtap;

    @FXML
    private TextField txtbn;

    @FXML
    private TextField txtbp;

    @FXML
    private TextField txton;

    @FXML
    private TextField txtop;

    @FXML
    void initialize() throws Exception {
        assert txtabn != null : "fx:id=\"txtabn\" was not injected: check your FXML file 'bloodstockView.fxml'.";
        assert txtabp != null : "fx:id=\"txtabp\" was not injected: check your FXML file 'bloodstockView.fxml'.";
        assert txtan != null : "fx:id=\"txtan\" was not injected: check your FXML file 'bloodstockView.fxml'.";
        assert txtap != null : "fx:id=\"txtap\" was not injected: check your FXML file 'bloodstockView.fxml'.";
        assert txtbn != null : "fx:id=\"txtbn\" was not injected: check your FXML file 'bloodstockView.fxml'.";
        assert txtbp != null : "fx:id=\"txtbp\" was not injected: check your FXML file 'bloodstockView.fxml'.";
        assert txton != null : "fx:id=\"txton\" was not injected: check your FXML file 'bloodstockView.fxml'.";
        assert txtop != null : "fx:id=\"txtop\" was not injected: check your FXML file 'bloodstockView.fxml'.";
        
        con=	issueblood.DatabaseConnection.doConnect();
        pst=con.prepareStatement("select * from total_blood_record");
        table=pst.executeQuery();
        while(table.next())
        {
        txtap.setText(table.getString("AP"));
        txtan.setText(table.getString("AN"));
        txtabp.setText(table.getString("ABP"));
        txtabn.setText(table.getString("ABN"));
        txtbp.setText(table.getString("BP"));
        txtbn.setText(table.getString("BN"));
        txtop.setText(table.getString("OP"));
        txton.setText(table.getString("ON"));
        }
    }

}
