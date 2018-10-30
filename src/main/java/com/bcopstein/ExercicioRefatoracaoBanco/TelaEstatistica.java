package com.bcopstein.ExercicioRefatoracaoBanco;

import java.util.List;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class TelaEstatistica {
	private Stage mainStage;
	private Scene cenaOperacoes;
	private Scene cenaEstatistica;
	private Conta conta;
	private List<Operacao> operacoes;
	
	public TelaEstatistica(Stage mainStage, Scene telaOperacoes, Conta conta,List<Operacao> operacoes) {
		this.mainStage = mainStage;
		this.cenaOperacoes = telaOperacoes;
		this.conta = conta;
		this.operacoes = operacoes;
	}
	
	public Scene getTelaEstatistica() {
		return cenaEstatistica;
	}
}
