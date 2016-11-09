package modelo;

public class Estado{
	private int quantMissionariosEsquerda;
	private int quantMissionariosDireita;
	private int quantCanibaisEsquerda;
	private int quantCanibaisDireita;
	//true = direita, false = esquerda
	private boolean margemCanoa;
	
	public Estado(boolean margemCanoa, int quantMissionariosEsquerda, int quantMissionariosDireita, int quantCanibaisEsquerda, int quantCanibaisDireita){
		this.quantCanibaisDireita = quantCanibaisDireita;
		this.quantCanibaisEsquerda = quantCanibaisEsquerda;
		this.quantMissionariosDireita = quantMissionariosDireita;
		this.quantMissionariosEsquerda = quantMissionariosEsquerda;
		this.margemCanoa = margemCanoa;
	}
	
	
	public int getQuantCanibaisEsquerda() {
		return quantCanibaisEsquerda;
	}
	
	public int getQuantMissionariosEsquerda() {
		return quantMissionariosEsquerda;
	}

	public int getQuantCanibaisDireita() {
		return quantCanibaisDireita;
	}
	
	public int getQuantMissionariosDireita() {
		return quantMissionariosDireita;
	}
	
	public boolean getMargemCanoa(){
		return margemCanoa;
	}
}
