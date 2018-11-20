package Negocios;
public class Conta {
	public final int SILVER = 0;
	public final int GOLD = 1;
	public final int PLATINUM = 2;
	public final int LIM_SILVER_GOLD = 50000;
	public final int LIM_GOLD_PLATINUM = 200000;
	public final int LIM_PLATINUM_GOLD = 100000;
	public final int LIM_GOLD_SILVER = 25000;

	private int numero;
	private String correntista;
	private double saldo;
	private int status;

	public Conta(int umNumero, String umNome) {
		numero = umNumero;
		correntista = umNome;
		saldo = 0.0;
		status = SILVER;
	}
	
	public Conta(int umNumero, String umNome,double umSaldo, int umStatus) {
		numero = umNumero;
		correntista = umNome;
		saldo = umSaldo;
		status = umStatus;
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
		return status;
	}
	
	public String getStrStatus() {
		switch(status) {
		case 0:  return "Silver";
		case 1:  return "Gold";
		case 2:  return "Platinum";
		default: return "none";

		}
	}
	
	public double getLimRetiradaDiaria() {
		switch(status) {
		case 0:  return 5000.0;
		case 1:  return 50000.0;
		case 2:  return 500000.0;
		default: return 0.0;
		}
	}
	
	public void deposito(double valor) {
		if (status == SILVER) {
			saldo += valor;
			if (saldo >= LIM_SILVER_GOLD) {
				status = GOLD;
			}
		} else if (status == GOLD) {
			saldo += valor * 1.01;
			if (saldo >= LIM_GOLD_PLATINUM) {
				status = PLATINUM;
			}
		} else if (status == PLATINUM) {
			saldo += valor * 1.025;
		}
	}

	public void retirada(double valor) {
		if (saldo - valor < 0.0) {
			return;
		} else {
			saldo = saldo - valor;
			if (status == PLATINUM) {
				if (saldo < LIM_PLATINUM_GOLD) {
					status = GOLD;
				}
			} else if (status == GOLD) {
				if (saldo < LIM_GOLD_SILVER) {
					status = SILVER;
				}
			}
		}
	}

	@Override
	public String toString() {
		return "Conta [numero=" + numero + ", correntista=" + correntista + ", saldo=" + saldo + ", status=" + status
				+ "]";
	}
}
