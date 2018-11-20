package Negocios;

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

	public boolean entrar(int numeroConta) {
		if (GerenciaContas.getInstance().getListaContas().get(numeroConta) != null) {
			GerenciaContas.getInstance().setContaEmUso(numeroConta);
			return true;
		}
		return false;
	}

	public void creditaDeposita(double valor, Conta conta) {
		// CREDITA/DEPOSITA O VALOR NA CONTA DO PARAMETRO
		(GerenciaContas.getInstance().getListaContas().get(conta.getNumero())).deposito(valor);
	}

	public void debitaSaca(double valor, Conta conta) {

	}

	public double getSaldoConta(Conta conta) {
		return (GerenciaContas.getInstance().getListaContas().get(conta.getNumero())).getSaldo();
		// RETORNA O SALDO DA CONTA DO PARAMETRO
	}

	public double getSaldoMedioConta(Conta conta, int mes, int ano) {
		return GerenciaOperacoes.getInstance().calculaSaldoMedioNoMes(conta, mes, ano);
	}

	public double getTotalDepositosConta(Conta conta, int mes, int ano) {
		return GerenciaOperacoes.getInstance().calculaDepositoNoMes(conta, mes, ano);
	}

	public double getTotalRetiradasConta(Conta conta, int mes, int ano) {
		return GerenciaOperacoes.getInstance().calculaRetiradaNoMes(conta, mes, ano);
	}

	public Conta getContaEmUso() {
		return GerenciaContas.getInstance().getContaEmUso();
	}

}
