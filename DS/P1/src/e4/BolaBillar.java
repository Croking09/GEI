package e4;

public enum BolaBillar {
    BLANCA(0,Color.BLANCO,Tipo.LISA),
    BOLA1(1,Color.AMARILLO,Tipo.LISA),
    BOLA2(2,Color.AZUL,Tipo.LISA),
    BOLA3(3,Color.ROJO,Tipo.LISA),
    BOLA4(4,Color.VIOLETA,Tipo.LISA),
    BOLA5(5,Color.NARANJA,Tipo.LISA),
    BOLA6(6,Color.VERDE,Tipo.LISA),
    BOLA7(7,Color.GRANATE,Tipo.LISA),
    BOLA8(8,Color.NEGRO,Tipo.LISA),
    BOLA9(9,Color.AMARILLO,Tipo.RAYADA),
    BOLA10(10,Color.AZUL,Tipo.RAYADA),
    BOLA11(11,Color.ROJO,Tipo.RAYADA),
    BOLA12(12,Color.VIOLETA,Tipo.RAYADA),
    BOLA13(13,Color.NARANJA,Tipo.RAYADA),
    BOLA14(14,Color.VERDE,Tipo.RAYADA),
    BOLA15(15,Color.GRANATE,Tipo.RAYADA);
    public enum Color{
        BLANCO,
        AMARILLO,
        AZUL,
        ROJO,
        VIOLETA,
        NARANJA,
        VERDE,
        GRANATE,
        NEGRO;
    }
    public enum Tipo{
        LISA,
        RAYADA;
    }
    private final int valor;
    private final Color color;
    private final Tipo tipo;
    BolaBillar(int valor, Color color,Tipo tipo) {
        this.valor=valor;
        this.color=color;
        this.tipo=tipo;
    }
    public int getValor(){
        return this.valor;
    }
    public Color getColor(){
        return this.color;
    }
    public Tipo getTipo(){
        return this.tipo;
    }
}