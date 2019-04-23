package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.dao.DaoFactory;
import model.entities.Seller;
import model.services.SellerService;

public class SellerFormController implements Initializable {
	
	private Seller entity;
	
	private SellerService service;
	
	private List <DataChangeListener> dataChangeListener = new ArrayList <>();
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtNome;
	
	@FXML
	private TextField txtEmail;
	
	@FXML
	private TextField txtDataNascimento;
	
	@FXML
	private TextField txtSalarioBase;
	
	@FXML
	private TextField txtDepartamentoId;
	
	@FXML
	private Label labelErrorNome;
	
	@FXML
	private Button btSalvar;
	
	@FXML
	private Button btCancelar;
	
	public void setSeller (Seller entity) {
		this.entity = entity;
	}
	
	public void setSellerService (SellerService service) {
		this.service = service;
	}
	
	public void subscribeDataChangeListener (DataChangeListener listener) {
		dataChangeListener.add(listener);
	}
		
	@FXML
	public void onBtSalvarAction (ActionEvent event) {
		if (entity == null) {
			throw new IllegalStateException("Entity estava nulo");
		}
		if (service == null) {
			throw new IllegalStateException("Service estava nulo");
		}
		try {
			entity = getFormData();
			service.saveOrUpdate(entity);
			notifyDataChangeListener();
			Utils.currentStage(event).close();;
		}
		catch (DbException e)  {
			Alerts.showAlert("Erro salvando objeto", null, e.getMessage(), AlertType.ERROR);
		}
	}

	private void notifyDataChangeListener() {
		for (DataChangeListener listener : dataChangeListener) {
			listener.onDataChanged();
		}
		
	}

	private Seller getFormData() {
		Seller obj = new Seller ();
		
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		obj.setName(txtNome.getText());
		obj.setEmail(txtEmail.getText());
		obj.setBirthDate(Utils.tryParseToDate(txtDataNascimento.getText()));
		obj.setBaseSalary(Utils.tryParseToDouble(txtSalarioBase.getText()));
		
		Seller novo = DaoFactory.createSellerDao().findById(Utils.tryParseToInt(txtDepartamentoId.getText()));
		
		obj.setDepartment(novo.getDepartment());

		return obj;
	}

	@FXML
	public void onBtCancelarAction (ActionEvent event) {
		Utils.currentStage(event).close();;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}
	
	private void initializeNodes () {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtNome, 30);
		Constraints.setTextFieldMaxLength(txtEmail, 30);
		Constraints.setTextFieldMaxLength(txtDataNascimento, 10);
		Constraints.setTextFieldDouble(txtSalarioBase);
		Constraints.setTextFieldInteger(txtDepartamentoId);		
	}
	
	public void updateFormData () {
		if (entity  == null) {
			throw new IllegalStateException ("Entity estava nulo");
		}
		
		entity.setBirthDate(new Date());
		
		txtId.setText(String.valueOf(entity.getId()));
		txtNome.setText(entity.getName());
		txtEmail.setText(entity.getEmail());
		txtDataNascimento.setText(String.valueOf((entity.getBirthDate())));
		txtSalarioBase.setText(String.valueOf(entity.getBaseSalary()));
		txtDepartamentoId.setText(String.valueOf(entity.getId()));
	}
}
