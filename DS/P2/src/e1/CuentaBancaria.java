package e1;

import java.util.Random;

public abstract class CuentaBancaria {
    IBAN IBAN;
    protected long saldo;
    protected Cliente titular;

    protected CuentaBancaria(IBAN IBAN, long saldo, Cliente titular) {
        this.IBAN = IBAN;
        this.saldo = saldo;
        this.titular = titular;
    }

    protected IBAN getIBAN(){
        return IBAN;
    }
    protected long getSaldo(){
        return this.saldo;
    }
    protected Cliente getTitular(){
        return this.titular;
    }

    public static class IBAN{
        private final int numControl;
        private final int numCuenta;
        private final String pais;

        public IBAN(){
            Random random=new Random(System.currentTimeMillis());
            this.numControl=random.nextInt(90)+10;
            this.numCuenta=random.nextInt(90000)+10000;
            this.pais="ES";
        }

        public int getNumControl() {
            return numControl;
        }

        public int getNumCuenta() {
            return numCuenta;
        }

        public String getPais() {
            return pais;
        }
        @Override
        public String toString(){
            return (this.pais + this.numControl + this.numCuenta);
        }
    }
}