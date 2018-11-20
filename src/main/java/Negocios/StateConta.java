package Negocios;

public interface StateConta {
	public String getStrStatus();
	public double getLimRetiradaDiaria();
	public void deposito(double valor);
	public void retirada(double valor);
	
}
