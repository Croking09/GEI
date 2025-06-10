package e2;

public class NodoBatalla extends NodoBifurcacion {
    //ATRIBUTOS
    private final Flota enemigo;

    //CONSTRUCTOR
    public NodoBatalla(Flota enemigo, String name) {
        super(name);
        if(enemigo==null){
            throw new IllegalArgumentException("Enemigo no valido");
        }
        this.enemigo=enemigo;
    }

    //METODOS
    @Override
    public String getBigName(){
        return "Battle";
    }
    @Override
    public void action(Flota flota){
        enemigo.restarHP(flota.getPoder()-enemigo.getBlindaje());
        flota.restarHP(enemigo.getPoder()-flota.getBlindaje());
    }
    @Override
    public Nodo next(Flota flota){
        if(enemigo.getHP() <= 0){
            return this.getHijoIzq();
        }
        else{
            return this.getHijoDer();
        }
    }
}