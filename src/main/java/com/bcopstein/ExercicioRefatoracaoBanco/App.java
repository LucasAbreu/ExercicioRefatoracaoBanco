package com.bcopstein.ExercicioRefatoracaoBanco;

import InteracaoUsuario.TelaEntrada;
import Negocios.GerenciaContas;
import Negocios.GerenciaOperacoes;
import Persistencia.Persistencia;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
	
	private TelaEntrada telaEntrada;
	private Persistencia persistencia;

	@Override
	public void start(Stage primaryStage) {
		persistencia = Persistencia.getInstance();

		primaryStage.setTitle("$$ Banco NOSSA GRANA $$");

		telaEntrada = TelaEntrada.getInstance();
		telaEntrada.setMainStage(primaryStage);
		
		primaryStage.setScene(telaEntrada.getTelaEntrada());
		primaryStage.show();
	}

	@Override
	public void stop() {
		persistencia.saveContas(GerenciaContas.getInstance().getListaContas().values());
		persistencia.saveOperacoes(GerenciaOperacoes.getInstance().getOperacoes());
	}

	public static void main(String[] args) {
		launch(args);
	}
}
