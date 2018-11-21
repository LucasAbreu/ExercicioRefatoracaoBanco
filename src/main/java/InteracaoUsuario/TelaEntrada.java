package InteracaoUsuario;

import Negocios.Fachada;
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
	private static TelaEntrada instance;
	private Stage mainStage;
	private Scene cenaEntrada;
	private TextField tfContaCorrente;

	private TelaEntrada() {

	}

	public static TelaEntrada getInstance() {
		if (instance == null)
			return new TelaEntrada();
		return instance;
	}

	public void setMainStage(Stage stage) {
		mainStage = stage;
	}

	public Scene getTelaEntrada() {

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

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
				// 5 questoes factory, decorate, strategy, ideia de camada de negocios JML
				Integer numeroConta = Integer.parseInt(tfContaCorrente.getText());
				Fachada.getInstance().entrar(numeroConta); // VERIFICA LOGIN PELA FACHADA
				TelaOperacoes telaOperacoes = TelaOperacoes.getInstance();
				telaOperacoes.setMainStage(mainStage);
				Scene scene = telaOperacoes.getTelaOperacoes();
				mainStage.setScene(scene);
			} catch (NumberFormatException ex) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Conta inválida !!");
				alert.setHeaderText(null);
				alert.setContentText("Número de conta inválido!! ");
				alert.showAndWait();
			}
		});
		cenaEntrada = new Scene(grid);
		return cenaEntrada;
	}
}
