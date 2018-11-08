package com.bcopstein.ExercicioRefatoracaoBanco;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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
		grid.add(datePicker, 3, 1);
		
		String saldoMedio = "Saldo Medio: ";
		lbSaldo = new Label(saldoMedio);
		lbSaldo.setMinWidth(Label.USE_PREF_SIZE);
		grid.add(lbSaldo, 0, 2);
		
		tfSaldo = new TextField();
		tfSaldo.setEditable(false);
		grid.add(tfSaldo, 1, 2);
		
		String totDeposito = "Total Deposito: ";
		lbDeposito = new Label(totDeposito);
		lbDeposito.setMinWidth(Label.USE_PREF_SIZE);
		grid.add(lbDeposito, 0, 3);
		
		tfDeposito = new TextField();
		tfDeposito.setEditable(false);
		grid.add(tfDeposito, 1, 3);
		
		String totRetirado = "Total Retirado: ";
		lbRetirado = new Label(totRetirado);
		lbRetirado.setMinWidth(Label.USE_PREF_SIZE);
		grid.add(lbRetirado, 0, 4);
		
		tfRetirada = new TextField();
		tfRetirada.setEditable(false);
		grid.add(tfRetirada, 1, 4);
		
		Button btnVoltar = new Button("Voltar");
		grid.add(btnVoltar, 0, 5);
		
		btnVoltar.setOnAction(e -> {
			mainStage.setScene(cenaOperacoes);
		});
		
		cenaEstatistica = new Scene(grid);
		this.calculaSaldoMedioNoMes(operacoes, conta);
		return cenaEstatistica;
	}
	
	public double calculaSaldoMedioNoMes(List<Operacao> operacao,Conta conta) {
		GregorianCalendar calen = new GregorianCalendar();
		int diaHoje = calen.get(Calendar.DAY_OF_MONTH);
		int mesHoje = calen.get(Calendar.MONTH + 1);
		int anoHoje = calen.get(Calendar.YEAR);
		
		List<Operacao> opsMesAnterior = operacao.stream().
				filter( (p) -> (p.getNumeroConta() == conta.getNumero() && ((p.getAno() < anoHoje) || ( p.getAno() == anoHoje && p.getMes() < ( 9 ) ))))
				.collect(Collectors.toList()); // busca todas operaçoes até o mes anterior ao selecionado
		
		List<Operacao> opsMesAtual = operacao.stream().
				filter( (p) -> (p.getNumeroConta() == conta.getNumero() && ( p.getAno() == anoHoje && p.getMes() == ( 9 ) )))
				.collect(Collectors.toList()); // busca todas operaçoes no mes selecionado
		
		double saldoMesAnterior = 0; // Calcula o saldo da conta até o mes anterior ao selecionado
		for( Operacao op : opsMesAnterior ) {
			if( op.getTipoOperacao() == 0 )
				saldoMesAnterior += op.getValorOperacao();
			else
				saldoMesAnterior -= op.getValorOperacao();
		}
		
//		System.out.println(opsMesAnterior);
//		System.out.println(opsMesAnterior.size());
//		System.out.println(saldoMesAnterior);
		
		double debitosDoDia[] = new double[31];
		double creditosDoDia[] = new double[31];
		for(int i=1; i<=30; i++) { // Armazena todos debitos e creditos em cada ponto do array
			int dia = i;
			debitosDoDia[dia] = opsMesAtual.stream().filter((op) -> op.getDia()== dia && op.getTipoOperacao()==1)
					.mapToDouble((op) -> op.getValorOperacao()).sum();
			creditosDoDia[dia] = opsMesAtual.stream().filter((op) -> op.getDia()== dia && op.getTipoOperacao()==0)
					.mapToDouble((op) -> op.getValorOperacao()).sum();
		}

		double saldoDoDia[] = new double[31];
		saldoDoDia[0] = saldoMesAnterior;
		for(int i=1; i<=30; i++)
			saldoDoDia[i] = saldoDoDia[i-1] - debitosDoDia[i] + creditosDoDia[i];
//		System.out.println("aaa" + saldoDoDia[26]);
		saldoDoDia[0] -= saldoMesAnterior;
		
		double totalDebitadoMes = Arrays.stream(debitosDoDia).sum();
		double totalCreditadoMes = Arrays.stream(creditosDoDia).sum();
		double saldoMedioMes = Arrays.stream(saldoDoDia).sum()/30;
//		System.out.println(totalDebitadoMes + ", " + totalCreditadoMes  + ", " + saldoMedioMes);
		return 0;
	}
}
