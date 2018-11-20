package InteracaoUsuario;

import java.util.Calendar;
import java.util.GregorianCalendar;

import Negocios.Conta;
import Negocios.Fachada;
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
	private static TelaEstatistica instance;
	
	private Stage mainStage;
	private Scene cenaEstatistica;
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

	

	private TelaEstatistica() {
		gregorianCalendar = new GregorianCalendar();
	}

	public static TelaEstatistica getInstance() {
		if (instance == null)
			return new TelaEstatistica();
		return instance;
	}

	public void setMainStage(Stage stage) {
		mainStage = stage;
	}

	public Scene getTelaEstatistica() {
		GridPane grid = new GridPane();
		// grid.setMinSize(grid.USE_PREF_SIZE, grid.USE_PREF_SIZE);
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		//AUXILIAR LOCAL DIMINUIR TAMANHO LINHAS//////////////////////////////
		Conta contaEmUso = Fachada.getInstance().getContaEmUso();
		int mesHoje = gregorianCalendar.get(Calendar.MONTH) + 1;
		int anoHoje = gregorianCalendar.get(Calendar.YEAR);
		//AUXILIAR LOCAL DIMINUIR TAMANHO LINHAS//////////////////////////////
		
		String c = "Conta: ";
		lbConta = new Label(c);
		grid.add(lbConta, 0, 1);
		
		tfConta = new TextField();
		tfConta.setText(""+Fachada.getInstance().getContaEmUso().getNumero());
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
		tfSaldo.setText(""+Fachada.getInstance().getSaldoMedioConta(contaEmUso,mesHoje,anoHoje));
		tfSaldo.setEditable(false);
		grid.add(tfSaldo, 1, 2);

		String totDeposito = "Total Deposito: ";
		lbDeposito = new Label(totDeposito);
		lbDeposito.setMinWidth(Label.USE_PREF_SIZE);
		grid.add(lbDeposito, 0, 3);

		tfDeposito = new TextField();
		tfDeposito.setText(""+Fachada.getInstance().getTotalDepositosConta(contaEmUso,mesHoje,anoHoje));
		tfDeposito.setEditable(false);
		grid.add(tfDeposito, 1, 3);

		String totRetirado = "Total Retirado: ";
		lbRetirado = new Label(totRetirado);
		lbRetirado.setMinWidth(Label.USE_PREF_SIZE);
		grid.add(lbRetirado, 0, 4);

		tfRetirada = new TextField();
		tfRetirada.setText(""+Fachada.getInstance().getTotalRetiradasConta(contaEmUso,mesHoje,anoHoje));
		tfRetirada.setEditable(false);
		grid.add(tfRetirada, 1, 4);

		Button btnVoltar = new Button("Voltar");
		grid.add(btnVoltar, 0, 5);

		datePicker = new DatePicker();
		datePicker.setOnAction(e -> {
			int mesDatePicker = datePicker.getValue().getMonthValue();
			int anoDatePicker = datePicker.getValue().getYear();
			tfSaldo.setText(""+Fachada.getInstance().getSaldoMedioConta(contaEmUso,mesDatePicker,anoDatePicker));
			tfDeposito.setText(""+Fachada.getInstance().getTotalRetiradasConta(contaEmUso,mesDatePicker,anoDatePicker));
			tfRetirada.setText(""+Fachada.getInstance().getTotalDepositosConta(contaEmUso,mesDatePicker,anoDatePicker));
		});
		grid.add(datePicker, 3, 1);

		btnVoltar.setOnAction(e -> {
			TelaOperacoes telaOperacoes = TelaOperacoes.getInstance();
			telaOperacoes.setMainStage(mainStage);
			Scene scene = telaOperacoes.getTelaOperacoes();
			mainStage.setScene(scene);
		});
	
		cenaEstatistica = new Scene(grid);
		return cenaEstatistica;
	}

}
