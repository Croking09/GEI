package e2;

public class NodoAvistamiento extends NodoBifurcacion {
    //ATRIBUTOS
    private final int distancia;

    //CONSTRUCTOR
    public NodoAvistamiento(int distancia, String name) {
        super(name);
        this.distancia=distancia;
    }

    //METODOS
    @Override
    public String getBigName(){
        return "WaypointSpotting";
    }
    @Override
    public Nodo next(Flota flota){
        if(flota.getVision() >= distancia){
            return this.getHijoIzq();
        }
        else{
            return this.getHijoDer();
        }
    }
}