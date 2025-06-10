package e2;

public class NodoFin extends Nodo {
    //CONSTRUCTOR
    public NodoFin(String name){
        super(name);
    }

    //METODOS
    @Override
    public String getBigName(){
        return "End";
    }
    @Override
    public String Newick(){
        return "(" + this.getName() + " " + this.getBigName() + ")";
    }
    @Override
    public boolean valido(){
        return true;
    }
}