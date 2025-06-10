package e2;

public class NodoAtaqueAereo extends NodoRutaFija{
    //ATRIBUTOS
    private final int airpower;

    //CONSTRUCTOR
    public NodoAtaqueAereo(int airpower, String name) {
        super(name);
        this.airpower=airpower;
    }

    //METODOS
    @Override
    public String getBigName(){
        return "AirRaid";
    }
    @Override
    public void action(Flota flota){
        flota.restarHP(this.airpower - (2 * flota.getAntiaereos() + flota.getBlindaje()));
    }
}
