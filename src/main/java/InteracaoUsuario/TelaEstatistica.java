package InteracaoUsuario;

import java.util.Calendar;
import java.util.GregorianCalendar;

import Negocios.Conta;
import Negocios.GerenciaOperacoes;
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
	private Scene cenaEstatistica;
	
	private Conta conta;
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
	private GregorianCalendar gregorianCalendar;

	private static TelaEstatistica instance;
	private TelaEstatistica() {
		gregorianCalendar = new GregorianCalendar();
	}
	public static TelaEstatistica getInstance() {
		if(instance == null) return new TelaEstatistica();
		return instance;
	}
	/*public TelaEstatistica(Stage mainStage, Scene telaOperacoes) {
		this.mainStage = mainStage;
		this.cenaOperacoes = telaOperacoes;
		this.conta = GerenciaContas.getInstance().getContaEmUso();
		gregorianCalendar = new GregorianCalendar();
	}*/

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
		
		String saldoMedio = "Saldo Medio: ";
		lbSaldo = new Label(saldoMedio);
		lbSaldo.setMinWidth(Label.USE_PREF_SIZE);
		grid.add(lbSaldo, 0, 2);
		
		tfSaldo = new TextField();
		tfSaldo.setText(String.valueOf(GerenciaOperacoes.getInstance().calculaSaldoMedioNoMes(conta, gregorianCalendar.get(Calendar.MONTH) + 1, gregorianCalendar.get(Calendar.YEAR))));
		tfSaldo.setEditable(false);
		grid.add(tfSaldo, 1, 2);
		
		String totDeposito = "Total Deposito: ";
		lbDeposito = new Label(totDeposito);
		lbDeposito.setMinWidth(Label.USE_PREF_SIZE);
		grid.add(lbDeposito, 0, 3);
		
		tfDeposito = new TextField();
		tfDeposito.setText(String.valueOf(GerenciaOperacoes.getInstance().calculaDepositoNoMes(conta, gregorianCalendar.get(Calendar.MONTH) + 1, gregorianCalendar.get(Calendar.YEAR))));
		tfDeposito.setEditable(false);
		grid.add(tfDeposito, 1, 3);
		
		String totRetirado = "Total Retirado: ";
		lbRetirado = new Label(totRetirado);
		lbRetirado.setMinWidth(Label.USE_PREF_SIZE);
		grid.add(lbRetirado, 0, 4);
		
		tfRetirada = new TextField();
		tfRetirada.setText(String.valueOf(GerenciaOperacoes.getInstance().calculaRetiradaNoMes(conta, gregorianCalendar.get(Calendar.MONTH) + 1, gregorianCalendar.get(Calendar.YEAR))));
		tfRetirada.setEditable(false);
		grid.add(tfRetirada, 1, 4);
		
		Button btnVoltar = new Button("Voltar");
		grid.add(btnVoltar, 0, 5);
		
		datePicker = new DatePicker();
		datePicker.setOnAction(e ->{
			tfSaldo.setText(String.valueOf(GerenciaOperacoes.getInstance().calculaSaldoMedioNoMes(conta, datePicker.getValue().getMonthValue(), datePicker.getValue().getYear())));
			tfDeposito.setText(String.valueOf(GerenciaOperacoes.getInstance().calculaDepositoNoMes(conta, datePicker.getValue().getMonthValue(), datePicker.getValue().getYear())));
			tfRetirada.setText(String.valueOf(GerenciaOperacoes.getInstance().calculaRetiradaNoMes(conta, datePicker.getValue().getMonthValue(), datePicker.getValue().getYear())));
		});
		grid.add(datePicker, 3, 1);
		
		btnVoltar.setOnAction(e -> {
			mainStage.setScene(TelaOperacoes.getInstance().getTelaOperacoes()); // TELA ESTATIST VOLTA PRA TELA OP
		});
		
		cenaEstatistica = new Scene(grid);
		return cenaEstatistica;
	}
}
