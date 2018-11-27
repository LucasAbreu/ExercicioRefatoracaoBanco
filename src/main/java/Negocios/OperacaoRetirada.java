package Negocios;

public class OperacaoRetirada extends Operacao{

	public OperacaoRetirada(int dia, int mes, int ano, int hora, int minuto, int segundo, int numeroConta,
			int statusConta, double valorOperacao) {
		super(dia, mes, ano, hora, minuto, segundo, numeroConta, statusConta, valorOperacao, 1);
	}

	@Override
	public int getTipoOperacao() {
		return 1;
	}

	@Override
	public String toString() {
		String line = getDia()+"/"+getMes()+"/"+getAno()+" "+
				getHora()+":"+getMinuto()+":"+getSegundo()+" "+
				getNumeroConta()+" "+
				getStatusConta() +" "+
				"<D>" + " "+
				getValorOperacao();
		return(line);
	}
}
