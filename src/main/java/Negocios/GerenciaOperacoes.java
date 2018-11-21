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

	private GerenciaOperacoes() {
		operacoes = Persistencia.getInstance().loadOperacoes();
	}

	static public GerenciaOperacoes getInstance() {
		if (instance == null)
			instance = new GerenciaOperacoes();
		return instance;
	}
	
	public List<Operacao> getOperacoes() {
		return operacoes;
	}

	/////////////////////////////// MÉTODOS SOBRE A LISTA DE OPERACOES ////////////////////////////////////////
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
	public double calculaSaldoMedioNoMes(int mes, int ano) { // USA A CONTA ATUAL
		Conta conta = GerenciaContas.getInstance().getContaEmUso();
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

	public double calculaRetiradaNoMes(Conta conta,int mes, int ano) {
		double valorRetirado = operacoes
				.stream().filter((op) -> op.getNumeroConta() == conta.getNumero() && op.getAno() == ano
						&& op.getMes() == mes && op.getTipoOperacao() == 1)
				.mapToDouble((op) -> op.getValorOperacao()).sum();
		return valorRetirado;
	}
	public double calculaRetiradaNoMes(int mes, int ano) {// USA A CONTA ATUAL
		Conta conta = GerenciaContas.getInstance().getContaEmUso();
		double valorRetirado = operacoes
				.stream().filter((op) -> op.getNumeroConta() == conta.getNumero() && op.getAno() == ano
						&& op.getMes() == mes && op.getTipoOperacao() == 1)
				.mapToDouble((op) -> op.getValorOperacao()).sum();
		return valorRetirado;
	}

	public double calculaDepositoNoMes(Conta conta, int mes, int ano) {
		double valorDepositado = operacoes
				.stream().filter((op) -> op.getNumeroConta() == conta.getNumero() && op.getAno() == ano
						&& op.getMes() == mes && op.getTipoOperacao() == 0)
				.mapToDouble((op) -> op.getValorOperacao()).sum();
		return valorDepositado;
	}
	public double calculaDepositoNoMes(int mes, int ano) { // USA A CONTA ATUAL
		Conta conta = GerenciaContas.getInstance().getContaEmUso();
		double valorDepositado = operacoes
				.stream().filter((op) -> op.getNumeroConta() == conta.getNumero() && op.getAno() == ano
						&& op.getMes() == mes && op.getTipoOperacao() == 0)
				.mapToDouble((op) -> op.getValorOperacao()).sum();
		return valorDepositado;
	}

	public List<Operacao> getOperacoesDaConta() { // USA A CONTA ATUAL
		int numeroConta = GerenciaContas.getInstance().getNumeroContaEmUso();
		return operacoes.stream().filter((op) -> op.getNumeroConta() == numeroConta ).collect(Collectors.toList());
	}
	public List<Operacao> getOperacoesDaConta(Conta conta) {
		return operacoes.stream().filter((op) -> op.getNumeroConta() == conta.getNumero()).collect(Collectors.toList());
	}

	public double calculaValorSacadoHoje() {
		GregorianCalendar calen = new GregorianCalendar();
		int diaHoje = calen.get(Calendar.DAY_OF_MONTH);
		int mesHoje = calen.get(Calendar.MONTH) + 1;
		int anoHoje = calen.get(Calendar.YEAR);
		double valorSacadoHoje = GerenciaOperacoes.getInstance().getOperacoes().stream()
				.filter((op) -> op.getNumeroConta() == GerenciaContas.getInstance().getContaEmUso().getNumero() && op.getAno() == anoHoje
						&& op.getMes() == mesHoje && op.getDia() == diaHoje && op.getTipoOperacao() == 1.0)
				.mapToDouble((op) -> op.getValorOperacao()).sum();
		return valorSacadoHoje;
	}

	public void adicionaOP(double valor, int tipo) { // tipo "0" para CREDITO, "1" para DEBITO
		GregorianCalendar date = new GregorianCalendar();
		Operacao op = new Operacao(date.get(GregorianCalendar.DAY_OF_MONTH),
				((int) date.get(GregorianCalendar.MONTH)) + 1, date.get(GregorianCalendar.YEAR),
				date.get(GregorianCalendar.HOUR), date.get(GregorianCalendar.MINUTE),
				date.get(GregorianCalendar.SECOND), GerenciaContas.getInstance().getContaEmUso().getNumero(), GerenciaContas.getInstance().getContaEmUso().getStatus(), valor, tipo);
		operacoes.add(op);// ADICIONA A OP NA LISTA DE OP's
	}

}
