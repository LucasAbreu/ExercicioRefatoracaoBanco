import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import Negocios.Conta;
import Negocios.GerenciaContas;

class ContaTest {
	private Conta conta;

	@DisplayName("Testa deposito")
	@ParameterizedTest
	@CsvSource({ "0, 10, 10, 0, 0" })
	public void testeDeposito(double saldo, double valorDepositado, double saldoFinal, int stateInit, int stateFinal) {
		conta = new Conta(1, "Fulano", saldo, stateInit);
		double sf = saldo + valorDepositado;
		conta.deposito(valorDepositado);
		assertEquals(conta.getStatus(), stateFinal);
		assertEquals(conta.getSaldo(), sf);
	}

	
	@DisplayName("Testa mudança de subida de status")
	@ParameterizedTest
	@CsvSource({"0, 10, 0, 0",	// Silver -> Silver
			"0, 49999, 0, 1",	// Silver -> Gold
			"0, 500000, 0, 2"	// Silver -> Gold -> Plat
			})
	public void testeDepositoStatusUp(double saldo, double valorDepositado, int stateInit, int stateFinal) {
		GerenciaContas gc = GerenciaContas.getInstance();
		try {
			gc.apagaConta(1);
			gc.criaConta(1, "Fulano", saldo, stateInit);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gc.setContaEmUso(1);
		gc.deposito(valorDepositado);
		gc.deposito(1);
		assertEquals(stateFinal, GerenciaContas.getInstance().getStatus());
	}
	
	@DisplayName("Testa mudança de descida de status")
	@ParameterizedTest
	@CsvSource({"101000, 10000,  2, 1",	// Plat -> Gold
			"101000, 80000, 2, 0"		// Plat -> Silver
			})
	public void testeRetiradaStatusDown(double saldo, double valorRetirado, int stateInit, int stateFinal) {
		GerenciaContas gc = GerenciaContas.getInstance();
		try {
			gc.apagaConta(1);
			gc.criaConta(1, "Fulano", saldo, stateInit);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gc.setContaEmUso(1);
		gc.retirada(valorRetirado);
		gc.retirada(1);
		assertEquals(stateFinal, GerenciaContas.getInstance().getStatus());
	}
	
	@DisplayName("Testa saque")
	@ParameterizedTest
	@CsvSource({ "10, 5, 5, 0, 0" })
	public void testeSaque(double saldo, double valorSacado, double saldoFinal, int stateInit, int stateFinal) {
		conta = new Conta(1, "Fulano", saldo, stateInit);
		double sf = saldo + valorSacado;
		conta.deposito(valorSacado);
		assertEquals(conta.getStatus(), stateFinal);
		assertEquals(conta.getSaldo(), sf);
	}

	
	
}
