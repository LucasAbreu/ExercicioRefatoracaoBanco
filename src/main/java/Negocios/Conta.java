package Negocios;

public class Conta {
	private final int SILVER = 0;
	private final int GOLD = 1;
	private final int PLATINUM = 2;
	private final int LIM_SILVER_GOLD = 50000;
	private final int LIM_GOLD_PLATINUM = 200000;
	private final int LIM_PLATINUM_GOLD = 100000;
	private final int LIM_GOLD_SILVER = 25000;

	private int numero;
	private String correntista;
	private double saldo;
	public IStateConta state;

	public Conta(int umNumero, String umNome) {
		super();
		numero = umNumero;
		correntista = umNome;
		saldo = 0.0;
		state = FactoryStateConta.getInstance().novaConta(SILVER);
	}

	public Conta(int numero, String correntista, double saldo, int state) {
		this.numero = numero;
		this.correntista = correntista;
		this.saldo = saldo;
		this.state = FactoryStateConta.getInstance().novaConta(state);
	}

	public double getSaldo() {
		return saldo;
	}

	public Integer getNumero() {
		return numero;
	}

	public String getCorrentista() {
		return correntista;
	}

	public int getStatus() {
		return state.getStatus();
	}

	public String getStrStatus() {
		return state.getStrStatus();
	}

	public double getLimRetiradaDiaria() {
		return state.getLimRetiradaDiaria();
	}

	public void deposito(double valor) {
		if (state.getStatus() == SILVER) {
			saldo += state.creditoDeposito(valor);
			if (saldo >= LIM_SILVER_GOLD) {
				state = FactoryStateConta.getInstance().novaConta(GOLD);
			}
		} else if (state.getStatus() == GOLD) {
			saldo += state.creditoDeposito(valor);
			if (saldo >= LIM_GOLD_PLATINUM) {
				state = FactoryStateConta.getInstance().novaConta(PLATINUM);
			}
		} else if (state.getStatus() == PLATINUM) {
			saldo += state.creditoDeposito(valor);
		}
	}

	public void retirada(double valor) {
		if (saldo - valor < 0) {
			return;
		} else {
			saldo = saldo - valor;
			if (state.getStatus() == PLATINUM) {
				if (saldo < LIM_PLATINUM_GOLD) {
					state = FactoryStateConta.getInstance().novaConta(GOLD);
				}
			} else if (state.getStatus() == GOLD) {
				if (saldo < LIM_GOLD_SILVER) {
					state = FactoryStateConta.getInstance().novaConta(SILVER);
				}
			}
		}
	}

}