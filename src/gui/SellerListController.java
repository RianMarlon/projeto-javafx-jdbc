package gui;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
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
	public void onBtNovoAction (ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		createDialogForm ("/gui/SellerForm.fxml", parentStage);
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
	
	private void createDialogForm (String absoluteNome, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteNome));
			Pane pane = loader.load();
			
			Stage dialogStage = new Stage ();
			dialogStage.setTitle("Digite os dados do Vendedor");
			dialogStage.setScene(new Scene (pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
		}
		catch (IOException e) {
			Alerts.showAlert("IO Exception", "Erro carregando a p�gina", e.getMessage(), AlertType.ERROR);
		}
	}
}