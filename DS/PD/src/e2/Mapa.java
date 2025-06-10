package e2;

import java.util.ArrayList;
import java.util.List;

public class Mapa {
    //ATRIBUTOS
    private final List<Nodo> nodos;

    //CONSTRUCTOR
    public Mapa(Nodo inicial){
        if(inicial==null){
           throw new IllegalArgumentException("Nodo no valido");
        }
        this.nodos=new ArrayList<>();
        nodos.add(inicial);
    }

    //GETTERS
    public Nodo getInicial() {
        return nodos.get(0);
    }

    //METODOS
    public void addHijos(Nodo padre, Nodo hijo){
        if(!nodos.contains(padre)){
            throw new IllegalArgumentException("El nodo padre no existe");
        }
        padre.addHijoUnico(hijo);
        nodos.add(hijo);
    }
    public void addHijos(Nodo padre, Nodo hijoIzq, Nodo hijoDer){
        if(!nodos.contains(padre)){
            throw new IllegalArgumentException("El nodo padre no existe");
        }
        padre.addHijoBinario(hijoIzq, hijoDer);
        nodos.add(hijoIzq);
        nodos.add(hijoDer);
    }
    public boolean mapaValido(){
        boolean result;

        try{
            result = this.getInicial().valido();
        } catch (Exception e){
            return false;
        }

         return result;
    }
}