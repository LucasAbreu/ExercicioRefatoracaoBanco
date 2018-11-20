package Negocios;

public class LogicaOperacoesFachada {

	private static LogicaOperacoesFachada instance;

	private LogicaOperacoesFachada() {

	}

	public static LogicaOperacoesFachada getInstance() {
		if (instance == null) {
			return new LogicaOperacoesFachada();
		}
		return instance;
	}

	public boolean verificaSeContaExiste(int numeroConta) {
		return GerenciaContas.getInstance().getListaContas().get(numeroConta) != null;
		// RETORNA TRUE SE EXISTE FALSE SE NÃO EXSITE
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

	public double getSaldoMedioConta(Conta conta) {
		return 0.0;
	}

	public double getTotalDepositosConta(Conta conta) {
		return 0.0;
	}

	public double getTotalRetiradasConta(Conta conta) {
		return 0.0;
	}

	public Conta getContaEmUso() {
		return GerenciaContas.getInstance().getContaEmUso();
	}

}
