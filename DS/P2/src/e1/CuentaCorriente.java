package e1;

public class CuentaCorriente extends CuentaBancaria{
    public CuentaCorriente(IBAN IBAN, long saldo, Cliente titular) {
        super(IBAN, saldo, titular);
    }
    void ingresar(long cantidad){
        if(cantidad<0){
            throw new IllegalArgumentException("Cantidad no valida");
        }
        this.saldo+=cantidad;
    }
    void retirar(long cantidad){
        if(cantidad<0){
            throw new IllegalArgumentException("Cantidad no valida");
        }
        if(!(this.titular instanceof ClienteVIP)) {
            if (saldo - cantidad < -this.titular.getMaxDeuda()) {
                throw new IllegalArgumentException("NO se permite descubierto");
            }
        }
        this.saldo-=cantidad;
    }
}