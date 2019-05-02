package gui;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

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
import model.exceptions.ValidationException;
import model.services.SellerService;

public class SellerFormController implements Initializable {
	
	private Seller entity;
	
	private SellerService service;
	
	private List <DataChangeListener> dataChangeListener = new ArrayList <>();
	
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
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
	private Label labelErrorEmail;
	
	@FXML
	private Label labelErrorDataNascimento;
	
	@FXML
	private Label labelErrorSalarioBase;
	
	@FXML
	private Label labelErrorIdDepartamento;
	
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
		catch (ValidationException e) {
			setErrorMessages(e.getErrors());
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
		clearErrorLabels();
		Seller obj = new Seller ();
		ValidationException exception = new ValidationException ("Erro de validação");
		
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		
		if (txtNome.getText() == null || txtNome.getText().trim().equals("")) {
			exception.addErrors("nome", "O campo não pode ser vazio");
		}
		
		obj.setName(txtNome.getText());
		
		if (txtEmail.getText() == null || txtEmail.getText().trim().equals("")) {
			exception.addErrors("email", "O campo não pode ser vazio");
		}

		obj.setEmail(txtEmail.getText());
		
		if (txtDataNascimento.getText() == null || txtDataNascimento.getText().trim().equals("")) {
			exception.addErrors("dataNascimento", "O campo não pode ser vazio");
		}
		
		obj.setBirthDate(Utils.tryParseToDate(txtDataNascimento.getText()));
		
		if (txtSalarioBase.getText() == null || txtSalarioBase.getText().trim().equals("")) {
            exception.addErrors("salarioBase", "O campo não pode ser vazio");
		}
		
		obj.setBaseSalary(Utils.tryParseToDouble(txtSalarioBase.getText()));
		
		if (txtDepartamentoId.getText() == null || txtDepartamentoId.getText().trim().equals("")) {
			exception.addErrors("idDepartamento", "O campo não pode ser vazio");
		}
		
		if (exception.getErrors().size() > 0) {
			throw exception;
		}

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
		} else {
			
			txtId.setText(String.valueOf(entity.getId()));
			txtNome.setText(entity.getName());
			txtEmail.setText(entity.getEmail());
			
			if (entity.getBirthDate() != null) {
				txtDataNascimento.setText(sdf.format(entity.getBirthDate()));
			}
	        
	        txtSalarioBase.setText(String.valueOf(entity.getBaseSalary()));
	            
	        if (entity.getDepartment() != null) {
	        	txtDepartamentoId.setText(String.valueOf(entity.getId()));
	        }
		}
	}
	
	private void setErrorMessages (Map<String, String> errors) {
		Set <String> fields = errors.keySet();
		
		if (fields.contains("nome")) {
			labelErrorNome.setText(errors.get("nome"));
		}
		
		if (fields.contains("email")) {
			labelErrorEmail.setText(errors.get("email"));
		}
		
		if (fields.contains("dataNascimento")) {
			labelErrorDataNascimento.setText(errors.get("dataNascimento"));
		}
		
		if (fields.contains("salarioBase")) {
			labelErrorSalarioBase.setText(errors.get("salalarioBase"));
		}
		
		if (fields.contains("idDepartamento")) {
			labelErrorIdDepartamento.setText(errors.get("idDepartamento"));
		}
	}
	
	 private void clearErrorLabels() {
	        labelErrorNome.setText("");
	        labelErrorEmail.setText("");
	        labelErrorDataNascimento.setText("");
	        labelErrorSalarioBase.setText("");
	        labelErrorIdDepartamento.setText("");
	    }
}
