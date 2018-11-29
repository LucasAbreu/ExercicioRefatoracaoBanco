package Negocios;
import java.util.Map;

import Persistencia.Persistencia;
 public class GerenciaContas  {
	static GerenciaContas instance;
	private Conta contaEmUso;
	private Map<Integer,Conta> listaContas;
	
 	private GerenciaContas() {
 		this.loadContas(Persistencia.getInstance().loadContas());
	}
	
	public static GerenciaContas getInstance() {
		if(instance == null) {
			instance = new GerenciaContas();
		}
		return instance;
	}
	
	public void isValidAccount(int numeroConta) {
		Conta account = listaContas.get(numeroConta); // TENTA ACHAR A CONTA
		if(account == null) throw new NumberFormatException("Numero de conta inválido!");
		contaEmUso = listaContas.get(numeroConta);
	}
	
	public void loadContas(Map<Integer,Conta> listaContas){
		this.listaContas = listaContas;
	}
	
	public void setContas(Map<Integer,Conta> listaContas) {
		this.listaContas = listaContas;
	}
	public Map<Integer,Conta> getListaContas(){ 
		return this.listaContas;
	}
	
	public void setContaEmUso(int numeroConta) {
		contaEmUso = listaContas.get(numeroConta);
	}
	
	public Conta getContaEmUso() { // CONTA EM USO
		return contaEmUso;
	}
	
	public int getNumeroContaEmUso() { // CONTA EM USO
		return contaEmUso.getNumero();
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
	
	public void deposito(double valor,Conta account){
		if(valor < 0) throw new NumberFormatException("Valor inválido");
		listaContas.get(account.getNumero()).deposito(valor);	
		GerenciaOperacoes.getInstance().adicionaOP(valor, 0); // ADICIONA A OP NA LISTA DE OP'S
	}
	public void deposito(double valor){ // CONTA EM USO
		if(valor < 0) throw new NumberFormatException("Valor inválido");
		contaEmUso.deposito(valor);
		GerenciaOperacoes.getInstance().adicionaOP(valor, 0);
	}
	
	public void retirada(double valor,Conta account) {
		if(valor < 0) throw new NumberFormatException("Valor inválido");
		double valSacadoHoje = GerenciaOperacoes.getInstance().calculaValorSacadoHoje();
		if(valor + valSacadoHoje > account.getLimRetiradaDiaria()) throw new NumberFormatException("O valor :" + valor + " ultrapassa seu limite de saque do dia"
				+"\nO total sacado hoje foi dê :" + valSacadoHoje + "\nSeu limite de saque é dê :"+GerenciaContas.getInstance().getLimRetiradaDiaria());
		listaContas.get(account.getNumero()).retirada(valor);
		GerenciaOperacoes.getInstance().adicionaOP(valor, 1);// ADICIONA A OP NA LISTA DE OP'S
	} 
	public void retirada(double valor) {// CONTA EM USO
		if(valor < 0) throw new NumberFormatException("Valor inválido");
		double valSacadoHoje = GerenciaOperacoes.getInstance().calculaValorSacadoHoje();
		if(valor + valSacadoHoje > contaEmUso.getLimRetiradaDiaria()) throw new NumberFormatException("O valor :" + valor + " ultrapassa seu limite de saque do dia"
				+"\nO total sacado hoje foi dê :" + valSacadoHoje + "\nSeu limite de saque é dê :"+GerenciaContas.getInstance().getLimRetiradaDiaria());
		listaContas.get(contaEmUso.getNumero()).retirada(valor);
		GerenciaOperacoes.getInstance().adicionaOP(valor, 1); // ADICIONA A OP NA LISTA DE OP'S
	}
	
	public double getSaldo() { // CONTA EM USO
		return contaEmUso.getSaldo();
	}
	
	public String getCorrentista() { // CONTA EM USO
		return contaEmUso.getCorrentista();
	}
	
	public String getStringDadosCorrentista() { // CONTA EM USO
		return contaEmUso.getNumero()+" : "+contaEmUso.getCorrentista();
	}
	
	public int getStatus() { // CONTA EM USO
		return contaEmUso.getStatus();
	}
	
	public String getStrStatus() { // CONTA EM USO
		return contaEmUso.getStrStatus();
	}
	
	public double getLimRetiradaDiaria() { // CONTA EM USO
		return contaEmUso.getLimRetiradaDiaria();
	}
	
	public String toString() { // CONTA EM USO
		return contaEmUso.toString();
	}
	
	
}