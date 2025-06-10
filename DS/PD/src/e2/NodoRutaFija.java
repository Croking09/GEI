package e2;

public abstract class NodoRutaFija extends Nodo {
    //ATRIBUTOS
    private Nodo hijo;

    //CONSTRUCTOR
    protected NodoRutaFija(String name) {
        super(name);
    }

    //METODOS
    @Override
    public Nodo next(Flota flota) {
        return hijo;
    }
    @Override
    public String Newick(){
        return "(" + this.getName() + " " + this.getBigName() + ", " + this.hijo.Newick() + ")";
    }
    @Override
    public int caminoCorto(int tamCamino){
        return 1+this.hijo.caminoCorto(tamCamino);
    }
    @Override
    public void addHijoUnico(Nodo hijo){
        this.hijo=hijo;
    }
    @Override
    public void addHijoBinario(Nodo hijoIzq, Nodo hijoDer){
        throw new IllegalArgumentException("No puedo contener mas de 1 hijo");
    }
    @Override
    public boolean valido(){
        return hijo.valido();
    }
}