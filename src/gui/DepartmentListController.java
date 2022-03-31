package gui;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentListController implements Initializable {

	private DepartmentService service;
	
	@FXML
	private TableView<Department> tableViewDepartment;
	
	@FXML
	private TableColumn<Department, Integer> tableColumnId;
	
	@FXML
	private TableColumn<Department, String> tableColumnName;
	
	@FXML
	private Button btNew;
	
	private ObservableList<Department> obsList;
	
	@FXML
	public void onBtNewAction(ActionEvent event) { // referencia para o controller do event
		Stage parentStage = Utils.currentStage(event); // recebe o stage atual da classe criada Utils.
		createDialogForm("/gui/DepartmentForm.fxml", parentStage);		
	}
	
	public void setDepartmentService(DepartmentService service) {
		this.service = service;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
		
	}

	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("Name"));
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());
		
	}

	public void updateTableView() { // este metodo deve ser chamado quando na tela da lista
		if(service == null) {
			throw new IllegalStateException("Service was null!");
		}
		List<Department> list = service.findAll();
		obsList = FXCollections.observableArrayList(list); // cria a Observable List e passa ela pra tableview (tabela)
		tableViewDepartment.setItems(obsList);
	}
	
	private void createDialogForm(String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load(); // view DepartmentForm é carregada
			
			Stage dialogStage = new Stage(); // nova cena é criada sobre a já existente.
			dialogStage.setTitle("Enter Department data");
			dialogStage.setScene(new Scene(pane)); // atribuido elemento raiz anchorPane
			dialogStage.setResizable(false); // impedido de ridemensionar a janela
			dialogStage.initOwner(parentStage); // atribuido o stage atual (palco) pai da dialogStage
			dialogStage.initModality(Modality.APPLICATION_MODAL); // nao permiti acessar a outra janela
			dialogStage.showAndWait();
			// Modality.WINDOW_MODAL trocado para APPLICATION_MODAL
			
		} catch (IOException e) {
			Alerts.showAlert("Io Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}
}
