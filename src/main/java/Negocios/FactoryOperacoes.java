package Negocios;

import java.util.GregorianCalendar;

public class FactoryOperacoes {
	private static FactoryOperacoes fo = null;
	
	public static FactoryOperacoes getInstance() {
		if( fo == null )
			fo = new FactoryOperacoes();
		return fo;
	}
	
	public Operacao depositar(int numeroConta, int statusConta, double valorOperacao) {
		GregorianCalendar date = new GregorianCalendar();
		return new OperacaoDeposito(date.get(GregorianCalendar.DAY_OF_MONTH),
				((int) date.get(GregorianCalendar.MONTH)) + 1, date.get(GregorianCalendar.YEAR),
						date.get(GregorianCalendar.HOUR), date.get(GregorianCalendar.MINUTE),
						date.get(GregorianCalendar.SECOND), numeroConta, statusConta, valorOperacao);
	}
	
	public Operacao sacar(int numeroConta, int statusConta, double valorOperacao) {
		GregorianCalendar date = new GregorianCalendar();
		return new OperacaoRetirada(date.get(GregorianCalendar.DAY_OF_MONTH),
				((int) date.get(GregorianCalendar.MONTH)) + 1, date.get(GregorianCalendar.YEAR),
						date.get(GregorianCalendar.HOUR), date.get(GregorianCalendar.MINUTE),
						date.get(GregorianCalendar.SECOND), numeroConta, statusConta, valorOperacao);
	}
}
