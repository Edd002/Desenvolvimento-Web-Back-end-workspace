public class Principal {

	public static void main(String[] args) {
		Veiculo veiculo;
		
		veiculo = preencherCarro();
		System.out.println(veiculo.toString());

		veiculo = preencherMoto();
		System.out.println(veiculo.toString());
	}
	
	private static Veiculo preencherCarro() {
		Carro carro = new Carro();

		carro.setMarca("Marca de Carro");
		carro.setModelo("Modelo de Carro");
		carro.setCavalos(500);

		return carro;
	}

	private static Veiculo preencherMoto() {
		Moto moto = new Moto();

		moto.setMarca("Marca de Moto");
		moto.setModelo("Modelo de Moto");
		moto.setCilindradas(200);

		return moto;
	}
}