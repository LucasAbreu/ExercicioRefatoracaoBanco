import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import Negocios.Conta;
import Negocios.GerenciaContas;
import Persistencia.Persistencia;

class GerenciaContasTest {
	private Conta conta;
	private GerenciaContas gc;

	private Persistencia mockPersistencia = mock(Persistencia.class);
	private GerenciaContas mockGerenciaContas = mock(GerenciaContas.class);

	@BeforeEach
	public void inicializa() {

		gc = GerenciaContas.getInstance();
		
		Map<Integer, Conta> contas = new HashMap<>();
		// int numero, String correntista, double saldo, int state
		contas.put(1, new Conta(1, "Fulano", 100, 0));
		contas.put(2, new Conta(2, "Beltrano", 70000, 1));

		when(mockPersistencia.loadContas()).thenReturn(contas);
		when(mockGerenciaContas.getContaEmUso()).thenReturn(contas.get(1));
		gc.setContas(contas);
		gc.setContaEmUso(1);
	}

	@DisplayName("Testa cria conta")
	@ParameterizedTest
	@CsvSource({ 
		"2,2,2" 
	})
	public void testCriaConta(int mes, int ano, double saldoMedioEsperado) throws Exception {
		gc.setContas(mockPersistencia.loadContas());
		gc.criaConta(3, "Ciclano", 1000, 0);
		gc.setContaEmUso(3);
		assertEquals( 3, gc.getNumeroContaEmUso() );
		assertEquals( "Ciclano", gc.getCorrentista() );
		assertEquals(1000, gc.getSaldo());
		assertEquals(0, gc.getStatus());
	}
	
	@DisplayName("Testa deposito")
	@ParameterizedTest
	@CsvSource({"10, 10, 20, 0, 0", 
				"20000, 40000, 60000, 0, 1",
				"80000, 130000, 211300, 1, 2",
				"200000, 100000, 302500, 2, 2"
	})
	public void testeDeposito(double saldo, double valorDepositado, double saldoFinal, int stateInit, int stateFinal) throws Exception {
		gc.criaConta(3, "Ciclano", saldo, stateInit);
		gc.setContaEmUso(3);
		gc.deposito(valorDepositado);
		assertEquals(stateFinal, gc.getStatus());
		assertEquals(saldoFinal, gc.getSaldo());
	}
	
	@DisplayName("Testa deposito")
	@ParameterizedTest
	@CsvSource({"10, 10, 0, 0, 0", 
				"120000, 30000, 90000, 2, 1",
				"30000, 10000, 20000, 1, 0",
	})

	public void testeSaque(double saldo, double valorRetirado, double saldoFinal, int stateInit, int stateFinal) throws Exception {
		gc.criaConta(3, "Ciclano", saldo, stateInit);
		gc.setContaEmUso(3);
		gc.retirada(valorRetirado);
		assertEquals(stateFinal, gc.getStatus());
		assertEquals(saldoFinal, gc.getSaldo());
	}
	
}
