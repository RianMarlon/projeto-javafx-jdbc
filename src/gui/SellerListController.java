package gui;

import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Department;
import model.entities.Seller;
import model.services.SellerService;

public class SellerListController implements Initializable {

	private  SellerService  service;
	
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
	private TableColumn<Seller, Department> tableColumnIdDepartamento;
	
	@FXML
	private Button btNovo;
	
	private ObservableList <Seller> obsList;
	
	@FXML
	public void onBtNovoAction () {
		System.out.println("onBtNovoAction");
	}
	
	public void setSellerService (SellerService service) {
		this.service = service;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("name"));
		tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		tableColumnDataNascimento.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
		tableColumnSalarioBase.setCellValueFactory(new PropertyValueFactory<>("baseSalary"));
		tableColumnIdDepartamento.setCellValueFactory(new PropertyValueFactory<>("department"));	
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewVendedor.prefHeightProperty().bind(stage.heightProperty());
	}
	
	public void updateTableView () {
		if (service == null) {
			throw new IllegalStateException("Service estava nulo");
		}
		List <Seller> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewVendedor.setItems(obsList);
	}
}
