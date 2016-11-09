package controle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import modelo.Estado;
import modelo.No;


public class Controle implements modelo.FuncoesControle{
	private ArrayList<Estado> estadosTestados = new ArrayList<Estado>();
	private ArrayList<No> filaA = new ArrayList<No>();
	private boolean solucaoEncontrada;
	private boolean semSolucao;
	private int capacidadeCanoa;
	private int quantNosVisitados;
	
	public Controle(int capacidadeCanoa){
		this.capacidadeCanoa = capacidadeCanoa;
		this.quantNosVisitados = 0;
		this.solucaoEncontrada = false;
		this.semSolucao = false;
	}
	
	public int quantidadeNosFronteiraDeEstados(No raiz){
		int quantFin = raiz.getFilhos().size();
		int quantIn;
		No noIn = raiz;
		No noTemp;
		while(noIn.getFilhos().size() > 0){
			noTemp = noIn;
			noIn = noIn.getFilhos().get(0);
			quantIn = 0;
			do{
				for(int i=0; i<noTemp.getFilhos().size(); i++){
					quantIn = quantIn + noTemp.getFilhos().get(i).getFilhos().size();
				}
				noTemp = noTemp.getIrmaoDireito();
			}while(noTemp != null);
			if(quantFin < quantIn)
				quantFin = quantIn;
		}
		return quantFin;
	}
	
	//verifica se um estado já foi testado
	private boolean isTestado(Estado e){
		for(int i=0; i<estadosTestados.size(); i++){
			if(estadosTestados.get(i).getMargemCanoa() == e.getMargemCanoa()){
				if(estadosTestados.get(i).getQuantCanibaisEsquerda() == e.getQuantCanibaisEsquerda()){
					if(estadosTestados.get(i).getQuantMissionariosDireita() == e.getQuantMissionariosDireita()){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	//testa se um estado é a solução
	private boolean isSolucao(Estado e){
		if(e.getQuantMissionariosEsquerda() == 0 && e.getQuantCanibaisEsquerda() == 0)
			return true;
		else
			return false;
	}
	
	//testa se um estado é válido
	private boolean isValido(Estado e){
		if((e.getQuantCanibaisDireita() <= e.getQuantMissionariosDireita() || e.getQuantMissionariosDireita() == 0) && (e.getQuantCanibaisEsquerda()<=e.getQuantMissionariosEsquerda() || e.getQuantMissionariosEsquerda() == 0))
			return true;
		return false;
	}
	
	//testa a possibilidade de transportar a mesma quantidade de canibais e missionários e retorna o estado resultante
	private No transportaCanibaisEMissionarios(No noInicial, int numero){
		No newNo;
		if(numero > 1 && numero <= capacidadeCanoa && numero%2 == 0){
			numero = numero/2;
			Estado e;
			//true = direita; false = esquerda
			boolean ladoCanoa = noInicial.getEstado().getMargemCanoa();
			int quantCanibais = 0;
			int quantMissionarios = 0;
				
			//Descobrindo em que margem está a canoa
			if(ladoCanoa == true){
				quantCanibais = noInicial.getEstado().getQuantCanibaisDireita();
				quantMissionarios = noInicial.getEstado().getQuantMissionariosDireita();
			}
			else{
				quantCanibais = noInicial.getEstado().getQuantCanibaisEsquerda();
				quantMissionarios = noInicial.getEstado().getQuantMissionariosEsquerda();
			}
				
			if(quantCanibais >= numero && quantMissionarios >= numero){
				if(ladoCanoa == true){
					e = new Estado(!ladoCanoa, noInicial.getEstado().getQuantMissionariosEsquerda()+numero, quantMissionarios-numero, noInicial.getEstado().getQuantCanibaisEsquerda()+numero, quantCanibais-numero);
				}
				else{
					e = new Estado(!ladoCanoa, quantMissionarios-numero, noInicial.getEstado().getQuantMissionariosDireita()+numero, quantCanibais-numero, noInicial.getEstado().getQuantCanibaisDireita()+numero);
				}
			
				newNo = new No(noInicial, e);
				return newNo;
			}
		}
			
		return null;
	}
		
	//testa a possibilidade de transportar apenas canibais e retorna o estado resultante
	private No transportaCanibais(No noInicial, int numero){
		No newNo = null;
		if(numero>=1 && numero<=capacidadeCanoa){
			Estado e;
			//true = direita; false = esquerda
			boolean ladoCanoa = noInicial.getEstado().getMargemCanoa();
			int quantCanibais = 0;
				
			//Descobrindo em que margem está a canoa
			if(ladoCanoa == true){	
				quantCanibais = noInicial.getEstado().getQuantCanibaisDireita();
			}
			else{
				quantCanibais = noInicial.getEstado().getQuantCanibaisEsquerda();
			}
				
			if(quantCanibais>=numero){
				if(ladoCanoa == true){
					e = new Estado(!ladoCanoa, noInicial.getEstado().getQuantMissionariosEsquerda(), noInicial.getEstado().getQuantMissionariosDireita(), noInicial.getEstado().getQuantCanibaisEsquerda()+numero, quantCanibais-numero);
				}
				else{
					e = new Estado(!ladoCanoa, noInicial.getEstado().getQuantMissionariosEsquerda(), noInicial.getEstado().getQuantMissionariosDireita(), quantCanibais-numero, noInicial.getEstado().getQuantCanibaisDireita()+numero);
				}
			
				newNo = new No(noInicial, e);  
			}
		}
			
		return newNo;
	}
		
	//testa a possibilidade de transportar apenas missionários e retorna o estado resultante
	private No transportaMissionarios(No noInicial, int numero){
		No newNo = null;
		if(numero>=1 && numero<=capacidadeCanoa){
			Estado e;
			//true = direita; false = esquerda
			boolean ladoCanoa = noInicial.getEstado().getMargemCanoa();
			int quantMissionarios = 0;
				
			//Descobrindo em que margem está a canoa
			if(ladoCanoa == true){	
				quantMissionarios = noInicial.getEstado().getQuantMissionariosDireita();
			}
			else{
				quantMissionarios = noInicial.getEstado().getQuantMissionariosEsquerda();
			}
				
			if(quantMissionarios>=numero){
				if(ladoCanoa == true){
					e = new Estado(!ladoCanoa,noInicial.getEstado().getQuantMissionariosEsquerda()+numero, quantMissionarios-numero, noInicial.getEstado().getQuantCanibaisEsquerda(), noInicial.getEstado().getQuantCanibaisDireita());
				}
				else{
					e = new Estado(!ladoCanoa,quantMissionarios-numero, noInicial.getEstado().getQuantMissionariosDireita()+numero, noInicial.getEstado().getQuantCanibaisEsquerda(), noInicial.getEstado().getQuantCanibaisDireita());
				}
		
				newNo = new No(noInicial, e);  
			}
		}
			
		return newNo;
	}
	
	private boolean testaTudo(No novoNo, No noInicial){
		if(isValido(novoNo.getEstado())){
			if(!isTestado(novoNo.getEstado())){
				quantNosVisitados++;
				//Exibe informação de nó expandido
				System.out.println("Novo No: "+novoNo.getEstado().getMargemCanoa()+", "+novoNo.getEstado().getQuantMissionariosEsquerda()+", "
						+novoNo.getEstado().getQuantMissionariosDireita()+", "+novoNo.getEstado().getQuantCanibaisEsquerda()+", "+novoNo.getEstado().getQuantCanibaisDireita());				
				System.out.println("Quantidade de nós gerados: "+quantNosVisitados);
				Scanner input = new Scanner(System.in);
				//String pausa = input.next();
				return true;
			}
			else{
				return false;
			}
		}
		else{
			return false;
		}
	}
	
	@Override
	public void buscaEmLargura(No noInicial) {
		if(noInicial == null)
			return;
		if(!isSolucao(noInicial.getEstado()) && !semSolucao){
			estadosTestados.add(noInicial.getEstado());
			No novoNo;
			
			//Inicio de testes de expansão
			
			//Teste de possibilidade de transportar a mesma quantidade de canibais e missionarios
			for(int i=capacidadeCanoa; i>0; i--){
				if(!solucaoEncontrada){
					novoNo = transportaCanibaisEMissionarios(noInicial, i); //testa se eh possivel transportar
					if(novoNo != null){
						if(testaTudo(novoNo, noInicial)){
							if(noInicial.getFilhos().size()>0){
								noInicial.getFilhos().get(noInicial.getFilhos().size()-1).setIrmaoDireito(novoNo);
							}
							noInicial.addFilho(novoNo);
							solucaoEncontrada = isSolucao(novoNo.getEstado());
							estadosTestados.add(novoNo.getEstado());
						}
					}
					else{
						//Impossibilidade de transportar canibais e missionarios
					}
				}
				else{
					break;
				}
			}
			//Fim da primeira possibilidade
			
			//Teste de possibilidade de transportar apenas canibais
			for(int i=capacidadeCanoa; i>0; i--){
				if(!solucaoEncontrada){
					novoNo = transportaCanibais(noInicial,i);
					if(novoNo != null){
						if(testaTudo(novoNo, noInicial)){
							if(noInicial.getFilhos().size()>0){
								noInicial.getFilhos().get(noInicial.getFilhos().size()-1).setIrmaoDireito(novoNo);
							}
							noInicial.addFilho(novoNo);
							solucaoEncontrada = isSolucao(novoNo.getEstado());
							estadosTestados.add(novoNo.getEstado());
						}
					}
					else{
						////Impossibilidade de transportar apenas canibais
					}
				}
				else{
					break;
				}
			}
			//Fim da segunda possibilidade
			
			//Teste de possibilidade de transportar apenas missionários
			for(int i=capacidadeCanoa; i>0; i--){
				if(!solucaoEncontrada){
					novoNo = transportaMissionarios(noInicial,i);
					if(novoNo != null){
						if(testaTudo(novoNo, noInicial)){
							if(noInicial.getFilhos().size()>0){
								noInicial.getFilhos().get(noInicial.getFilhos().size()-1).setIrmaoDireito(novoNo);
							}
							noInicial.addFilho(novoNo);
							solucaoEncontrada = isSolucao(novoNo.getEstado());
							estadosTestados.add(novoNo.getEstado());
						}
					}
					else{
						////Impossibilidade de transportar apenas missionários
					}
				}
				else{
					break;
				}
			}
			//Fim da terceira possibilidade
			
			//O nó foi completamente expandido
			noInicial.expandir();
			
			//Fim do teste de todas as possibilidades
			
			No noASerExpandido  = noInicial;
			if(!solucaoEncontrada && !semSolucao){
				if(!noInicial.isRaiz()){
					while(noASerExpandido.isExpandido()){
						//Se tiver irmão
						if(noInicial.getIrmaoDireito() != null){
							//Se o irmão não tiver sido expandido
							if(!noInicial.getIrmaoDireito().isExpandido()){
								noASerExpandido = noInicial.getIrmaoDireito();
							}
							else{
								if(noASerExpandido.getIrmaoDireito().getFilhos().size()>0){
									noASerExpandido = noASerExpandido.getIrmaoDireito().getFilhos().get(0);
									while(noASerExpandido.isExpandido()){
										if(noASerExpandido.getFilhos().size() > 0)
											noASerExpandido = noASerExpandido.getFilhos().get(0);
										else
											break;
									}
								}
							}
						
						}
						else{
							while(noASerExpandido.getPai() != null){
								noASerExpandido = noASerExpandido.getPai();
								if(noASerExpandido.getIrmaoDireito() != null){
									noASerExpandido = noASerExpandido.getIrmaoDireito();
									break;
								}
							}
							
							if(noASerExpandido.getFilhos().size() > 0){
								noASerExpandido = noASerExpandido.getFilhos().get(0);
								while(noASerExpandido.isExpandido()){
									if(noASerExpandido.getFilhos().size() > 0)
										noASerExpandido = noASerExpandido.getFilhos().get(0);
									else{
										if(noASerExpandido.isExpandido())
											semSolucao = true;
										break;
									}
								}
							}
						}
					}
					buscaEmLargura(noASerExpandido);
				}	
				else{
					buscaEmLargura(noInicial.getFilhos().get(0));
				}
				
			}
			
		}
		else{				
			solucaoEncontrada = true;
		}
	}
	
	@Override
	public void buscaEmProfundidade(No noInicial) {
		if(!isSolucao(noInicial.getEstado())){
			estadosTestados.add(noInicial.getEstado());
			No novoNo;
			
			//Início de testes de possibilidades de Transporte
			
			//Teste de possibilidade de transportar apenas canibais
			for(int i=capacidadeCanoa; i>0; i--){
				if(!solucaoEncontrada){
					novoNo = transportaCanibais(noInicial,i);
					if(novoNo != null){
						if(testaTudo(novoNo, noInicial)){
							noInicial.addFilho(novoNo);
							//buscaEmProfundidade(novoNo);
						}
					}
					else{
						////Impossibilidade de transportar apenas canibais
					}
				}
				else{
					break;
				}
			}
			//Fim da primeira possibilidade
			
			

			//Teste de possibilidade de transportar a mesma quantidade de canibais e missionarios
			for(int i=capacidadeCanoa; i>0; i--){
				if(!solucaoEncontrada){
					novoNo = transportaCanibaisEMissionarios(noInicial, i);
					if(novoNo != null){
						if(testaTudo(novoNo, noInicial)){
							noInicial.addFilho(novoNo);
							//buscaEmProfundidade(novoNo);
						}
						
					}
					else{
						//Impossibilidade de transportar canibais e missionarios
					}
				}
				else{
					break;
				}
			}
			//Fim da segunda possibilidade
			
			//Teste de possibilidade de transportar apenas missionários
			for(int i=capacidadeCanoa; i>0; i--){
				if(!solucaoEncontrada){
					novoNo = transportaMissionarios(noInicial,i);
					if(novoNo != null){
						if(testaTudo(novoNo, noInicial)){
							noInicial.addFilho(novoNo);
							//buscaEmProfundidade(novoNo);
						}
					}
					else{
						////Impossibilidade de transportar apenas missionários
					}
				}
				else{
					break;
				}
			}
			//Fim da terceira possibilidade
			
							
			//Fim do teste de todas as possibilidades
			if(!solucaoEncontrada){
				if(noInicial.getFilhos().size() > 0){
					for(int i=0; i<noInicial.getFilhos().size(); i++){
						if(!solucaoEncontrada)
							buscaEmProfundidade(noInicial.getFilhos().get(i));
						else{
							break;
						}
					}
				}
				else{
					//-------FIM DO RAMO--------
					System.out.println("-----------FIM DO RAMO---------\n");
					Scanner input = new Scanner(System.in);
					String pausa = input.next();
				}
			}
		}
		else{
			solucaoEncontrada = true;
		}
		
	}

	@Override
	public void buscaGulosa(No noInicial) {
		/* Heurística
		 * O máximo de número de ambos para a esquerda
		 * O máximo de canibais para a direita
		 * O máximo de missionários para a direita
		 * O mínimo de canibais para a esquerda
		 * */
		
		if(!isSolucao(noInicial.getEstado())){
			estadosTestados.add(noInicial.getEstado());
			No novoNo;
			
			//Início de testes de possibilidades de Transporte
			
			if(noInicial.getEstado().getMargemCanoa() == false){
				for(int i=capacidadeCanoa; i>0; i--){
					//Teste de possibilidade de transportar a mesma quantidade de canibais e missionarios
					novoNo = transportaCanibaisEMissionarios(noInicial, i);
					if(novoNo != null){
						if(testaTudo(novoNo, noInicial)){
							noInicial.addFilho(novoNo);
						}
					}
					
					//Teste de possibilidade de transportar apenas canibais
					novoNo = transportaCanibais(noInicial, i);
					if(novoNo != null){
						if(testaTudo(novoNo, noInicial)){
							noInicial.addFilho(novoNo);
						}
					}
					
					//Teste de possibilidade de transportar apenas missionarios
					novoNo = transportaMissionarios(noInicial, i);
					if(novoNo != null){
						if(testaTudo(novoNo, noInicial)){
							noInicial.addFilho(novoNo);
						}
					}
				}
			}
			else{
				for(int i=1; i<=capacidadeCanoa; i++){
					//Teste de possibilidade de transportar apenas missionarios
					novoNo = transportaMissionarios(noInicial, i);
					if(novoNo != null){
						if(testaTudo(novoNo, noInicial)){
							noInicial.addFilho(novoNo);
						}
					}
					
					//Teste de possibilidade de transportar a mesma quantidade de canibais e missionarios
					novoNo = transportaCanibaisEMissionarios(noInicial, i);
					if(novoNo != null){
						if(testaTudo(novoNo, noInicial)){
							noInicial.addFilho(novoNo);
						}
					}
					
					//Teste de possibilidade de transportar apenas canibais
					novoNo = transportaCanibais(noInicial, i);
					if(novoNo != null){
						if(testaTudo(novoNo, noInicial)){
							noInicial.addFilho(novoNo);
						}
					}
					
				}
			}
			if(!solucaoEncontrada){
				if(noInicial.getFilhos().size() > 0){
					for(int i=0; i<noInicial.getFilhos().size(); i++){
						if(!solucaoEncontrada)
							buscaGulosa(noInicial.getFilhos().get(i));
						else{
							break;
						}
					}
				}
				else{
					//-------FIM DO RAMO--------
					System.out.println("-----------FIM DO RAMO---------\n");
					Scanner input = new Scanner(System.in);
					String pausa = input.next();
				}
			}
		}
		else{
			solucaoEncontrada = true;
		}
		
	}

	@Override
	public void buscaA(No noInicial) {
		/* Heurística
		 * O número máximo de ambos para a direita
		 * O máximo de canibais para a direita
		 * O máximo de missionários para a direita
		 * O mínimo de canibais para a esquerda
		 * */
		
		if(!isSolucao(noInicial.getEstado())){
			estadosTestados.add(noInicial.getEstado());
			No novoNo;
			
			//Início de testes de possibilidades de Transporte
			
			if(noInicial.getEstado().getMargemCanoa() == false){
				for(int i=capacidadeCanoa; i>0; i--){
					//Teste de possibilidade de transportar a mesma quantidade de canibais e missionarios
					novoNo = transportaCanibaisEMissionarios(noInicial, i);
					if(novoNo != null){
						if(testaTudo(novoNo, noInicial)){
							noInicial.addFilho(novoNo);
						}
					}
					
					//Teste de possibilidade de transportar apenas canibais
					novoNo = transportaCanibais(noInicial, i);
					if(novoNo != null){
						if(testaTudo(novoNo, noInicial)){
							noInicial.addFilho(novoNo);
						}
					}
					
					//Teste de possibilidade de transportar apenas missionarios
					novoNo = transportaMissionarios(noInicial, i);
					if(novoNo != null){
						if(testaTudo(novoNo, noInicial)){
							noInicial.addFilho(novoNo);
						}
					}
				}
			}
			else{
				for(int i=1; i<=capacidadeCanoa; i++){
					//Teste de possibilidade de transportar apenas missionarios
					novoNo = transportaMissionarios(noInicial, i);
					if(novoNo != null){
						if(testaTudo(novoNo, noInicial)){
							noInicial.addFilho(novoNo);
						}
					}
					
					//Teste de possibilidade de transportar a mesma quantidade de canibais e missionarios
					novoNo = transportaCanibaisEMissionarios(noInicial, i);
					if(novoNo != null){
						if(testaTudo(novoNo, noInicial)){
							noInicial.addFilho(novoNo);
						}
					}
					
					//Teste de possibilidade de transportar apenas canibais
					novoNo = transportaCanibais(noInicial, i);
					if(novoNo != null){
						if(testaTudo(novoNo, noInicial)){
							noInicial.addFilho(novoNo);
						}
					}
					
				}
			}
			
			if(!solucaoEncontrada){
				if(noInicial.getFilhos().size() > 0){
					for(int i=0; i<noInicial.getFilhos().size(); i++){
						filaA.add(noInicial.getFilhos().get(i));
					}
					
					Collections.sort(filaA);

				}
				else{
					//-------FIM DO RAMO--------
					System.out.println("-----------FIM DO RAMO---------\n");
					Scanner input = new Scanner(System.in);
					String pausa = input.next();
				}
				
				if(filaA.size() > 0){
					novoNo = filaA.get(0);
					filaA.remove(0);
					buscaA(novoNo);
				}
			}
		}
		else{
			solucaoEncontrada = true;
		}
	}

}
