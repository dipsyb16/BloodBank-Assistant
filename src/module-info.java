module Bloodproject {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	requires javafx.graphics;
	requires javafx.base;
	
	opens application to javafx.graphics, javafx.fxml;
	opens DonorMaster to javafx.graphics, javafx.fxml;
	opens bloodunitcollection to javafx.graphics, javafx.fxml;
	opens bloodstock to javafx.graphics, javafx.fxml;
	opens panel to javafx.graphics, javafx.fxml;
	opens login to javafx.graphics, javafx.fxml;
	opens issueblood to javafx.graphics, javafx.fxml;
	opens developer to javafx.graphics, javafx.fxml;
	opens history to javafx.graphics, javafx.fxml,javafx.base;
	opens bloodcollectiontbl to javafx.graphics, javafx.fxml,javafx.base;
	opens bloodissued to javafx.graphics, javafx.fxml,javafx.base;
}
