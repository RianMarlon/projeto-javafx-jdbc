package gui;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Seller;

public class SellerListController implements Initializable {

	@FXML
	private TableView <Seller> tableViewVendedor;
	
	@FXML
	private TableColumn <Seller, Integer> tableColumnId;
	
	@FXML
	private TableColumn <Seller, String> tableColumnNome;
	
	@FXML
	private TableColumn <Seller, String> tableColumnEmail;
	
	@FXML
	private TableColumn <Seller, Date> tableColumnDataNascimento;
	
	@FXML
	private TableColumn <Seller, Double> tableColumnSalarioBase;
	
	@FXML
	private TableColumn<Seller, Integer> tableColumnIdDepartamento;
	
	@FXML
	private Button btNovo;
	
	@FXML
	public void onBtNovoAction () {
		System.out.println("onBtNovoAction");
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("name"));
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("email"));
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("baseSalary"));
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("departmentId"));	
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewVendedor.prefHeightProperty().bind(stage.heightProperty());
	}
}
