package e1;

public abstract class Cliente {
    private final long minIngr;
    private final double comision;
    private final long minCom;
    private final long maxDeuda;
    DNI DNI;
    protected Cliente(DNI DNI, long minIngr, double comision, long minCom, long maxDeuda) {
        this.DNI = DNI;
        this.minIngr = minIngr;
        this.comision = comision;
        this.minCom = minCom;
        this.maxDeuda = maxDeuda;
    }
    protected DNI getDNI(){return DNI;}
    protected long getMinIngr(){
        return minIngr;
    }
    protected double getComision() { return comision; }

    protected long getMaxDeuda() { return maxDeuda; }

    protected long getMinCom() { return minCom; }

    public static class DNI{
        private final int num;
        private final char letra;

        public DNI(int num){
            this.num = num;

            char[] letras = "TRWAGMYFPDXBNJZSQVHLCKE".toCharArray();

            this.letra = letras[num%23];
        }

        public int getNum() {
            return num;
        }

        public char getLetra() {
            return letra;
        }
        @Override
        public String toString(){
            return (Integer.toString(this.num) + this.letra);
        }
    }
}