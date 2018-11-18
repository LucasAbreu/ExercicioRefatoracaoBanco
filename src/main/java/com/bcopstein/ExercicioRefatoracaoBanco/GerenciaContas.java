package com.bcopstein.ExercicioRefatoracaoBanco;
 import java.util.ArrayList;
import java.util.Map;
 public class GerenciaContas  {
	static GerenciaContas instance;
	private int ContaEmUso;
	private Map<Integer,Conta> listaContas;
	
 	private void Gerenciacontas() {
		return;
	}
	
	static public GerenciaContas getInstance() {
		if(instance == null) {
			instance = new GerenciaContas();
		}
		return instance;
	}
	
	public void loadContas(Map<Integer,Conta> listaContas){
		this.listaContas = listaContas;
	}
	
	public Map<Integer,Conta> getListaContas(){
		return this.listaContas;
	}
	
	public void setContaEmUso(int conta) {
		this.ContaEmUso = conta;
	}
	
	public Conta getContaEmUso() {
		return listaContas.get(this.ContaEmUso);
	}
	
	public int getNumeroContaEmUso() {
		return this.ContaEmUso;
	}
	
	public Conta criaConta(int umNumero, String umNome,double umSaldo, int umStatus) throws Exception {
		if(this.listaContas.get(umNumero) != null) {
			throw new Exception("Numero de conta já existe");
		}
		Conta c = new Conta(umNumero, umNome, umSaldo, umStatus);
		this.listaContas.put(umNumero, c);
		return c;
	}
	
	public void apagaConta(int conta) {
		this.listaContas.remove(conta);
	}
	
	public double deposito(double valor){
		this.listaContas.get(this.ContaEmUso).deposito(valor);
		return this.listaContas.get(this.ContaEmUso).getSaldo();
	}
	
	public double retirada(double valor) {
		if(valor > this.getSaldo())
			throw new NumberFormatException("Saldo insuficiente");
		else
			this.listaContas.get(this.ContaEmUso).retirada(valor);
		return this.listaContas.get(this.ContaEmUso).getSaldo();
	}
	
	public double getSaldo() {
		return this.listaContas.get(this.ContaEmUso).getSaldo();
	}
	
	public String getCorrentista() {
		return this.listaContas.get(this.ContaEmUso).getCorrentista();
	}
	
	public int getStatus() {
		return this.listaContas.get(this.ContaEmUso).getStatus();
	}
	
	public String getStrStatus() {
		return this.listaContas.get(this.ContaEmUso).getStrStatus();
	}
	
	public double getLimRetiradaDiaria() {
		return this.listaContas.get(this.ContaEmUso).getLimRetiradaDiaria();
	}
	
	public String toString() {
		return this.listaContas.get(this.ContaEmUso).toString();
	}
	
	
}