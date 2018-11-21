package Negocios;

import java.util.List;

public class Fachada {

	private static Fachada instance;

	private Fachada() {

	}

	public static Fachada getInstance() {
		if (instance == null) {
			return new Fachada();
		}
		return instance;
	}

	public void entrar(int numeroConta) {
		GerenciaContas.getInstance().isValidAccount(numeroConta);
	}

	public void credito(double valor, Conta conta) {
		GerenciaContas.getInstance().deposito(valor, conta);
	}
	public void credito(double valor) { // CONTA EM USO
		GerenciaContas.getInstance().deposito(valor);
	}

	public void debito(double valor, Conta conta) {
		GerenciaContas.getInstance().retirada(valor, conta);
	}
	public void debito(double valor) { // CONTA EM USO
		GerenciaContas.getInstance().retirada(valor);
	}

	public double getSaldoConta(Conta conta) { // CONTA EM USO
		return GerenciaContas.getInstance().getListaContas().get(conta.getNumero()).getSaldo();
	}
	public double getSaldoConta() {
		return GerenciaContas.getInstance().getContaEmUso().getSaldo(); // CONTA EM USO
	}

	public double getSaldoMedioConta(int mes, int ano) { // CONTA EM USO
		return GerenciaOperacoes.getInstance().calculaSaldoMedioNoMes(mes, ano);
	}
	public double getSaldoMedioConta(Conta conta, int mes, int ano) {
		return GerenciaOperacoes.getInstance().calculaSaldoMedioNoMes(conta, mes, ano);
	}

	public double getTotalDepositosConta(int mes, int ano) { // CONTA EM USO
		return GerenciaOperacoes.getInstance().calculaDepositoNoMes(mes, ano);
	}
	public double getTotalDepositosConta(Conta conta, int mes, int ano) {
		return GerenciaOperacoes.getInstance().calculaDepositoNoMes(conta, mes, ano);
	}

	public double getTotalRetiradasConta(int mes, int ano) { // CONTA EM USO
		return GerenciaOperacoes.getInstance().calculaRetiradaNoMes(mes, ano);
	}
	public double getTotalRetiradasConta(Conta conta, int mes, int ano) {
		return GerenciaOperacoes.getInstance().calculaRetiradaNoMes(conta, mes, ano);
	}
	
	public double getLimiteRetiradaConta(){
		return GerenciaContas.getInstance().getLimRetiradaDiaria(); // CONTA EM USO
	}

	public Conta getContaEmUso() {
		return GerenciaContas.getInstance().getContaEmUso(); // CONTA EM USO
	}
	
	public String getStringDadosConrrentista() {
		return GerenciaContas.getInstance().getStringDadosCorrentista(); // CONTA EM USO
	}
	
	public String getStrgCategoriaConta(){
		return GerenciaContas.getInstance().getStrStatus(); // CONTA EM USO
	}
	
	public List<Operacao> getListaOpConta(){
		return GerenciaOperacoes.getInstance().getOperacoesDaConta(); // CONTA EM USO
	}
}
