package Negocios;

public class FactoryStateConta {
	private static FactoryStateConta instance;

	private FactoryStateConta() {

	}

	public static FactoryStateConta getInstance() {
		if (instance == null) {
			instance = new FactoryStateConta();
		}
		return instance;
	}

	public IStateConta novaConta(int tipo) {
		switch (tipo) {
		case 0:
			return new Silver();
		case 1:
			return new Gold();
		case 2:
			return new Platinum();
		default:
			return null;
		}
	}

	class Silver implements IStateConta {
		private final int STATUS = 0;
		private final int LIM_SILVER_SAQUE = 5000;

		@Override
		public String getStrStatus() {
			return "Silver";
		}

		@Override
		public int getStatus() {
			return STATUS;
		}

		@Override
		public double getLimRetiradaDiaria() {
			return LIM_SILVER_SAQUE;
		}

		@Override
		public double creditoDeposito(double value) {
			return value;
		}
	}

	class Gold implements IStateConta {
		private final int STATUS = 1;
		private final int LIM_GOLD_SAQUE = 50000;

		@Override
		public String getStrStatus() {
			return "Gold";
		}

		@Override
		public int getStatus() {
			return STATUS;
		}

		@Override
		public double getLimRetiradaDiaria() {
			return LIM_GOLD_SAQUE;
		}

		@Override
		public double creditoDeposito(double value) {
			return value * 1.01;
		}
	}

	class Platinum implements IStateConta {
		private final int STATUS = 2;
		private final int LIM_PLATINUM_SAQUE = 500000;

		@Override
		public String getStrStatus() {
			return "Platinum";
		}

		@Override
		public int getStatus() {
			return STATUS;
		}

		@Override
		public double getLimRetiradaDiaria() {
			return LIM_PLATINUM_SAQUE;
		}

		@Override
		public double creditoDeposito(double value) {
			return value * 1.025;
		}
	}
}
