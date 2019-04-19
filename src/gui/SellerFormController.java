package gui;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Seller;

public class SellerFormController implements Initializable {

	private Seller entity;
	
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
		
	@FXML
	public void onBtSalvarAction () {
		System.out.println("onBtSalvarAction");
	}
	
	@FXML
	public void onBtCancelarAction () {
		System.out.println("onBtCancelarAction");
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
		
		txtId.setText(String.valueOf(entity.getId()));
		txtNome.setText(entity.getName());
		txtEmail.setText(entity.getEmail());
		txtDataNascimento.setText(String.valueOf(entity.getBirthDate()));
		txtSalarioBase.setText(String.valueOf(entity.getBaseSalary()));
		txtDepartamentoId.setText(String.valueOf(entity.getId()));
	}
}
