package e2;

public abstract class NodoBifurcacion extends Nodo {
    //ATRIBUTOS
    private Nodo hijoIzq;
    private Nodo hijoDer;

    //CONSTRUCTOR
    protected NodoBifurcacion(String name) {
        super(name);
    }

    //GETTERS
    public Nodo getHijoIzq() {
        return hijoIzq;
    }
    public Nodo getHijoDer() {
        return hijoDer;
    }

    //METODOS
    @Override
    public String Newick(){
        return "(" + this.getName() + " " + this.getBigName() + ", " + this.hijoIzq.Newick() + ", " + this.hijoDer.Newick() + ")";
    }
    @Override
    public int caminoCorto(int tamCamino){
        return 1+Math.min(this.hijoIzq.caminoCorto(tamCamino), this.hijoDer.caminoCorto(tamCamino));
    }
    @Override
    public void addHijoUnico(Nodo hijo){
        throw new IllegalArgumentException("Necesito 2 hijos");
    }
    @Override
    public void addHijoBinario(Nodo hijoIzq, Nodo hijoDer){
        this.hijoIzq=hijoIzq;
        this.hijoDer=hijoDer;
    }
    @Override
    public boolean valido(){
        return hijoIzq.valido() && hijoDer.valido();
    }
}