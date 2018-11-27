package Negocios;

public class OperacaoDeposito extends Operacao {

	public OperacaoDeposito(int dia, int mes, int ano, int hora, int minuto, int segundo, int numeroConta,
			int statusConta, double valorOperacao) {
		super(dia, mes, ano, hora, minuto, segundo, numeroConta, statusConta, valorOperacao, 0);
	}

	@Override
	public int getTipoOperacao() {
		return 0;
	}

	@Override
	public String toString() {
		String line = getDia()+"/"+getMes()+"/"+getAno()+" "+
				getHora()+":"+getMinuto()+":"+getSegundo()+" "+
				getNumeroConta()+" "+
				getStatusConta() +" "+
				"<C>" + " "+
				getValorOperacao();
		return(line);
	}
}
