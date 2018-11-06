package com.bcopstein.ExercicioRefatoracaoBanco;

import java.util.List;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TelaEstatistica {
	private Stage mainStage;
	private Scene cenaOperacoes;
	private Scene cenaEstatistica;
	private Conta conta;
	private List<Operacao> operacoes;
	private Label lbMes;
	private Label lbConta;
	private TextField tfConta;
	private Label lbSaldo;
	private TextField tfSaldo;
	private Label lbDeposito;
	private TextField tfDeposito;
	private Label lbRetirado;
	private TextField tfRetirada;

	public TelaEstatistica(Stage mainStage, Scene telaOperacoes, Conta conta, List<Operacao> operacoes) {
		this.mainStage = mainStage;
		this.cenaOperacoes = telaOperacoes;
		this.conta = conta;
		this.operacoes = operacoes;
	}

	public Scene getTelaEstatistica() {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		//grid.setGridLinesVisible(true);
		
		String conta = "Conta: ";
		lbConta = new Label(conta);
		grid.add(lbConta, 0, 1);
		
		tfConta = new TextField();
		grid.add(tfConta, 1, 1);
		
		String mes = "Mes: ";
		lbMes = new Label(mes);
		grid.add(lbMes, 2, 1);
		
		String saldoMedio = "Saldo Medio: ";
		lbSaldo = new Label(saldoMedio);
		grid.add(lbSaldo, 0, 2);
		
		tfSaldo = new TextField();
		grid.add(tfSaldo, 1, 2);
		
		String totDeposito = "Total Deposito: ";
		lbDeposito = new Label(totDeposito);
		grid.add(lbDeposito, 0, 3);
		
		tfDeposito = new TextField();
		grid.add(tfDeposito, 1, 3);
		
		String totRetirado = "Total Retirado: ";
		lbRetirado = new Label(totRetirado);
		grid.add(lbRetirado, 0, 4);
		
		tfRetirada = new TextField();
		grid.add(tfRetirada, 1, 4);
		
		Button btnVoltar = new Button("Voltar");
		grid.add(btnVoltar, 0, 5);
		
		btnVoltar.setOnAction(e -> {
			mainStage.setScene(cenaOperacoes);
		});
		
		cenaEstatistica = new Scene(grid);
		return cenaEstatistica;
	}
}
