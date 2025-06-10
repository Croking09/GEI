package e2;

public abstract class Objetos{
    public enum Tipo{
        ATAQUE,
        DEFENSA
    }
    private final Tipo tipo;
    protected Objetos(Tipo tipo){this.tipo=tipo;}
    public Tipo getTipo(){return this.tipo;}
}