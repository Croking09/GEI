package e1;

public class ClienteVIP extends Cliente{

    private static final long minIngr = 0;
    private static final double comision = 0;
    private static final long minCom = 0;
    private static final long maxDeuda = -1;

    public ClienteVIP(DNI DNI) { super(DNI, minIngr, comision, minCom, maxDeuda);}
}