package e2;

public class NodoTormentaMarina extends NodoRutaFija{
    //ATRIBUTOS
    private final int fuerzaTormenta;

    //CONSTRUCTOR
    public NodoTormentaMarina(int fuerzaTormenta, String name) {
        super(name);
        this.fuerzaTormenta=fuerzaTormenta;
    }

    //METODOS
    @Override
    public String getBigName(){
        return "Maelstrom";
    }
    @Override
    public void action(Flota flota){
        if(flota.getVision()< this.fuerzaTormenta) {
            flota.restarHP(10);
        }
    }
}