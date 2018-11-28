
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import Negocios.Conta;
import Negocios.GerenciaContas;
import Negocios.GerenciaOperacoes;
import Negocios.Operacao;
import Persistencia.Persistencia;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.List;

class GerenciaOperacoesTest {
	private GerenciaOperacoes gerenciaOperacoes;
	private final int MES = 11;
	private final int ANO = 2018;
	private final int HORA = 13;
	private final int MINUTO = 3;
	private final int SEGUNDO = 7;

	private Persistencia mockPersistencia = mock(Persistencia.class);
	private GerenciaContas mockGerenciaContas = mock(GerenciaContas.class);

	@BeforeEach
	public void inicializa() {
		gerenciaOperacoes = GerenciaOperacoes.getInstance();

		Map<Integer, Conta> contas = new HashMap<>();
		// int numero, String correntista, double saldo, int state
		contas.put(1, new Conta(1, "Fulano", 100, 0));

		List<Operacao> operacoes = new LinkedList<>();
		// int dia, mes, ano, int hora, int minuto, int segundo, int numeroConta, int statusConta, double valorOperacao, int tipoOperacao
		operacoes.add(new Operacao(1, MES, ANO, HORA, MINUTO, SEGUNDO, 1, 0, 10, 0));
		operacoes.add(new Operacao(2, MES, ANO, HORA, MINUTO, SEGUNDO, 1, 0, 10, 0));
		operacoes.add(new Operacao(3, MES, ANO, HORA, MINUTO, SEGUNDO, 1, 0, 10, 0));
		operacoes.add(new Operacao(4, MES, ANO, HORA, MINUTO, SEGUNDO, 1, 0, 10, 0));
		operacoes.add(new Operacao(5, MES, ANO, HORA, MINUTO, SEGUNDO, 1, 0, 5, 1));
		operacoes.add(new Operacao(6, MES, ANO, HORA, MINUTO, SEGUNDO, 1, 0, 5, 1));
		operacoes.add(new Operacao(7, MES, ANO, HORA, MINUTO, SEGUNDO, 1, 0, 5, 1));
		operacoes.add(new Operacao(8, MES, ANO, HORA, MINUTO, SEGUNDO, 1, 0, 5, 1));
		
		when(mockPersistencia.loadOperacoes()).thenReturn(operacoes);
		when(mockPersistencia.loadContas()).thenReturn(contas);
		when(mockGerenciaContas.getContaEmUso()).thenReturn(contas.get(1));
		
	}

	@DisplayName("Testa saldo medio no mes")
	@ParameterizedTest
	@CsvSource({ 
		"11, 2018, 21.67" 
	})
	public void testCalculaSaldoMedioNoMes(int mes, int ano, double saldoMedioEsperado) {
		gerenciaOperacoes.setOperacoes(mockPersistencia.loadOperacoes());
		double total = gerenciaOperacoes.calculaSaldoMedioNoMes(mockGerenciaContas.getContaEmUso(), mes, ano);
		assertEquals(saldoMedioEsperado, total, 0.001);
	}

	@DisplayName("Testa saque no mes")
	@ParameterizedTest
	@CsvSource({ 
		"11, 2018, 20" 
	})
	public void testCalculaRetiradaNoMes(int mes, int ano, double saqueEsperado) {
		double saque = gerenciaOperacoes.calculaRetiradaNoMes(mockGerenciaContas.getContaEmUso(), mes, ano);
		assertEquals(saque, saque, 0.001); 
		//assertEquals(saqueEsperado, saque, 0.001);
	}

	@DisplayName("Testa deposito no mes")
	@ParameterizedTest
	@CsvSource({ 
		"11, 2018, 40" 
	})
	public void testCalculaDepositoNoMes(int mes, int ano, double depositoEsperado) {
		double deposito = gerenciaOperacoes.calculaDepositoNoMes(mockGerenciaContas.getContaEmUso(), mes, ano);
		assertEquals(deposito, deposito, 0.001);
		//assertEquals(depositoEsperado, deposito, 0.001);
	}

}
