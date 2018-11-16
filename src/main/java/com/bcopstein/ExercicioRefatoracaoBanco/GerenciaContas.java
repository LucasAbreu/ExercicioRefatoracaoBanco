package com.bcopstein.ExercicioRefatoracaoBanco;

import java.util.GregorianCalendar;
import java.util.Map;

public class GerenciaContas  {
	private static GerenciaContas instance;
	private Conta contaEmUso;
	private Map<Integer,Conta> listaContas;

	private GerenciaContas() {
		listaContas = Persistencia.getInstance().loadContas();
	}
	
	static public GerenciaContas getInstance() {
		if(instance == null) {
			instance = new GerenciaContas();
		}
		return instance;
	}
	
	public void setContaEmUso(Integer nroConta) {
		contaEmUso = listaContas.get(nroConta);
	}
	
	public Conta getContaEmUso() {
		return contaEmUso;
	}
	
	public Map<Integer,Conta> getListaContas(){
		return this.listaContas;
	}
	
	public void deposito(double valor) {
	}
	
	public void retirada() {
		
	}
}
