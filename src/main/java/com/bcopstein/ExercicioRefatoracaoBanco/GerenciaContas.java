package com.bcopstein.ExercicioRefatoracaoBanco;

import java.util.Map;

public class GerenciaContas  {
	private static GerenciaContas instance;
	private Conta ContaEmUso;
	private Map<Integer,Conta> listaContas;

	private void Gerenciacontas() {
		listaContas = Persistencia.getInstance().loadContas();
	}
	
	static public GerenciaContas getInstance() {
		if(instance == null) {
			instance = new GerenciaContas();
		}
		return instance;
	}
	
	public void setContaEmUso(Integer nroConta) {
		ContaEmUso = listaContas.get(nroConta);
	}
	
	public Conta getContaEmUso() {
		return ContaEmUso;
	}
	
}
