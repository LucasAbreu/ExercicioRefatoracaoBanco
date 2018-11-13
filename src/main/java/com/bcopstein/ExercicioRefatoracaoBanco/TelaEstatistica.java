package com.bcopstein.ExercicioRefatoracaoBanco;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.DatePicker;

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
	private DatePicker datePicker;

	public TelaEstatistica(Stage mainStage, Scene telaOperacoes, Conta conta, List<Operacao> operacoes) {
		this.mainStage = mainStage;
		this.cenaOperacoes = telaOperacoes;
		this.conta = conta;
		this.operacoes = operacoes;
	}

	public Scene getTelaEstatistica() {
		GridPane grid = new GridPane();
		//grid.setMinSize(grid.USE_PREF_SIZE, grid.USE_PREF_SIZE);
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		//grid.setGridLinesVisible(true);
		
		String c = "Conta: ";
		lbConta = new Label(c);
		grid.add(lbConta, 0, 1);
		
		tfConta = new TextField();
		tfConta.setText(String.valueOf(conta.getNumero()));
		tfConta.setEditable(false);
		grid.add(tfConta, 1, 1);
		
		String mes = "Mes: ";
		lbMes = new Label(mes);
		lbMes.setMinWidth(Label.USE_PREF_SIZE);
		grid.add(lbMes, 2, 1);
		
		datePicker = new DatePicker();
		datePicker.setOnAction(e ->{
			tfSaldo.setText(String.valueOf(GerenciaOperacoes.calculaSaldoMedioNoMes(operacoes, conta,)));
			tfDeposito.setText(String.valueOf(calculaDepositoNoMes()));
			tfRetirada.setText(String.valueOf(calculaRetiradaNoMes()));
		});
		grid.add(datePicker, 3, 1);
		
		String saldoMedio = "Saldo Medio: ";
		lbSaldo = new Label(saldoMedio);
		lbSaldo.setMinWidth(Label.USE_PREF_SIZE);
		grid.add(lbSaldo, 0, 2);
		
		tfSaldo = new TextField();
		tfSaldo.setText(String.valueOf(GerenciaOperacoes.calculaSaldoMedioNoMes(operacoes, conta)));
		tfSaldo.setEditable(false);
		grid.add(tfSaldo, 1, 2);
		
		String totDeposito = "Total Deposito: ";
		lbDeposito = new Label(totDeposito);
		lbDeposito.setMinWidth(Label.USE_PREF_SIZE);
		grid.add(lbDeposito, 0, 3);
		
		tfDeposito = new TextField();
		tfDeposito.setText(String.valueOf(calculaDepositoNoMes()));
		tfDeposito.setEditable(false);
		grid.add(tfDeposito, 1, 3);
		
		String totRetirado = "Total Retirado: ";
		lbRetirado = new Label(totRetirado);
		lbRetirado.setMinWidth(Label.USE_PREF_SIZE);
		grid.add(lbRetirado, 0, 4);
		
		tfRetirada = new TextField();
		tfRetirada.setText(String.valueOf(GerenciaOperacoes.calculaRetiradaNoMes()));
		tfRetirada.setEditable(false);
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
