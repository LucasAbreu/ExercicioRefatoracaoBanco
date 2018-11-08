package com.bcopstein.ExercicioRefatoracaoBanco;
import java.util.List;
import java.util.Map;

import javafx.application.Application;
import javafx.stage.Stage;


public class App extends Application {
	private Persistencia persistencia;
	private Map<Integer,Conta> contas;
	private List<Operacao> operacoes;
	
	private TelaEntrada telaEntrada;
	
    @Override
    public void start(Stage primaryStage) {
    	persistencia = new Persistencia();
        contas = persistencia.loadContas();    	
    	operacoes = persistencia.loadOperacoes();
    	
    	primaryStage.setTitle("$$ Banco NOSSA GRANA $$");

    	telaEntrada = new TelaEntrada(primaryStage, contas, operacoes); // << Substituir por singleton

        primaryStage.setScene(telaEntrada.getTelaEntrada());
        primaryStage.show();
    }
    
    @Override
    public void stop() {
        persistencia.saveContas(contas.values());
        persistencia.saveOperacoes(operacoes);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}

