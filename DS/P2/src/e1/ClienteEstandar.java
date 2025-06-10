package e1;

public class ClienteEstandar extends Cliente{
    private static final long minIngr = 100000;
    private static final double comision = 0.04;
    private static final long minCom = 300;
    private static final long maxDeuda = 0;

    public ClienteEstandar(DNI DNI) { super(DNI, minIngr, comision, minCom, maxDeuda);}

}