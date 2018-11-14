package com.bcopstein.ExercicioRefatoracaoBanco;
import java.util.Map;
import javafx.application.Application;
import javafx.stage.Stage;


public class App extends Application {
	private Persistencia persistencia;
	private Map<Integer,Conta> contas;
	
	private TelaEntrada telaEntrada;
	
    @Override
    public void start(Stage primaryStage) {
    	persistencia = Persistencia.getInstance();
        contas = persistencia.loadContas();    	
    		
    	primaryStage.setTitle("$$ Banco NOSSA GRANA $$");

    	telaEntrada = new TelaEntrada(primaryStage, contas); // << Substituir por singleton

        primaryStage.setScene(telaEntrada.getTelaEntrada());
        primaryStage.show();
    }
    
    @Override
    public void stop() {
        persistencia.saveContas(contas.values());
        persistencia.saveOperacoes(GerenciaOperacoes.getInstance().getOperacoes());
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}

