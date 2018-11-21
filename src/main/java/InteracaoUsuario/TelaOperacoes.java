package InteracaoUsuario;


import Negocios.Fachada;
import Negocios.Operacao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TelaOperacoes {
	private Stage mainStage;
	private Scene cenaOperacoes;

	private ObservableList<Operacao> operacoesConta;
	private TextField tfValorOperacao;
	private TextField tfSaldo;
	private Label cat;

	private static TelaOperacoes instance;

	private TelaOperacoes() {

	}

	public static TelaOperacoes getInstance() {
		if (instance == null) {
			return new TelaOperacoes();
		}
		return instance;
	}

	public void setMainStage(Stage stage) {
		mainStage = stage;
	}

	public Scene getTelaOperacoes() {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		String dadosCorr = Fachada.getInstance().getStringDadosConrrentista();
		Text scenetitle = new Text(dadosCorr);
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);

		String categoria = "Categoria: "+Fachada.getInstance().getStrgCategoriaConta();
		String limRetDiaria = "Limite retirada diaria: "+Fachada.getInstance().getLimiteRetiradaConta();

		cat = new Label(categoria);
		grid.add(cat, 0, 1);

		Label lim = new Label(limRetDiaria);
		grid.add(lim, 0, 2);

		Label tit = new Label("Ultimos movimentos");
		grid.add(tit, 0, 3);

		// Seleciona apenas o extrato da conta atual
		operacoesConta = FXCollections.observableArrayList(Fachada.getInstance().getListaOpConta());// CONTA EM USO

		ListView<Operacao> extrato = new ListView<>(operacoesConta);
		extrato.setPrefHeight(140);
		grid.add(extrato, 0, 4);

		tfSaldo = new TextField();
		tfSaldo.setDisable(true);
		tfSaldo.setText(""+Fachada.getInstance().getSaldoConta());
		HBox valSaldo = new HBox(20);
		valSaldo.setAlignment(Pos.BOTTOM_LEFT);
		valSaldo.getChildren().add(new Label("Saldo"));
		valSaldo.getChildren().add(tfSaldo);
		grid.add(valSaldo, 0, 5);

		tfValorOperacao = new TextField();
		HBox valOper = new HBox(30);
		valOper.setAlignment(Pos.BOTTOM_CENTER);
		valOper.getChildren().add(new Label("Valor operacao"));
		valOper.getChildren().add(tfValorOperacao);
		grid.add(valOper, 1, 1);

		Button btnCredito = new Button("Credito");
		Button btnDebito = new Button("Debito");
		Button btnEstatistica = new Button("Estatistica");
		Button btnVoltar = new Button("Voltar");
		HBox hbBtn = new HBox(20);
		hbBtn.setAlignment(Pos.TOP_CENTER);
		hbBtn.getChildren().add(btnCredito);
		hbBtn.getChildren().add(btnDebito);
		hbBtn.getChildren().add(btnEstatistica);
		hbBtn.getChildren().add(btnVoltar);
		grid.add(hbBtn, 1, 2);

		btnCredito.setOnAction(e -> {
			try {
				double valor = Integer.parseInt(tfValorOperacao.getText());
				Fachada.getInstance().credito(valor);
				extrato.setItems(FXCollections.observableArrayList(Fachada.getInstance().getListaOpConta())); // ATUALIZA LIST OBSERVABLE
				tfSaldo.setText(""+Fachada.getInstance().getSaldoConta()); // SALDO CONTA ATUAL
				cat.setText("Categoria: "+Fachada.getInstance().getStrgCategoriaConta());
			} catch (NumberFormatException ex) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Valor inválido !!");
				alert.setHeaderText(null);
				alert.setContentText(ex.getMessage());
				alert.showAndWait();
			}
		});

		btnDebito.setOnAction(e -> {
			try {
				double valor = Integer.parseInt(tfValorOperacao.getText());
				Fachada.getInstance().debito(valor);
				extrato.setItems(FXCollections.observableArrayList(Fachada.getInstance().getListaOpConta())); // ATUALIZA LIST OBSERVABLE
				tfSaldo.setText(""+Fachada.getInstance().getSaldoConta()); // SALDO CONTA ATUAL
				cat.setText("Categoria: "+Fachada.getInstance().getStrgCategoriaConta());
			} catch (NumberFormatException ex) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Valor inválido !!");
				alert.setHeaderText(null);
				alert.setContentText(ex.getMessage());
				alert.showAndWait();
			}
		});

		btnEstatistica.setOnAction(e -> { // VAI PARA TELA ESTATISTICA
			TelaEstatistica telaEstatistica = TelaEstatistica.getInstance();
			telaEstatistica.setMainStage(mainStage);
			Scene scene = telaEstatistica.getTelaEstatistica();
			mainStage.setScene(scene);
		});

		btnVoltar.setOnAction(e -> { // VOLTA PRA TELA ENTRADA
			TelaEntrada telaEntrada = TelaEntrada.getInstance();
			telaEntrada.setMainStage(mainStage);
			Scene scene = telaEntrada.getTelaEntrada();
			mainStage.setScene(scene);
		});

		cenaOperacoes = new Scene(grid);
		return cenaOperacoes;
	}

}
