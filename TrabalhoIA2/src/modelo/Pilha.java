package modelo;

import java.util.ArrayList;
import java.util.Stack;
import modelo.No;

/**
 *
 * @author layca
 */
public class Pilha {

    private ArrayList<No> pilha = new ArrayList<No>();

    public void insere(No novo) {
        this.pilha.add(novo);
//        System.out.println("Inseriu >>> ");
//        imprimir();
    }
    public void remove() {
        this.pilha.remove(this.pilha.size()-1);
    }

    public boolean vazia() {
        return this.pilha.size() == 0;
    }
    
    public int tamanho(){
        return this.pilha.size();
    }
    
    public void imprimir() {
        for (No i : pilha) {
            System.out.println(" " + i.getEstado().getMargemCanoa() + ", " + i.getEstado().getQuantMissionariosEsquerda() + ", "
                    + i.getEstado().getQuantMissionariosDireita() + ", " + i.getEstado().getQuantCanibaisEsquerda() + ", " + i.getEstado().getQuantCanibaisDireita());

        }
    }
}
