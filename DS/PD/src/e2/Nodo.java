package e2;

public abstract class Nodo {
    //ATRIBUTOS
    private final String name;

    //CONSTRUCTOR
    protected Nodo(String name) {
        if(name==null || name.isEmpty()){
            throw new IllegalArgumentException("Nombre no valido");
        }
        this.name = name;
    }

    //GETTERS
    public String getName() {
        return name;
    }
    public String getBigName() {
        return null;
    }

    //METODOS
    public void action(Flota flota){
    }
    public Nodo next(Flota flota){
        return null;
    }
    public String Newick(){
        return null;
    }
    public int caminoCorto(int tamCamino){
        return 1+tamCamino;
    }
    public void addHijoUnico(Nodo hijo){

    }
    public void addHijoBinario(Nodo hijoIzq, Nodo hijoDer){

    }
    public boolean valido(){
        return false;
    }
}