package com.bcopstein.ExercicioRefatoracaoBanco;
import javafx.application.Application;
import javafx.stage.Stage;


public class App extends Application {
	private Persistencia persistencia;
	GerenciaContas gerenciaContas;
	//private Map<Integer,Conta> contas;
	
	private TelaEntrada telaEntrada;
	
    @Override
    public void start(Stage primaryStage) {
    	persistencia = Persistencia.getInstance();
        //contas = persistencia.loadContas();    	
    		
    	primaryStage.setTitle("$$ Banco NOSSA GRANA $$");

    	telaEntrada = new TelaEntrada(primaryStage); // << Substituir por singleton

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

