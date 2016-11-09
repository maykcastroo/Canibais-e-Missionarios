package modelo;

import java.util.ArrayList;
import modelo.No;

/**
 *
 * @author layca
 */
public class Pilha {

    private ArrayList<No> pilha = new ArrayList<No>();

    public void insere(No objeto) {
        this.pilha.add(objeto);
    }

    public No remove() {
        return this.pilha.remove(this.pilha.size() - 1);
    }

    public boolean vazia() {
        return this.pilha.size() == 0;
    }

    public void imprimir() {
        for (No i : pilha) {
            System.out.println(" " + i.getEstado().getMargemCanoa() + ", " + i.getEstado().getQuantMissionariosEsquerda() + ", "
                    + i.getEstado().getQuantMissionariosDireita() + ", " + i.getEstado().getQuantCanibaisEsquerda() + ", " + i.getEstado().getQuantCanibaisDireita());

        }
    }
}
