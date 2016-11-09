/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;
import java.util.Stack;
import modelo.No;

/**
 *
 * @author layca
 */
public class Lista {

    private ArrayList<No> lista = new ArrayList<No>();

    public void insere(No novo) {
        this.lista.add(novo);
    }
    public void remove() {
        this.lista.remove(0);
    }

    public boolean vazia() {
        return this.lista.size() == 0;
    }
    
    public int tamanho(){
        return this.lista.size();
    }
    
    public void imprimir() {
        for (No i : lista) {
            System.out.println(" " + i.getEstado().getMargemCanoa() + ", " + i.getEstado().getQuantMissionariosEsquerda() + ", "
                    + i.getEstado().getQuantMissionariosDireita() + ", " + i.getEstado().getQuantCanibaisEsquerda() + ", " + i.getEstado().getQuantCanibaisDireita());

        }
    }
}
