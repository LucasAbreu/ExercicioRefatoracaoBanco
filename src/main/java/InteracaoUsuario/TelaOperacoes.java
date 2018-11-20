package InteracaoUsuario;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.stream.Collectors;

import Negocios.GerenciaContas;
import Negocios.GerenciaOperacoes;
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
	private Scene cenaEntrada;
	private Scene cenaOperacoes;
	private ObservableList<Operacao> operacoesConta;
//	private Conta conta;
	private TextField tfValorOperacao;
	private TextField tfSaldo;

	// ***MUDANÇAS***\\
	private Label cat;
	// private String categoria;
	private double totalSacadoHoje;
	// ***MUDANÇAS***\\

	/*public TelaOperacoes(Stage mainStage, Scene telaEntrada, Conta conta) { 
		this.mainStage = mainStage;
		this.cenaEntrada = telaEntrada;
		this.conta = conta;
		totalSacadoHoje = calculaValorSacadoHoje();
	}*/
	public TelaOperacoes(Stage mainStage, Scene telaEntrada) { 
		this.mainStage = mainStage;
		this.cenaEntrada = telaEntrada;
//		this.conta = GerenciaContas.getInstance().getContaEmUso();
	}


	public Scene getTelaOperacoes() {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		//String dadosCorr = conta.getNumero() + " : " + conta.getCorrentista();
		String dadosCorr = GerenciaContas.getInstance().getNumeroContaEmUso() + " : " + GerenciaContas.getInstance().getCorrentista();
		Text scenetitle = new Text(dadosCorr);
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);

		//String categoria = "Categoria: " + conta.getStrStatus();
		String categoria = "Categoria: " + GerenciaContas.getInstance().getStrStatus();
		//String limRetDiaria = "Limite retirada diaria: " + conta.getLimRetiradaDiaria();
		String limRetDiaria = "Limite retirada diaria: " + GerenciaContas.getInstance().getLimRetiradaDiaria();

		cat = new Label(categoria);
		grid.add(cat, 0, 1);

		Label lim = new Label(limRetDiaria);
		grid.add(lim, 0, 2);

		Label tit = new Label("Ultimos movimentos");
		grid.add(tit, 0, 3);

		// Seleciona apenas o extrato da conta atual
		//operacoesConta = FXCollections.observableArrayList(GerenciaOperacoes.getInstance().getOperacoesDaConta(conta.getNumero()));
		operacoesConta = FXCollections.observableArrayList(GerenciaOperacoes.getInstance().getOperacoesDaConta(GerenciaContas.getInstance().getNumeroContaEmUso()));

		ListView<Operacao> extrato = new ListView<>(operacoesConta);
		extrato.setPrefHeight(140);
		grid.add(extrato, 0, 4);

		tfSaldo = new TextField();
		tfSaldo.setDisable(true);
		tfSaldo.setText("" + GerenciaContas.getInstance().getSaldo());
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
				if (valor < 0.0) {
					throw new NumberFormatException("Valor invalido");
				}
				GerenciaContas.getInstance().deposito(valor);
				operacoesConta.add(GerenciaOperacoes.getInstance().adicionaOP(valor, 0));// ADICIONA NA OBSERVABLE
				tfSaldo.setText("" + GerenciaContas.getInstance().getSaldo());
			} catch (NumberFormatException ex) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Valor inválido !!");
				alert.setHeaderText(null);
				alert.setContentText("Valor inválido para operacao de crédito!!");
				alert.showAndWait();
			}
			// ***MUDANÇAS***\\
			cat.setText("Categoria: " + GerenciaContas.getInstance().getStrStatus());
			// ***MUDANÇAS***\\
		});

		btnDebito.setOnAction(e -> {
			try {
				double valor = Integer.parseInt(tfValorOperacao.getText());
//				if (valor < 0.0 || valor > GerenciaContas.getInstance().getSaldo()) // <- teste sendo realizado no GerenciaContas 
//					throw new NumberFormatException("Saldo insuficiente");
				// ***MUDANÇAS***\\
				totalSacadoHoje = GerenciaOperacoes.getInstance().calculaValorSacadoHoje();
				if (valor + totalSacadoHoje > GerenciaContas.getInstance().getLimRetiradaDiaria()) { //VERIFICANDO onde fazer esse if fora da tela
					throw new NumberFormatException("O valor :" + valor + " ultrapassa seu limite de saque do dia"
							+ "\nO total sacado hoje foi dê :" + totalSacadoHoje + "\nSeu limite de saque é dê :"
							+ GerenciaContas.getInstance().getLimRetiradaDiaria());
				} // ***MUDANÇAS***\\
				GerenciaContas.getInstance().retirada(valor);
				operacoesConta.add(GerenciaOperacoes.getInstance().adicionaOP(valor, 1));// ADICIONA NA OBSERVABLE
				tfSaldo.setText("" + GerenciaContas.getInstance().getSaldo());
			} catch (NumberFormatException ex) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Valor inválido !!");
				alert.setHeaderText(null);
				alert.setContentText(ex.getMessage());// ***MUDANÇAS***\\
				alert.showAndWait();
			}
			// ***MUDANÇAS***\\
			cat.setText("Categoria: " + GerenciaContas.getInstance().getStrStatus());
			// ***MUDANÇAS***\\
		});

		// Botao Estatistica
		btnEstatistica.setOnAction(e -> {
			try {
				TelaEstatistica telaEstatistica = new TelaEstatistica(mainStage, cenaOperacoes);
				Scene scene = telaEstatistica.getTelaEstatistica();
				mainStage.setScene(scene);
			} catch (NumberFormatException ex) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Conta inválida !!");
				alert.setHeaderText(null);
				alert.setContentText("Número de conta inválido!!");
				alert.showAndWait();
			}
		});

		btnVoltar.setOnAction(e -> {
			mainStage.setScene(cenaEntrada);
		});

		cenaOperacoes = new Scene(grid);
		return cenaOperacoes;
	}
}
