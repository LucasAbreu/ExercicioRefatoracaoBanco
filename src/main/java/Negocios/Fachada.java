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

	public boolean credito(double valor, Conta conta) {
		if (valor > 0 && conta != null) {
			GerenciaContas.getInstance().getListaContas().get(conta.getNumero()).deposito(valor);
			return true;
		}
		return false;
	}

	public boolean debito(double valor, Conta conta) {
		double totalSacadoHoje = GerenciaOperacoes.getInstance().calculaValorSacadoHoje();
		if (valor + totalSacadoHoje <= GerenciaContas.getInstance().getLimRetiradaDiaria() && conta != null) {
			GerenciaContas.getInstance().getListaContas().get(conta.getNumero()).retirada(valor);
			return true;
		}
		return false;
	}

	public double getSaldoConta(Conta conta) {
		return GerenciaContas.getInstance().getListaContas().get(conta.getNumero()).getSaldo();
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
