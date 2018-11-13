package com.bcopstein.ExercicioRefatoracaoBanco;

import java.util.Map;
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

public class TelaEntrada {
	private Stage mainStage; 
	private Scene cenaEntrada; 
	private Map<Integer, Conta> contas; 
	private TextField tfContaCorrente;

	public TelaEntrada(Stage anStage, Map<Integer, Conta> lstContas) {
		mainStage = anStage;
		contas = lstContas;
	}

	public Scene getTelaEntrada() {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		// grid.setGridLinesVisible(true);

		Text scenetitle = new Text("Bem vindo ao Banco Nossa Grana");
		scenetitle.setId("welcome-text");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);

		Label userName = new Label("Conta corrente:");
		grid.add(userName, 0, 1);

		tfContaCorrente = new TextField();
		grid.add(tfContaCorrente, 1, 1);

		Button btnIn = new Button("Entrar");
		Button btnOut = new Button("Encerrar");
		HBox hbBtn = new HBox(30);
		hbBtn.setAlignment(Pos.BOTTOM_CENTER);
		hbBtn.getChildren().add(btnIn);
		hbBtn.getChildren().add(btnOut);
		grid.add(hbBtn, 1, 4);

		// Botao encerrar
		btnOut.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Platform.exit();
			}
		});

		// Botao entrar
		btnIn.setOnAction(e -> {
			try {
				Integer nroConta = Integer.parseInt(tfContaCorrente.getText());
				// Codigo da camada de negócio
				
				//GerenciaContas.getInstance().setContaEmUso(nroConta);
				//Conta conta2 = GerenciaContas.getInstance().getContaEmUso();
				
				Conta conta = contas.get(nroConta);
				if (conta == null) {
					throw new NumberFormatException("Conta invalida");
				}
				// Transformar o parâmetro "conta" na conta atual na camada de negócio
				TelaOperacoes toper = new TelaOperacoes(mainStage, cenaEntrada,conta);
				Scene scene = toper.getTelaOperacoes();
				mainStage.setScene(scene);
			} catch (NumberFormatException ex) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Conta inválida !!");
				alert.setHeaderText(null);
				alert.setContentText("Número de conta inválido!!");

				alert.showAndWait();
			}
		});

		cenaEntrada = new Scene(grid);
		return cenaEntrada;
	}
}
