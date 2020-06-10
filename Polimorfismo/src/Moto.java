public class Moto extends Veiculo {
	private int cilindradas;

	public Moto() {
	}

	public Moto(String marca, String modelo, int cilindradas) {
		super(marca, modelo);
		this.cilindradas = cilindradas;
	}

	public int getCilindradas() {
		return cilindradas;
	}

	public void setCilindradas(int cilindradas) {
		this.cilindradas = cilindradas;
	}

	@Override
	public String toString() {
		return "Moto [cilindradas=" + cilindradas + ", getMarca()=" + getMarca() + ", getModelo()=" + getModelo() + "]";
	}
}