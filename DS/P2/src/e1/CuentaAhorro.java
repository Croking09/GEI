package e1;

public class CuentaAhorro extends CuentaBancaria{
    public CuentaAhorro(IBAN IBAN, long saldo, Cliente titular) {
        super(IBAN, saldo, titular);
    }

    void ingresar(long cantidad){
        if(cantidad<this.titular.getMinIngr()){
            throw new IllegalArgumentException("Cantidad no suficiente");
        }
        this.saldo+=cantidad;
    }

    void retirar(long cantidad){
        if(cantidad<0){
            throw new IllegalArgumentException("Cantidad no valida");
        }

        long comision = (long)(this.titular.getComision()*cantidad);
        if(comision<this.titular.getMinCom()){comision=this.titular.getMinCom();}
        if(! (this.titular instanceof ClienteVIP)) {
            if (saldo - cantidad - comision < -this.titular.getMaxDeuda()) {
                throw new IllegalArgumentException("NO se permite descubierto");
            }
        }

        this.saldo-=(cantidad+comision);
    }
}
