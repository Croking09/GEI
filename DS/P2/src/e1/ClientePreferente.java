package e1;

public class ClientePreferente extends Cliente{

    private static final long minIngr = 50000;
    private static final double comision = 0.02;
    private static final long minCom = 100;
    private static final long maxDeuda = 100000;

    public ClientePreferente(DNI DNI) { super(DNI, minIngr, comision, minCom, maxDeuda);}
}