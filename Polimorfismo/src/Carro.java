public class Carro extends Veiculo {
	private int cavalos;

	public Carro() {
	}

	public Carro(String marca, String modelo, int cavalos) {
		super(marca, modelo);
		this.cavalos = cavalos;
	}

	public int getCavalos() {
		return cavalos;
	}

	public void setCavalos(int cavalos) {
		this.cavalos = cavalos;
	}

	@Override
	public String toString() {
		return "Carro [cavalos=" + cavalos + ", getMarca()=" + getMarca() + ", getModelo()=" + getModelo() + "]";
	}
}
