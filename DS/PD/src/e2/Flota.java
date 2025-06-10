package e2;

public class Flota {
    //ATRIBUTOS
    private int HP;
    private final int blindaje;
    private final int poder;
    private final int vision;
    private final int antiaereos;
    private Nodo posicion;

    //CONSTRUCTOR
    public Flota(int HP, int blindaje, int poder, int vision, int antiaereos) {
        if(HP<=0 || blindaje<0 || poder<0 || vision<0 || antiaereos<0){
            throw new IllegalArgumentException("Estadisticas no validas");
        }

        this.HP=HP;
        this.blindaje=blindaje;
        this.poder=poder;
        this.vision=vision;
        this.antiaereos=antiaereos;
        this.posicion=null;
    }

    //METODOS
    public void restarHP(int damage){
        if(damage>0){
            HP-=damage;
        }
    }

    //SETTERS
    public void setPosicion(Nodo posicion) {
        this.posicion = posicion;
    }

    //GETTERS
    public Nodo getPosicion() {
        return posicion;
    }
    public int getHP() {
        return HP;
    }
    public int getBlindaje() {
        return blindaje;
    }
    public int getPoder() {
        return poder;
    }
    public int getVision() {
        return vision;
    }
    public int getAntiaereos() {
        return antiaereos;
    }
}