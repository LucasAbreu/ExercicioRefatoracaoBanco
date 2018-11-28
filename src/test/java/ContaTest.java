import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import Negocios.Conta;

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
