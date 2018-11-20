package Negocios;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

import Persistencia.Persistencia;

public class GerenciaOperacoes {
	private static GerenciaOperacoes instance;
	private List<Operacao> operacoes;
	private Conta contaEmUso;

	private GerenciaOperacoes() {
		operacoes = Persistencia.getInstance().loadOperacoes();
		contaEmUso = GerenciaContas.getInstance().getContaEmUso();
	}

	static public GerenciaOperacoes getInstance() {
		if (instance == null)
			instance = new GerenciaOperacoes();
		return (instance);
	}

	public double calculaSaldoMedioNoMes(Conta conta, int mes, int ano) {

		List<Operacao> opsMesAnterior = operacoes.stream()
				.filter((p) -> (p.getNumeroConta() == conta.getNumero()
						&& ((p.getAno() < ano) || (p.getAno() == ano && p.getMes() < (mes)))))
				.collect(Collectors.toList()); // busca todas operaçoes até o mes anterior ao selecionado

		List<Operacao> opsMesAtual = operacoes.stream()
				.filter((p) -> (p.getNumeroConta() == conta.getNumero() && (p.getAno() == ano && p.getMes() == (mes))))
				.collect(Collectors.toList()); // busca todas operaçoes no mes selecionado

		double saldoMesAnterior = 0; // Calcula o saldo da conta até o mes anterior ao selecionado
		for (Operacao op : opsMesAnterior) {
			if (op.getTipoOperacao() == 0)
				saldoMesAnterior += op.getValorOperacao();
			else
				saldoMesAnterior -= op.getValorOperacao();
		}

		double debitosDoDia[] = new double[31];
		double creditosDoDia[] = new double[31];
		for (int i = 1; i <= 30; i++) { // Armazena todos debitos e creditos em cada ponto do array
			int dia = i;
			debitosDoDia[dia] = opsMesAtual.stream().filter((op) -> op.getDia() == dia && op.getTipoOperacao() == 1)
					.mapToDouble((op) -> op.getValorOperacao()).sum();
			creditosDoDia[dia] = opsMesAtual.stream().filter((op) -> op.getDia() == dia && op.getTipoOperacao() == 0)
					.mapToDouble((op) -> op.getValorOperacao()).sum();
		}

		double saldoDoDia[] = new double[31];
		saldoDoDia[0] = saldoMesAnterior;
		for (int i = 1; i <= 30; i++)
			saldoDoDia[i] = saldoDoDia[i - 1] - debitosDoDia[i] + creditosDoDia[i];

		saldoDoDia[0] -= saldoMesAnterior;

		double saldoMedioMes = Arrays.stream(saldoDoDia).sum() / 30;

		return new BigDecimal(saldoMedioMes).setScale(2, RoundingMode.HALF_DOWN).doubleValue();
	}

	public double calculaRetiradaNoMes(Conta conta, int mes, int ano) {

		double valorRetirado = operacoes.stream()
				.filter((op) -> op.getNumeroConta() == conta.getNumero()
						&& op.getAno() == ano && op.getMes() == mes && op.getTipoOperacao() == 1)
				.mapToDouble((op) -> op.getValorOperacao()).sum();
		System.out.println(valorRetirado);
		return valorRetirado;
	}

	public double calculaDepositoNoMes(Conta conta, int mes, int ano) {

		double valorDepositado = operacoes.stream()
				.filter((op) -> op.getNumeroConta() == conta.getNumero()
						&& op.getAno() == ano && op.getMes() == mes && op.getTipoOperacao() == 0)
				.mapToDouble((op) -> op.getValorOperacao()).sum();

		return valorDepositado;
	}

	public List<Operacao> getOperacoesDaConta(int nroConta){
		return operacoes.stream().filter((op) -> op.getNumeroConta() == nroConta).collect(Collectors.toList());
	}

	public List<Operacao> getOperacoes(){
		return operacoes;
	}
	
	public double calculaValorSacadoHoje() {
		GregorianCalendar calen = new GregorianCalendar();
		int diaHoje = calen.get(Calendar.DAY_OF_MONTH);
		int mesHoje = calen.get(Calendar.MONTH)+1;
		int anoHoje = calen.get(Calendar.YEAR);
		double valorSacadoHoje = GerenciaOperacoes.getInstance().getOperacoes().stream()
				.filter((op) -> op.getNumeroConta() == contaEmUso.getNumero() && op.getAno() == anoHoje && op.getMes() == mesHoje
						&& op.getDia() == diaHoje && op.getTipoOperacao() == 1.0)
				.mapToDouble((op) -> op.getValorOperacao()).sum();
		return valorSacadoHoje;
	}
	
	public Operacao adicionaOP(double valor, int tipo) {
		GregorianCalendar date = new GregorianCalendar();
		Operacao op = new Operacao(date.get(GregorianCalendar.DAY_OF_MONTH),
				((int)date.get(GregorianCalendar.MONTH))+1, date.get(GregorianCalendar.YEAR),
				date.get(GregorianCalendar.HOUR), date.get(GregorianCalendar.MINUTE),
				date.get(GregorianCalendar.SECOND), contaEmUso.getNumero(), contaEmUso.getStatus(), valor, tipo);
		operacoes.add(op);// ADICIONA A OP NA LISTA DE OP's
		return op;
	}
}
